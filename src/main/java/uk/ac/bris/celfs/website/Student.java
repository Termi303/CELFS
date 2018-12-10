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
    @Column(name = "id")
    private Long id;
    
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
        return numberToId();
    }

    public Long getRawId() { return id; }

    public Student(String id, String seat, String studentClass) {
        try {
            this.id = Student.idToNumber(id);
        } catch(IllegalArgumentException e) {
            System.out.println(e);
            System.exit(1);
        }
        this.seat = seat;
        this.studentClass = studentClass;
    }

    private String numberToId() {
        StringBuilder result = new StringBuilder();
        result.append( (char)( id / 1e7 + 'a') );
        result.append( (char)( ((id / 1e5) % 100) + 'a') );
        for(int i = 10000; i > 0; i /= 10) {
            result.append( (id / i) % 10 );
        }
        return result.toString();
    }

    public static Long idToNumber(String studentId) throws IllegalArgumentException {
        Long result = 0L;

        //Check id correctness
        if(studentId.length() != 7) throw new IllegalArgumentException("Wrong length of id: must be 7, is " + studentId.length() + "; id == " + studentId);
        for(int i = 0; i < 2; i++) {
            if(!Character.isLowerCase(studentId.charAt(i))) {
                throw new IllegalArgumentException("First two characters of id must be lowercase characters: " + studentId);
            }
        }
        for(int i = 3; i < 7; i++) {
            if(!Character.isDigit(studentId.charAt(i))) {
                throw new IllegalArgumentException("Last 5 characters of id must be digits: " + studentId);
            }
        }

        //Perform conversion
        result = (studentId.charAt(0) - 'a') * 10000000L + (studentId.charAt(1) - 'a') * 100000L;
        result += Integer.parseInt(studentId.substring(2));
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Raw id == " + getRawId().toString() + "; student id == " + getId().toString());
        result.append("; seat == " + seat + "; class == " + studentClass.toString());
        return result.toString();
    }

}