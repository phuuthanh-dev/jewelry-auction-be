package vn.webapp.backend.auction.model;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(50)")
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "description", nullable = false, columnDefinition = "nvarchar(MAX)")
    private String description;

    @Column(name = "material", nullable = false, columnDefinition = "nvarchar(20)")
    private String material;

    @Column(name = "brand", nullable = false, columnDefinition = "nvarchar(20)")
    private String brand;

    @Column(name = "weight", nullable = false)
    private double weight;

    @Column(name = "status", nullable = false, columnDefinition = "nvarchar(20)")
    private String status;

    @OneToMany(mappedBy = "jewelry")
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
    private JewerlyCategory category;

    @OneToMany(mappedBy = "jewelry")
    private List<RequestApproval> requestApprovals;
}
