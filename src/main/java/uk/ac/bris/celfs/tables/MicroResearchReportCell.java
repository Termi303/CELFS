package uk.ac.bris.celfs.tables;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "micro_research_report_cells")
@Data
public class MicroResearchReportCell {
    @EmbeddedId
    MicroResearchReportCellId id;
    
    @Column(name = "text")
    @NotEmpty
    String description;
}