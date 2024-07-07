package vn.webapp.backend.auction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import vn.webapp.backend.auction.enums.JewelryState;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jewelry")
public class Jewelry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "The price required")
    private String name;

    @Min(value = 1, message = "The price must be at least 1")
    private Double price;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "material", nullable = false)
    private String material;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "is_holding", nullable = true)
    private Boolean isHolding;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private JewelryState state;

    @OneToMany(mappedBy = "jewelry", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Image> images;

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
    @JoinColumn(name = "category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private JewelryCategory category;

    @OneToMany(mappedBy = "jewelry", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<RequestApproval> requestApprovals;
}
