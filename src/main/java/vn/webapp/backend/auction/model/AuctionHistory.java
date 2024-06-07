package vn.webapp.backend.auction.model;

import jakarta.persistence.*;
import lombok.*;
import vn.webapp.backend.auction.enums.AuctionHistoryState;
import vn.webapp.backend.auction.enums.AuctionState;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "auction_history")
public class AuctionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "price_given", nullable = false)
    private double priceGiven;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    @Column(name = "bid_code", nullable = false, length = 20)
    private String bidCode;

    @Enumerated(EnumType.STRING)
    private AuctionHistoryState state;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH
    })
    @JoinColumn(name = "auction_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Auction auction;
}
