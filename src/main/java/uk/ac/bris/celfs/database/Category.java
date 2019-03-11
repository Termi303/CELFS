package uk.ac.bris.celfs.database;

import java.util.Set;
import lombok.Data;
import uk.ac.bris.celfs.coursework.Coursework;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @Column(name = "category_weight")
    @NotNull
    private Float weight;

    @ManyToOne
    private Coursework coursework;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coursework getCoursework() {
        return coursework;
    }

    private Category() {}

    public Category(String name, Coursework coursework, Float weight) {
        this.name = name;
        this.coursework = coursework;
        this.weight = weight;
    }
}
