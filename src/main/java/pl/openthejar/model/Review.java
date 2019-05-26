package pl.openthejar.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Setter @Getter
public class Review extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    @Column(nullable = false)
    private String title;

    @NonNull
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date date = new Date(new Timestamp(System.currentTimeMillis()).getTime());
}
