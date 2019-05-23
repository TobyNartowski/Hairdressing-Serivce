package pl.openthejar.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Client extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private String login;

    private String hash;

    @NonNull
    @Column(nullable = false)
    private String firstName;

    @NonNull
    @Column(nullable = false)
    private String lastName;

    @NonNull
    @Column(nullable = false)
    private Long phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Discount> discounts = new HashSet<>();

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    private void addLoginCredentials(String login, String hash) {
        this.login = login;
        this.hash = hash;
    }
}
