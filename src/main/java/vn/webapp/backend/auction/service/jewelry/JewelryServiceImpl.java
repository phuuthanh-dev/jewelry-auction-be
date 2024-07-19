package vn.webapp.backend.auction.service.jewelry;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.JewelryCreateRequest;
import vn.webapp.backend.auction.dto.JewelryUpdateRequest;
import vn.webapp.backend.auction.dto.SendJewelryFromUserRequest;
import vn.webapp.backend.auction.enums.JewelryMaterial;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.ErrorMessages;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.model.JewelryCategory;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.JewelryCategoryRepository;
import vn.webapp.backend.auction.repository.JewelryRepository;
import vn.webapp.backend.auction.repository.UserRepository;
import vn.webapp.backend.auction.service.email.EmailService;
import vn.webapp.backend.auction.service.jwt.JwtService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class JewelryServiceImpl implements JewelryService {

    private final JewelryRepository jewelryRepository;
    private final UserRepository userRepository;
    private final JewelryCategoryRepository jewelryCategoryRepository;
    private final EmailService emailService;
    private final JwtService jwtService;

    @Override
    public List<Jewelry> getAll() {
        return jewelryRepository.findAll();
    }

    @Override
    public Jewelry getJewelryById(Integer id) {
        return jewelryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.JEWELRY_NOT_FOUND));
    }

    @Override
    public Page<Jewelry> getJewelriesByUsername(String username, Pageable pageable) {
        return jewelryRepository.findByUserUsername(username, pageable);
    }

    @Override
    public Page<Jewelry> getJewelriesActiveByUserId(Integer userId, String jewelryName,Pageable pageable) {
        return jewelryRepository.findJewelryActiveByUserId(userId, jewelryName, pageable );
    }

    @Override
    public Jewelry requestJewelry(SendJewelryFromUserRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.userId());

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + request.userId() + " not found");
        }

        Optional<JewelryCategory> optionalCategory = jewelryCategoryRepository.findByName(request.category());

        if (optionalCategory.isEmpty()) {
            throw new IllegalArgumentException("Category with ID " + request.category() + " not found");
        }

        User user = optionalUser.get();
        JewelryCategory category = optionalCategory.get();

        Jewelry jewelry = new Jewelry();
        jewelry.setUser(user);
        jewelry.setBuyNowPrice(request.buyNowPrice());
        jewelry.setDescription(request.description());
        jewelry.setMaterial(request.material());
        jewelry.setWeight(request.weight());
        jewelry.setCategory(category);
        jewelry.setBrand(request.brand());
//        jewelry.setIsHolding(false);
        jewelry.setName(request.name());
        jewelry.setCreateDate(Timestamp.from(Instant.now()));
        jewelry.setState(JewelryState.APPROVING);

        jewelryRepository.save(jewelry);

        return jewelry;
    }

    @Override
    public Jewelry getLatestJewelry() {
        return jewelryRepository.findLatestJewelry().get(0);
    }

    @Override
    public Jewelry setStateWithHolding(Integer id,boolean isHolding, JewelryState state) throws MessagingException {
        var existingJewelry = jewelryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.JEWELRY_NOT_FOUND));
        var exitingUser = existingJewelry.getUser();
        System.out.println("Holding: " + isHolding );
        existingJewelry.setState(state);
        existingJewelry.setIsHolding(isHolding);
        if(isHolding) {
            existingJewelry.setReceivedDate(Timestamp.from(Instant.now()));
            emailService.sendConfirmHoldingEmail(exitingUser.getEmail(), exitingUser.getFullName(), existingJewelry.getName());
        }else {
            existingJewelry.setDeliveryDate(Timestamp.from(Instant.now()));
        }
        return  existingJewelry;
    }

    @Override
    public List<Jewelry> getJewelryByUsername(String username) {
        List<Jewelry> jewelryList = jewelryRepository.findJewelryByUsername(username);
        if (jewelryList.isEmpty()) {
            throw new ResourceNotFoundException("User '" + username + "' does not have any jewelry items.");
        }
        return jewelryList;
    }

    @Override
    public void deleteJewelry(Integer id) {
        var existingJewelry = jewelryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.JEWELRY_NOT_FOUND));
        existingJewelry.setState(JewelryState.HIDDEN);
    }

    @Override
    public List<Jewelry> getJewelriesByCategoryId(Integer id) {
        return jewelryRepository.findJewelryByCategoryId(id);
    }

    @Override
    public List<Jewelry> getJewelriesByNameContain(String key) {
        return jewelryRepository.getJewelriesByNameContaining(key);
    }

    @Override
    public Page<Jewelry> getAllJewelries(Pageable pageable) {
        return jewelryRepository.findByState(JewelryState.ACTIVE, pageable);
    }

    @Override
    public Page<Jewelry> getJewelriesManager(JewelryState state, String jewelryName, String category, Pageable pageable) {
        if (category.equals("Tất cả"))
            return jewelryRepository.findJewelriesManager(state,jewelryName,null,pageable);
        return jewelryRepository.findJewelriesManager(state, jewelryName, category, pageable);
    }

    @Override
    public Page<Jewelry> getJewelriesInWaitList(Pageable pageable) {
        return jewelryRepository.findJewelryInWaitlist(pageable);
    }

    @Override
    public Page<Jewelry> getJewelryPassed(String jewelryName, String category, Pageable pageable) {
        if (category.equals("Tất cả")) {
            return jewelryRepository.getPassedJewelry(jewelryName, null, pageable);
        }
        return jewelryRepository.getPassedJewelry(jewelryName, category, pageable);
    }

    @Override
    public Page<Jewelry> getJewelryByStateAndIsHolding(JewelryState state, Boolean isHolding,String category, String jewelryName, Pageable pageable) {
        if (category.equals("Tất cả"))
            return jewelryRepository.findJewelryByStateAndIsHolding(state,isHolding,null,jewelryName,pageable);
        return jewelryRepository.findJewelryByStateAndIsHolding(state,isHolding,category,jewelryName,pageable);
    }

    @Override
    public Page<Jewelry> getJewelryReturnedViolator(String category, String jewelryName, Pageable pageable) {
        if (category.equals("Tất cả"))
            return jewelryRepository.findJewelryReturnedViolator(null,jewelryName,pageable);
        return jewelryRepository.findJewelryReturnedViolator(category,jewelryName,pageable);
    }

    @Override
    public Page<Jewelry> getJewelriesInHandOver(Pageable pageable) {
        return jewelryRepository.findJewelryInHandOver(pageable);
    }

    @Override
    public Jewelry updateJewelry(JewelryUpdateRequest jewelry) {
        var existingJewelry = jewelryRepository.findById(jewelry.id())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.JEWELRY_NOT_FOUND));

        var category = jewelryCategoryRepository.findByName(jewelry.category())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND));

        existingJewelry.setName(jewelry.name());
        existingJewelry.setBuyNowPrice(jewelry.buyNowPrice());
        existingJewelry.setCategory(category);
        existingJewelry.setDescription(jewelry.description());
        existingJewelry.setMaterial(jewelry.material());
        existingJewelry.setBrand(jewelry.brand());
        existingJewelry.setWeight(jewelry.weight());
        existingJewelry.setCreateDate(jewelry.createDate());

        return existingJewelry;
    }

    @Override
    public Jewelry createJewelry(JewelryCreateRequest request) {
        var username = jwtService.extractUsername(request.token());
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND));

        JewelryCategory category = jewelryCategoryRepository.findByName(request.category())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.CATEGORY_NOT_FOUND));

        Jewelry jewelry = Jewelry.builder()
                .name(request.name())
                .user(user)
                .brand(request.brand())
                .material(JewelryMaterial.valueOf(request.material()))
                .description(request.description())
                .buyNowPrice(request.buyNowPrice())
                .weight(request.weight())
                .state(JewelryState.ACTIVE)
                .createDate(Timestamp.from(Instant.now()))
                .receivedDate(Timestamp.from(Instant.now()))
                .isHolding(true)
                .brand(request.brand())
                .category(category)
                .build();
        jewelryRepository.save(jewelry);
        return jewelry;
    }
}
