package uk.ac.bris.celfs.database;

import java.util.Set;
import lombok.Data;

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

    public Long getId() {
        return id;
    }

    private Category() {}

    public Category(String name) {
        this.name = name;
    }
}
