package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.repository.JewelryRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class JewelryServiceImpl implements JewelryService {

    private final JewelryRepository jewelryRepository;

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
    public List<Jewelry> getJewelriesInWaitList() {
        return jewelryRepository.findJewelryInWaitlist();
    }

    @Override
    public List<Jewelry> getJewelriesInHandOver() {
        return jewelryRepository.findJewelryInHandOver();
    }
}
