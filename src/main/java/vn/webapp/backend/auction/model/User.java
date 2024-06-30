package vn.webapp.backend.auction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.webapp.backend.auction.enums.AccountState;
import vn.webapp.backend.auction.enums.Role;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "[user]")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @JsonIgnore
    @NotBlank
    @Column(name = "password")
    private String password;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "The first name is required")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "The last name is required")
    private String lastName;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    @NotBlank(message = "The email is required")
    private String email;

    @Column(name = "phone", nullable = false, length = 15)
    @NotBlank(message = "The phone is required")
    private String phone;

    @Column(name = "address", nullable = false)
    @NotBlank(message = "The address is required")
    private String address;

    @Column(name = "city", nullable = false)
    @NotBlank(message = "The city is required")
    private String city;

    @Column(name = "district", nullable = false)
    @NotBlank(message = "The district is required")
    private String district;

    @Column(name = "ward", nullable = false)
    @NotBlank(message = "The ward is required")
    private String ward;

    @Column(name = "year_of_birth", nullable = false, length = 4)
    @NotBlank(message = "The year of birth is required")
    private String yob;

    @Column(name = "avatar")
    @Lob
    private String avatar;

    @Column(name = "CCCD", nullable = false, length = 20)
    @NotBlank(message = "The CCCD is required")
    private String cccd;

    @Column(name = "CCCD_first")
    @Lob
    private String cccdFirst;

    @Column(name = "CCCD_last")
    @Lob
    private String cccdLast;

    @Column(name = "CCCD_from", length = 50)
    private String cccdFrom;

    @Column(name = "register_date")
    private Timestamp registerDate;

    @Enumerated(EnumType.STRING)
    @Column(name ="state" , nullable = false, length = 10)
    private AccountState state;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Bank bank;

    @Column(name ="bank_account_number", nullable = false, length = 30)
    private String bankAccountNumber;

    @Column(name ="bank_account_name", nullable = false, length = 30)
    private String bankAccountName;


    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Token> tokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Jewelry> jewelries;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
