package vn.webapp.backend.auction.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.webapp.backend.auction.enums.PaymentMethod;
import vn.webapp.backend.auction.enums.TransactionState;
import vn.webapp.backend.auction.enums.TransactionType;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "[transaction]")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @Column(name = "payment_time")
    private Timestamp paymenTime;

    @Column(name = "total_price", nullable = false)
    @Min(value = 1, message = "The total price must be at least 1")
    private Double totalPrice;

    @Column(name = "fees_incurred", nullable = false)
    @Min(value = 0, message = "The fees incurred must be at least 0")
    private Double feesIncurred;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_state", nullable = false)
    private TransactionState state;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH
    })
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name ="payment_method", nullable = true, length = 20)
    private PaymentMethod paymentMethod;
}
