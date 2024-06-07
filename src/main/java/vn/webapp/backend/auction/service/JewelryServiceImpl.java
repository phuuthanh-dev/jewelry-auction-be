package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.SendJewelryFromUserRequest;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.JewelryRepository;
import vn.webapp.backend.auction.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class JewelryServiceImpl implements JewelryService {

    private final JewelryRepository jewelryRepository;
    private final UserRepository userRepository;

    @Override
    public List<Jewelry> getAll() {
        return jewelryRepository.findAll();
    }

    @Override
    public Jewelry getJewelryById(Integer id) {
        return jewelryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm hiện không tồn tại"));
    }

    @Override
    public Page<Jewelry> getJewelriesByUsername(String username, Pageable pageable) {
        return jewelryRepository.findByUserUsername(username, pageable);
    }

    @Override
    public Jewelry requestJewelry(SendJewelryFromUserRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.userId());

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + request.userId() + " not found");
        }

        User user = optionalUser.get();

        Jewelry jewelry = new Jewelry();
        jewelry.setUser(user);
        jewelry.setPrice(request.price());
        jewelry.setDescription(request.description());
        jewelry.setMaterial(request.material());
        jewelry.setWeight(request.weight());
        jewelry.setBrand(request.brand());
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
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy trang sức để xóa"));
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
    public Page<Jewelry> getJewelriesInHandOver(Pageable pageable) {
        return jewelryRepository.findJewelryInHandOver(pageable);
    }
}
