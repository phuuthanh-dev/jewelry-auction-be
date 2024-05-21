package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.model.Image;
import vn.webapp.backend.auction.repository.ImageRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ImageService implements IImageService  {
    private final ImageRepository imageRepository;


    @Override
    public List<Image> getImagesByJewelryId(Integer id) {
        return imageRepository.findImageByJewelryId(id);
    }

    @Override
    public Image getImageByIconAndJewelryId(Integer id) {
        return imageRepository.findImageByIconAndJewelryId(id);
    }
}
