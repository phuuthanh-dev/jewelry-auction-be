package vn.webapp.backend.auction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
