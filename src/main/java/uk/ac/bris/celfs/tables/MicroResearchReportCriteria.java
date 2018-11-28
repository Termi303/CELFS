package uk.ac.bris.celfs.tables;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class MicroResearchReportCriteria {
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
    
    @Column(name = "name")
    @NotEmpty
    String categoryName;
}
