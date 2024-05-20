package vn.webapp.backend.auction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.JewelryCategory;
import vn.webapp.backend.auction.repository.JewelryCategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JewelryCategoryService implements IJewelryCategoryService {

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
}
