package pl.openthejar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Encja rezerwacji
 */
@Entity
@NoArgsConstructor
@Setter @Getter
public class Reservation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Service service;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Review review;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private WorkDate workDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Client client;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean done = false;
}
