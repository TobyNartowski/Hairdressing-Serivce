package pl.openthejar.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Encja uslugi
 */
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

    @NonNull
    @Column(nullable = false)
    private Integer need;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Product> products = new ArrayList<>();
}
