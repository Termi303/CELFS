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
    private Long id;

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;
    
    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    private Teacher() {

    }
    
    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    @OneToMany(targetEntity = MicroResearchReport.class, mappedBy = "id", fetch = FetchType.LAZY)
    Set<MicroResearchReport> microResearchReports;
}