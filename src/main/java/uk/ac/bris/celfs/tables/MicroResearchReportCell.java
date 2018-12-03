package uk.ac.bris.celfs.tables;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class MicroResearchReportCell {
    @Column(name = "id")
    @Id
    @GeneratedValue
    Long id;
    
    /*@ManyToOne
    @JoinColumns({
        @JoinColumn(
                name = "category_id",
                referencedColumnName = "id"
        )
    })
    MicroResearchReportCategory category;*/
    
    @Column(name = "text")
    @NotEmpty
    String description;
    
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
                name = "band_id",
                referencedColumnName = "id"
        )
    })
    Band band;
}