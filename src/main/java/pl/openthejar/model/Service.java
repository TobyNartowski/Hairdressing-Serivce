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
public class Service extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @NonNull
    @Column(nullable = false)
    private Integer duration;

    @NonNull
    @Column(nullable = false)
    private Integer price;

    @OneToOne
    private Reservation reservation;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Product> products = new HashSet<>();

    public void setProducts(Set<Product> products) {
        products.forEach(p -> p.setService(this));
        this.products = products;
    }
}
