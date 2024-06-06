package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.ImageRequest;
import vn.webapp.backend.auction.model.Image;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.model.User;
import vn.webapp.backend.auction.repository.ImageRepository;
import vn.webapp.backend.auction.repository.JewelryRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService  {
    private final ImageRepository imageRepository;
    private final JewelryRepository jewelryRepository;


    @Override
    public List<Image> getImagesByJewelryId(Integer id) {
        return imageRepository.findImageByJewelryId(id);
    }

    @Override
    public Image getImageByIconAndJewelryId(Integer id) {
        return imageRepository.findImageByIconAndJewelryId(id);
    }

    @Override
    public Image createImage(ImageRequest request) {
        Optional<Jewelry> existJewelry = jewelryRepository.findById(request.jewelryId());
        if (existJewelry.isEmpty()) {
            throw new IllegalArgumentException("Jewelry with ID " + request.jewelryId() + " not found");
        }
        Jewelry jewelry = existJewelry.get();
        Image image = new Image();
        image.setIcon(request.icon());
        image.setData(request.data());
        image.setJewelry(jewelry);
        imageRepository.save(image);
        return image;
    }
}
