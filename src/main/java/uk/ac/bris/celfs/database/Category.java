package uk.ac.bris.celfs.database;

import java.util.Set;
import lombok.Data;
import uk.ac.bris.celfs.coursework.Coursework;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "category")
@Data
public class Category {
    @Column(name = "category_id")
    @Id
    @GeneratedValue
    Long id;

    @Column(name = "category_name")
    @NotEmpty
    String categoryName;
    
    @OneToMany(targetEntity = Criteria.class, mappedBy = "id", fetch = FetchType.LAZY)
    Set<Criteria> criterias;

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coursework_id", insertable = false, updatable = false)
    private Coursework coursework;

    public Coursework getCoursework() {
        return coursework;
    }
}
