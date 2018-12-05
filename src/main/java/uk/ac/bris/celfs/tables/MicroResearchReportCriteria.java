package uk.ac.bris.celfs.tables;

import java.util.Set;
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
    private Long id;
    
    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    MicroResearchReportCategory category;
    
    @Column(name = "name")
    @NotEmpty
    String criteriaName;
    
    public Long getId() {
        return id;
    }
    
    @OneToMany(targetEntity = MicroResearchReportCellId.class, mappedBy = "id", fetch = FetchType.LAZY)
    Set<MicroResearchReportCellId> cellIds;
}
