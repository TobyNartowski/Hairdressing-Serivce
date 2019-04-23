package pl.openthejar.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    private Long phoneNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Discount> discounts = new HashSet<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<>();

    public void addDiscount(Discount discount) {
        discount.setClient(this);
        discounts.add(discount);
    }

    public void addReservation(Reservation reservation) {
        reservation.setClient(this);
        reservations.add(reservation);
    }

    private void addLoginCredentials(String login, String hash) {
        this.login = login;
        this.hash = hash;
    }
}
