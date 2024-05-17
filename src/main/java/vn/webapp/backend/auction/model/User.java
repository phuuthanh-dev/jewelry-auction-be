package vn.webapp.backend.auction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.webapp.backend.auction.enums.AccountState;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "first_name", nullable = false, columnDefinition = "nvarchar(50)")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "nvarchar(50)")
    private String lastName;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "address", nullable = false, columnDefinition = "nvarchar(50)")
    private String address;

    @Column(name = "city", nullable = false, columnDefinition = "nvarchar(20)")
    private String city;

    @Column(name = "province", nullable = false, columnDefinition = "nvarchar(20)")
    private String province;

    @Column(name = "year_of_birth", nullable = false, length = 30)
    private int yob;

    @Column(name = "avatar", columnDefinition = "varchar(MAX)")
    @Lob
    private String avatar;

    @Column(name = "CCCD", nullable = false, length = 20)
    private String CCCD;

    @Enumerated(EnumType.STRING)
    @Column(name ="state" , nullable = false, length = 10)
    private AccountState state;

    @Column(name = "activation_code", nullable = false, length = 50)
    private String activationCode;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Jewelry> jewelries;

    @OneToMany(mappedBy = "user")
    private List<RequestApproval> requestApprovals;
}
