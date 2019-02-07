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
    

}
