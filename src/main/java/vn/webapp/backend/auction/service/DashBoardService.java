package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.dto.DashBoardResponse;
import vn.webapp.backend.auction.repository.AuctionRepository;
import vn.webapp.backend.auction.repository.BankRepository;
import vn.webapp.backend.auction.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class DashBoardService {
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;


    public DashBoardResponse getInformation () {
        Integer totalUser = userRepository.getTotalUser();
        Integer totalAuction = auctionRepository.countAllAuctions();

        return DashBoardResponse.builder()
                .totalUser(totalUser)
                .totalAuctions(totalAuction).build();
    }
}
