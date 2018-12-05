package uk.ac.bris.celfs.website;

import java.util.Set;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "teacher")
@Data
public class Teacher {

    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @Column(name = "first_name")
    @NotEmpty
    String firstName;
    
    @Column(name = "last_name")
    @NotEmpty
    String lastName;
    
    @OneToMany(targetEntity = MicroResearchReport.class, mappedBy = "id", fetch = FetchType.LAZY)
    Set<MicroResearchReport> microResearchReports;
}