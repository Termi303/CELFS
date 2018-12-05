package uk.ac.bris.celfs.tables;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "micro_research_report_cells")
@Data
public class Cell {
    @EmbeddedId
    CellId id;
    
    @Column(name = "text")
    @NotEmpty
    String description;
}