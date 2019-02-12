package uk.ac.bris.celfs.database;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "cell")
@Data
public class Cell {
    @Id
    @GeneratedValue
    @Column(name = "cell_id")
    private Long id;

    @Column(name = "cell_description")
    @NotEmpty
    String description;

    @ManyToOne
    private Criterion criterion;

    @ManyToOne
    private Band band;

    public Criterion getCriterion() {
        return criterion;
    }

    public String getDescription() {
        return description;
    }

    private Cell() {

    }

    public Cell(Criterion criterion, Band band, String description) {
        this.criterion = criterion;
        this.band = band;
        this.description = description;
    }


}