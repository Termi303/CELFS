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
    @Column(name = "band_id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "band_name")
    @NotEmpty
    private String name;

    @Column(name = "band_description")
    private String description;

    private Band() {
    }
    
    public Band(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    

}
