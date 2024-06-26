package vn.webapp.backend.auction.service.image;

import vn.webapp.backend.auction.dto.ImageRequest;
import vn.webapp.backend.auction.model.Image;

import java.util.List;

public interface ImageService {
    List<Image> getImagesByJewelryId(Integer id);
    Image getImageByIconAndJewelryId(Integer id);
    Image createImage(ImageRequest request);
    void deleteByJewelryId(Integer id);
}
