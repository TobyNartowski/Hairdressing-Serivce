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
public class ProductType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @NonNull
    @Column(nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Product> products = new HashSet<>();

    public void addProduct(Product product) {
        products.add(product);
    }
}
