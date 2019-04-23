package pl.openthejar.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
//@RequiredArgsConstructor
@Setter @Getter
public class Reservation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Client client;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private Service service;

    @Setter(AccessLevel.NONE)
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Review review;

    public void addReview(Review review) {
        review.setReservation(this);
        this.review = review;
    }

    public void addService(Service service) {
        service.setReservation(this);
        this.service = service;
    }
}
