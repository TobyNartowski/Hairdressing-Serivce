package pl.openthejar.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Encja klienta
 */
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

    /**
     * Dodaje znizke
     * @param discount Znizka do dodania
     */
    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    /**
     * Dodaje parametry logowania
     * @param login Login do dodania
     * @param hash Hash do dodania
     */
    private void addLoginCredentials(String login, String hash) {
        this.login = login;
        this.hash = hash;
    }
}
