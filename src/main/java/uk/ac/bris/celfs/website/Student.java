package uk.ac.bris.celfs.website;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class Student {

    @Id
    @GeneratedValue
    Long id;

    @Column
    @NotEmpty
    String firstName;
    
    @Column
    @NotEmpty
    String lastName;
    
    @Column
    @NotEmpty
    String seat;
    
    @Column
    @NotEmpty
    String studentClass;
}