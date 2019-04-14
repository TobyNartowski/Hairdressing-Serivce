package pl.openthejar.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
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
    private long phoneNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Discount> discounts = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Client> persons = new ArrayList<>();

    public void addDiscount(Discount discount) {
        discount.setPerson(this);
        discounts.add(discount);
    }

    private void addLoginCredentials(String login, String hash) {
        this.login = login;
        this.hash = hash;
    }
}
