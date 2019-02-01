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
    @Column(name = "cell_id")
    private Long id;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "criteria_id", insertable = false, updatable = false)
    private Criteria criteria;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "band_id", insertable = false, updatable = false)
    private Band band;

    public Criteria getCriteria() {
        return criteria;
    }

    public String getDescription() {
        return description;
    }

    private Cell() {

    }

    public Cell(Criteria criteria, Band band, String description) {
        this.criteria = criteria;
        this.band = band;
        this.description = description;
        this.id = new Long( hashCode() );
    }

    public int hashCode() {
        return Objects.hash(criteria.getId(), band.getId());
    }

    @Column(name = "cell_description")
    @NotEmpty
    String description;
}