package uk.ac.bris.celfs.website;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table
@Data
public class MrrRaw {

    @Id
    @GeneratedValue
    Long id;
    
    @Column
    @NotEmpty
    String studentID;
}