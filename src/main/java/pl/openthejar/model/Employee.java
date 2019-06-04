package pl.openthejar.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Encja pracownika
 */
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Setter @Getter
public class Employee extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    @Column(nullable = false, unique = true)
    private String login;

    @NonNull
    @Column(nullable = false)
    private String hash;

    @NonNull
    @Column(nullable = false)
    private String firstName;

    @NonNull
    @Column(nullable = false)
    private String lastName;
}
