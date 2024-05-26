package vn.webapp.backend.auction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.JewelryCategory;
import vn.webapp.backend.auction.repository.JewelryCategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JewelryCategoryServiceImpl implements JewelryCategoryService {

    private final JewelryCategoryRepository jewelryCategoryRepository;

    @Override
    public List<JewelryCategory> getAll() {
        return jewelryCategoryRepository.findAll();
    }

    @Override
    public JewelryCategory getById(int id) {
        return jewelryCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục sản phẩm hiện không tồn tại"));
    }

    @Override
    public JewelryCategory saveJewelryCategory(JewelryCategory jewelryCategory) {
        jewelryCategory.setId(0);
        return jewelryCategoryRepository.save(jewelryCategory);
    }

    @Override
    public void deleteJewelryCategory(Integer id) {
            var existingBookCategory = jewelryCategoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục trang sức để xóa"));
            existingBookCategory.getJewelries()
                    .forEach((book) -> book.setCategory(null));
        jewelryCategoryRepository.deleteById(id);
    }
}
