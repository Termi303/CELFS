package uk.ac.bris.celfs.website;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class Student {

    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = "seat")
    @NotEmpty
    private String seat;
    
    @Column(name = "student_class")
    @NotEmpty
    private String studentClass;

    private Student() {

    }

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "student")
    private MicroResearchReport microResearchReport;

    public String getId() {
        return id;
    }

    public Student(String id, String seat, String studentClass) {
        this.id = id;
        this.seat = seat;
        this.studentClass = studentClass;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Student id == " + getId().toString());
        result.append("; seat == " + seat + "; class == " + studentClass.toString());
        return result.toString();
    }

}