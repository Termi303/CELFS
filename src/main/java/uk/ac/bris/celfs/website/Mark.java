package uk.ac.bris.celfs.website;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class Mark {

    @Id
    @GeneratedValue
    Long id;

    @Column
    @NotEmpty
    String feedback;
}
