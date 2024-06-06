package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.DashBoardResponse;
import vn.webapp.backend.auction.repository.AuctionRepository;
import vn.webapp.backend.auction.repository.BankRepository;
import vn.webapp.backend.auction.repository.JewelryRepository;
import vn.webapp.backend.auction.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class DashBoardService {
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final JewelryRepository jewelryRepository;


    public DashBoardResponse getInformation () {
        Integer totalUser = userRepository.getTotalUser();
        Integer totalAuction = auctionRepository.countAllAuctions();
        Integer totalJewelryActive = jewelryRepository.countAllJewelriesActive();

        return DashBoardResponse.builder()
                .totalUser(totalUser)
                .totalJewelryActive(totalJewelryActive)
                .totalAuctions(totalAuction).build();
    }
}
