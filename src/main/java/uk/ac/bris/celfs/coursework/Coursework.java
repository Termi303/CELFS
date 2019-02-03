package uk.ac.bris.celfs.coursework;

import lombok.Data;
import uk.ac.bris.celfs.database.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coursework")
@Data
public class Coursework {
    @Id
    @GeneratedValue
    @Column(name = "coursework_id")
    private Long id;

    @Column(name = "coursework_name")
    private String name;

    @OneToMany(targetEntity = CourseworkEntry.class, mappedBy = "id", fetch = FetchType.LAZY)
    private Set<CourseworkEntry> courseworkEntries;

    @OneToMany(targetEntity = Category.class, mappedBy = "id", fetch = FetchType.LAZY)
    private Set<Category> categories;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coursework() {}

    public Coursework(String name) {
        this.name = name;
        courseworkEntries = new HashSet<>();
        categories = new HashSet<>();
    }
}
