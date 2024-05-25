package vn.webapp.backend.auction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "bank_name", unique = true, nullable = false, columnDefinition = "nvarchar(100)")
    private String bankName;

    @Column(name = "trading_name", unique = true, nullable = false, columnDefinition = "nvarchar(20)")
    private String tradingName;

    @OneToMany(mappedBy = "bank")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<User> users;
}
