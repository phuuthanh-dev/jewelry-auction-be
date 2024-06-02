package vn.webapp.backend.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.webapp.backend.auction.model.Transaction;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionResponse  {
    Double totalPriceJewelryWonByUsername;
    Integer numberTransactionsRequest;
    Integer totalJewelryWon;
    Integer totalBid;
}
