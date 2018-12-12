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
    private Long id;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @OneToMany(targetEntity = Cell.class, mappedBy = "band", fetch = FetchType.LAZY)
    private Set<Cell> cells = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    private Band() {
    }
    
    public Band(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Set<Cell> getCells() {
        return cells;
    }
    

}
