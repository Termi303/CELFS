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
    private Long id;

    @Column(name = "category_name")
    @NotEmpty
    private String name;
    
    @OneToMany(targetEntity = Criteria.class, mappedBy = "id", fetch = FetchType.LAZY)
    Set<Criteria> criteria;

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coursework_id", insertable = false, updatable = false)
    private Coursework coursework;

    public Coursework getCoursework() {
        return coursework;
    }

    public Long getId() {
        return id;
    }

    public Category(String name, Coursework coursework) {
        this.name = name;
        this.coursework = coursework;
    }
}
