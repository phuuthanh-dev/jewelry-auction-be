package vn.webapp.backend.auction.service;

import vn.webapp.backend.auction.dto.ImageRequest;
import vn.webapp.backend.auction.model.Image;
import vn.webapp.backend.auction.model.Jewelry;

import java.util.List;

public interface ImageService {
    List<Image> getImagesByJewelryId(Integer id);
    Image getImageByIconAndJewelryId(Integer id);
    Image createImage(ImageRequest request);
}
