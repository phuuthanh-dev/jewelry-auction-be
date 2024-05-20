package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.AuctionHistory;
import vn.webapp.backend.auction.repository.AuctionHistoryRepository;
import vn.webapp.backend.auction.repository.AuctionRepository;
import vn.webapp.backend.auction.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuctionHistoryService implements IAuctionHistoryService {

    private final AuctionHistoryRepository auctionHistoryRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    @Override
    public List<AuctionHistory> getAuctionHistoryByAuctionId(Integer auctionId) {
        var existingAuction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiên đấu giá."));
        return auctionHistoryRepository.findByAuctionId(existingAuction.getId());
    }

    @Override
    public List<AuctionHistory> getAuctionHistoryByUsername(String username) {
        var existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng."));
        return auctionHistoryRepository.findByUsername(existingUser.getUsername());
    }
}
