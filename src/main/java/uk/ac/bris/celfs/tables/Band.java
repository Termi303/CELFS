package uk.ac.bris.celfs.tables;

import java.util.HashSet;
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
    
    public Band() {
        cellIds = new HashSet<>();
    }
    
    public Band(String name) {
        this.name = name;
        cellIds = new HashSet<>();
    }
    
    @Column(name = "name")
    @NotEmpty
    private String name;
    
    @OneToMany(targetEntity = MicroResearchReportCellId.class, mappedBy = "id", fetch = FetchType.LAZY)
    Set<MicroResearchReportCellId> cellIds;
}
