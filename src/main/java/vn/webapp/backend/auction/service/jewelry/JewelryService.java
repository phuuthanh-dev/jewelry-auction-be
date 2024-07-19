package vn.webapp.backend.auction.service.jewelry;

import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.webapp.backend.auction.dto.JewelryCreateRequest;
import vn.webapp.backend.auction.dto.JewelryUpdateRequest;
import vn.webapp.backend.auction.dto.SendJewelryFromUserRequest;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.model.Jewelry;

import java.util.List;

public interface JewelryService {
    List<Jewelry> getAll();

    Jewelry getJewelryById(Integer id);

    List<Jewelry> getJewelryByUsername(String username);

    void deleteJewelry(Integer id);

    List<Jewelry> getJewelriesByCategoryId(Integer id);

    Page<Jewelry> getJewelryPassed(String jewelryName, String category, Pageable pageable);

    List<Jewelry> getJewelriesByNameContain(String key);

    Page<Jewelry> getAllJewelries(Pageable pageable);

    Page<Jewelry> getJewelriesManager(JewelryState state, String jewelryName, String category, Pageable pageable);

    Page<Jewelry> getJewelriesInWaitList(Pageable pageable);

    Page<Jewelry> getJewelryByStateAndIsHolding(JewelryState state, Boolean isHolding, String category, String jewelryName, Pageable pageable);

    Page<Jewelry> getJewelryReturnedViolator(String category, String jewelryName, Pageable pageable);

    Page<Jewelry> getJewelriesInHandOver(Pageable pageable);

    Page<Jewelry> getJewelriesByUsername(String username, Pageable pageable);

    Page<Jewelry> getJewelriesActiveByUserId(Integer userId, String jewelryName, Pageable pageable);

    Jewelry requestJewelry(SendJewelryFromUserRequest request);

    Jewelry getLatestJewelry();

    Jewelry updateJewelry(JewelryUpdateRequest jewelry);

    Jewelry createJewelry(JewelryCreateRequest jewelry);

    Jewelry setStateWithHolding(Integer id, boolean isHolding, JewelryState state) throws MessagingException;

}