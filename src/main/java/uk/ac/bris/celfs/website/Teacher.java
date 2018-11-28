package uk.ac.bris.celfs.website;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class Teacher {

    @Id
    @GeneratedValue
    Long id;

    @Column
    @NotEmpty
    String firstName;
    
    @Column
    @NotEmpty
    String lastName;
}