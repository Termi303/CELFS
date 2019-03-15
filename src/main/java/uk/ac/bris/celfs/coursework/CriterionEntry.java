package uk.ac.bris.celfs.coursework;

import lombok.Data;
import uk.ac.bris.celfs.coursework.CourseworkEntry;
import uk.ac.bris.celfs.database.Category;
import uk.ac.bris.celfs.database.Criterion;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "criterion_entry")
@Data
public class CriterionEntry {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @ManyToOne
    @NotEmpty
    private CategoryEntry categoryEntry;

    @ManyToOne
    @NotEmpty
    private Criterion criterion;

    @Column(name = "criterion_entry_comment")
    private String comment;

    public CriterionEntry(Criterion criterion, CategoryEntry categoryEntry) {
        this.criterion = criterion;
        this.categoryEntry = categoryEntry;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}