package uk.ac.bris.celfs.coursework;

import lombok.Data;
import uk.ac.bris.celfs.coursework.CourseworkEntry;
import uk.ac.bris.celfs.database.Band;
import uk.ac.bris.celfs.database.Category;
import uk.ac.bris.celfs.database.Cell;
import uk.ac.bris.celfs.database.Criterion;
import uk.ac.bris.celfs.factory.DataFactory;

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
    private CategoryEntry categoryEntry;

    @ManyToOne
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

    public int getBandIndex() {
        String bandName = cell.getBand().getName();
        for(int i = 0; i < DataFactory.bandNames.length; i++) {
            if(bandName.equals(DataFactory.bandNames[i]))
                return i;
        }
        return -1;
    }

    public CategoryEntry getCategoryEntry() { return categoryEntry; }
    public Cell getCell() { return cell; }
    public String getComment() { return comment; }
    public Long getId() { return id; }
}