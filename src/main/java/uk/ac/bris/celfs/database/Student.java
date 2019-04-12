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

    public Student(String id, String seat, String studentClass) {
        this.id = id;
        this.seat = seat;
        this.studentClass = studentClass;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Student id == " + id);
        result.append("; seat == " + seat + "; class == " + studentClass);
        return result.toString();
    }

    public String getId() {
        return id;
    }
    public String getSeat() { return seat; }
    public String getStudentClass() { return studentClass; }

}