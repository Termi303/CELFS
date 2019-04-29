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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cell_id")
    private Long id;

    @Column(name = "cell_description")
    @NotEmpty
    String description;

    @ManyToOne
    private Criterion criterion;

    @ManyToOne
    private Band band;

    private Cell() {

    }

    public Cell(Criterion criterion, Band band, String description) {
        this.criterion = criterion;
        this.band = band;
        this.description = description;
    }

    public Long getId() { return id; }
    public Criterion getCriterion() {
        return criterion;
    }
    public Band getBand() { return band; }
    public String getDescription() {
        return description;
    }
}
