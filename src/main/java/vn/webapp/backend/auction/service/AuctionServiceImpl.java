package vn.webapp.backend.auction.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.enums.AuctionState;
import vn.webapp.backend.auction.enums.JewelryState;
import vn.webapp.backend.auction.exception.ResourceNotFoundException;
import vn.webapp.backend.auction.model.Auction;
import vn.webapp.backend.auction.model.Jewelry;
import vn.webapp.backend.auction.repository.AuctionRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService{

    private final AuctionRepository auctionRepository;

    @Override
    public List<Auction> getAll() {
        return auctionRepository.findAll();
    }

    @Override
    public Auction getAuctionById(Integer id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phiên đấu giá này không tồn tại"));
    }

    @Override
    public void deleteAuction(Integer id) {
        Auction auction = getAuctionById(id);
        auction.setState(AuctionState.DELETED);
    }

    @Override
    public List<Auction> findTop3AuctionsByPriceAndState(List<AuctionState> states) {
        return auctionRepository.findTop3ByStateInOrderByFirstPriceDesc(states);
    }

    @Override
    public List<Auction> findAuctionSortByBetweenStartdayAndEndday(String startDay, String endDay) {
        LocalDate localDate1 = LocalDate.parse(startDay);
        LocalDate localDate2 = LocalDate.parse(endDay);

        Timestamp timestampStartDate1 = Timestamp.valueOf(localDate1.atStartOfDay());

        LocalDateTime endOfDay = LocalDateTime.of(localDate2, LocalTime.MAX);
        Timestamp timestampEndDate2 = Timestamp.valueOf(endOfDay);


        return auctionRepository.findAuctionSortByBetweenStartdayAndEndday(timestampStartDate1, timestampEndDate2);
    }

    @Override
    public Page<Auction> getByStaffID(Integer id, Pageable pageable) {
        return auctionRepository.findByStaffID(id, pageable);
    }

    @Override
    public List<Auction> getAuctionByJewelryId(Integer id) {
        return auctionRepository.findAuctionByJewelryId(id);
    }


    @Override
    public List<Auction> findAuctionByName(String name) {
        return auctionRepository.findAuctionByNameContaining(name);
    }

    @Override
    public Page<Auction> getAllAuctions(AuctionState state, Pageable pageable, Integer categoryId) {
        return auctionRepository.findByStateAndCategoryNotDeletedOrEmptyState(state, pageable, categoryId);
    }

    @Override
    public void setAuctionState(Integer id, String state) {
        var existingAuction = auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiên đấu giá."));
        existingAuction.setState(AuctionState.valueOf(state));
    }

    @Override
    public List<Auction> getAuctionByState(AuctionState state) {
        return auctionRepository.findByState(state);
    }

//    @Override
//    public List<Auction> findTodayAuctions() {
//        // Lấy ngày hiện tại
//        LocalDate today = LocalDate.now();
//
//        // Tính toán đầu và cuối ngày hiện tại
//        LocalDateTime startOfDay = today.atStartOfDay();
//        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
//
//        // Chuyển đổi LocalDateTime sang Timestamp
//        Timestamp startTimestamp = Timestamp.valueOf(startOfDay);
//        Timestamp endTimestamp = Timestamp.valueOf(endOfDay);
//
//        // Gọi phương thức repository
//        return auctionRepository.findAuctionSortByBetweenStartdayAndEndday(startTimestamp, endTimestamp);
//    }

    @Override
    public Page<Auction> getAuctionsByStates(List<AuctionState> states, Pageable pageable) {
        return auctionRepository.findByStateIn(states, pageable);
    }
}
