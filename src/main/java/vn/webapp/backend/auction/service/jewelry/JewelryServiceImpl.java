package vn.webapp.backend.auction.service.jewelry;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.SendJewelryFromUserRequest;
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
        jewelry.setIsHolding(false);
        jewelry.setName(request.name());
        jewelry.setState(JewelryState.APPROVING);

        jewelryRepository.save(jewelry);

        return jewelry;
    }

    @Override
    public Jewelry getLatestJewelry() {
        return jewelryRepository.findLatestJewelry().get(0);
    }

    @Override
    public Jewelry setHolding(Integer id,boolean state) throws MessagingException {
        var existingJewelry = jewelryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.JEWELRY_NOT_FOUND));
        var exitingUser = existingJewelry.getUser();
        existingJewelry.setIsHolding(state);
        if(state) {
            emailService.sendConfirmHoldingEmail(exitingUser.getEmail(), exitingUser.getFullName(), existingJewelry.getName());
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
    public List<Jewelry> getJeweriesByCategoryId(Integer id) {
        return jewelryRepository.findJewelryByCategoryId(id);
    }

    @Override
    public List<Jewelry> getJeweriesByNameContain(String key) {
        return jewelryRepository.getJewelriesByNameContaining(key);
    }

    @Override
    public Page<Jewelry> getAllJeweries(Pageable pageable) {
        return jewelryRepository.findByState(JewelryState.ACTIVE, pageable);
    }

    @Override
    public Page<Jewelry> getJewelriesInWaitList(Pageable pageable) {
        return jewelryRepository.findJewelryInWaitlist(pageable);
    }

    @Override
    public Page<Jewelry> getJewelryByStateAndIsHolding(JewelryState state, Boolean isHolding, String jewelryName, Pageable pageable) {
        return jewelryRepository.findJewelryByStateAndIsHolding(state,isHolding,jewelryName,pageable);
    }

    @Override
    public Page<Jewelry> getJewelriesInHandOver(Pageable pageable) {
        return jewelryRepository.findJewelryInHandOver(pageable);
    }
}
