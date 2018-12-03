package uk.ac.bris.celfs.tables;

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
    Long id;
    
    @Column(name = "name")
    @NotEmpty
    String name;
}
