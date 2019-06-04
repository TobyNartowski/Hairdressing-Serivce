package pl.openthejar.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Encja typu produktu
 */
@Entity
@Table(name = "product_type")
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
}
