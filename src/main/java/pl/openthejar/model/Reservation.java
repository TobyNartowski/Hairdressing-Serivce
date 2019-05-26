package pl.openthejar.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Setter @Getter
public class Reservation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Service service;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Review review;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private WorkDate workDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Client client;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean done = false;
}
