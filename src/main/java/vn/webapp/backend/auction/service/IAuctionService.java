package vn.webapp.backend.auction.service;

import vn.webapp.backend.auction.model.Auction;

import java.util.List;

public interface IAuctionService {
    List<Auction> getAll();
    Auction getAuctionById(Integer id);
    void deleteAuction(Integer id);
}
