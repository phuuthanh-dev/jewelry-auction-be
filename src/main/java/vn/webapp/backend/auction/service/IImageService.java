package vn.webapp.backend.auction.service;

import vn.webapp.backend.auction.model.Image;
import vn.webapp.backend.auction.model.Jewelry;

import java.util.List;

public interface IImageService {
    List<Image> getImagesByJewelryId(Integer id);
    Image getImageByIconAndJewelryId(Integer id);
}
