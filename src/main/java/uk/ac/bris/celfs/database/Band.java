package uk.ac.bris.celfs.database;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "band")
@Data
public class Band {
    @Column(name = "id")
    @Id
    @GeneratedValue
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
    
    public Band(String name) {
        this.id = id;
        this.name = name;
    }
    
    public Set<Cell> getCells() {
        return cells;
    }
    

}
