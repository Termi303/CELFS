package uk.ac.bris.celfs.database;

import lombok.Data;
import uk.ac.bris.celfs.coursework.CourseworkEntry;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "student")
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