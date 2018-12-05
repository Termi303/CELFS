package uk.ac.bris.celfs.tables;

import java.util.Set;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class Band {
    @Column(name = "id")
    @Id
    @GeneratedValue
    private Long id;
    
    public Long getId() {
        return id;
    }
    
    @Column(name = "name")
    @NotEmpty
    String name;
    
    @OneToMany(targetEntity = MicroResearchReportCellId.class, mappedBy = "id", fetch = FetchType.LAZY)
    Set<MicroResearchReportCellId> cellIds;
}
