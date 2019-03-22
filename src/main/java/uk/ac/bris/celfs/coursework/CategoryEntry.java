package uk.ac.bris.celfs.coursework;

import lombok.Data;
import uk.ac.bris.celfs.database.Category;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "category_entry")
@Data
public class CategoryEntry {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @ManyToOne
    @NotEmpty
    private CourseworkEntry courseworkEntry;

    @ManyToOne
    @NotEmpty
    private Category category;

    @Column(name = "category_mark")
    private Integer mark;

    public CategoryEntry(CourseworkEntry courseworkEntry, Category category, Integer mark) {
        this.courseworkEntry = courseworkEntry;
        this.category = category;
        this.mark = mark;
    }

    public Category getCategory() {
        return category;
    }

    public CourseworkEntry getCourseworkEntry() {
        return courseworkEntry;
    }

    public Integer getMark() {
        return mark;
    }
}
