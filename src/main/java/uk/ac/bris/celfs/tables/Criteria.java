package uk.ac.bris.celfs.tables;

import java.util.Set;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class Criteria {
    @Column(name = "id")
    @Id
    @GeneratedValue
    private Long id;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    Category category;
    
    @Column(name = "name")
    @NotEmpty
    String criteriaName;
    
    public Long getId() {
        return id;
    }
    
    @OneToMany(targetEntity = CellId.class, mappedBy = "id", fetch = FetchType.LAZY)
    Set<CellId> cellIds;
}
