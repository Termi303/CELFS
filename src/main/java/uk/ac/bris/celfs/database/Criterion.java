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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Category category;

    @Column(name = "criterion_name")
    @NotEmpty
    private String name;

    public Criterion(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    private Criterion() {}

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Long getId() {
        return id;
    }
}
