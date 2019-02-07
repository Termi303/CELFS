package uk.ac.bris.celfs.database;

import java.util.Set;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "criteria")
@Data
public class Criterion {
    @Column(name = "criterion_id")
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;
    
    @Column(name = "criterion_name")
    @NotEmpty
    private String name;

    public Criterion(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getCriterionName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Long getId() {
        return id;
    }
    
    @OneToMany(targetEntity = Cell.class, mappedBy = "id", fetch = FetchType.LAZY)
    Set<Cell> cells;
}
