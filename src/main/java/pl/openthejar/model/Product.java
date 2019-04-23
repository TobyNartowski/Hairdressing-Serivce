package pl.openthejar.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Setter @Getter
public class Product extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProductType type;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    private Service service;

    public Product(ProductType type, Integer quantity) {
        type.addProduct(this);
        this.type = type;
        this.quantity = quantity;
    }
}
