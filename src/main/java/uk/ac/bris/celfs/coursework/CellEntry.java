package uk.ac.bris.celfs.coursework;

import lombok.Data;
import uk.ac.bris.celfs.coursework.CourseworkEntry;
import uk.ac.bris.celfs.database.Category;
import uk.ac.bris.celfs.database.Cell;
import uk.ac.bris.celfs.database.Criterion;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "cell_entry")
@Data
public class CellEntry {
    @Id
    @GeneratedValue
    @Column(name = "cell_entry_id")
    private Long id;

    @ManyToOne
    @NotEmpty
    private CategoryEntry categoryEntry;

    @ManyToOne
    @NotEmpty
    private Cell cell;

    @Column(name = "cell_entry_comment")
    private String comment;

    public CellEntry(Cell cell, CategoryEntry categoryEntry) {
        this.cell = cell;
        this.categoryEntry = categoryEntry;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CategoryEntry getCategoryEntry() { return categoryEntry; }
    public Cell getCell() { return cell; }
    public String getComment() { return comment; }
    public Long getId() { return id; }
}