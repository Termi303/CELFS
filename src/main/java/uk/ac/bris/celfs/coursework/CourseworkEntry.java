package uk.ac.bris.celfs.coursework;

import lombok.Data;
import uk.ac.bris.celfs.database.Student;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "coursework_entry")
@Data
public class CourseworkEntry {
    @Id
    @Column(name = "student_id")
    private String id;

    @PrimaryKeyJoinColumn(name = "student_id", referencedColumnName = "id")
    @OneToOne
    private Student student;

    @Column
    @ElementCollection(targetClass=Integer.class)
    private List<Integer> categoryAverage;
    
    @Column(name = "overall")
    @NotNull
    private Float overallScore;
    
    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private Coursework coursework;

    private CourseworkEntry() {
    }
    
    public void setOverallScore(Float newMark) {
        this.overallScore = newMark;
    }
    
    public Float getOverallScore() {
        return this.overallScore;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public CourseworkEntry(Student student, List<Integer> categoryAverage, Float score) {
        this.student = student;
        this.id = this.student.getId();
        this.categoryAverage = categoryAverage;
        this.overallScore = score;
    }

}
