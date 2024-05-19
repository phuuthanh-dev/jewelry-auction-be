package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.repository.JewelryRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class JewelryService implements IJewelryService {

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
        Jewelry jewelry = getJewelryById(id);
        jewelryRepository.delete(jewelry);
    }
}
