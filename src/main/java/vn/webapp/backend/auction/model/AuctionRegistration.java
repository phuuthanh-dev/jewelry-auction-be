package vn.webapp.backend.auction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.webapp.backend.auction.enums.AuctionRegistrationState;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "auction_registration")
public class AuctionRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "registration_fee", nullable = false)
    private double registrationFee;

    @Column(name = "registration_date", nullable = false)
    private Timestamp registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "auction_registration_state", nullable = false)
    private AuctionRegistrationState auctionRegistrationState;

    @Column(name = "kick_reason")
    private String kickReason;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH
    })
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
