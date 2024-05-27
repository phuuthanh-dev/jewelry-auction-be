package vn.webapp.backend.auction.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name = "request_approval")
public class RequestApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH
    })
    @JoinColumn(name = "staff_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH
    })
    @JoinColumn(name = "jewelry_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Jewelry jewelry;

    @Column(name = "valuation", nullable = false)
    private double valuation;

    @Column(name = "desired_price", nullable = false)
    private double desiredPrice;

    @Column(name = "request_time", nullable = false)
    private Timestamp requestTime;

    @Column(name = "manager_confirm", nullable = false)
    private boolean managerConfirm;

    @Column(name = "member_confirm", nullable = false)
    private boolean memberConfirm;
}
