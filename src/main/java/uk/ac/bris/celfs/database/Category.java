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
    Long id;

    @Column(name = "category_name")
    @NotEmpty
    String categoryName;
    
    @OneToMany(targetEntity = Criteria.class, mappedBy = "id", fetch = FetchType.LAZY)
    Set<Criteria> criterias;
}
