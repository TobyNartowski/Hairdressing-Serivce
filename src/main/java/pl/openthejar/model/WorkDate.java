package pl.openthejar.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "work_date")
@RequiredArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class WorkDate extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    @Column(nullable = false)
    private Date date;

    @Setter(AccessLevel.NONE)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Employee> employees = new LinkedHashSet<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public WorkDate(Date date, Employee employee) {
        this.date = date;
        addEmployee(employee);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WorkDate) {
            WorkDate objWorkDate = (WorkDate) obj;
            return this.getId().equals(objWorkDate.getId());
        }
        return false;
    }
}
