package uk.ac.bris.celfs.coursework;

import lombok.Data;
import uk.ac.bris.celfs.database.Student;
import uk.ac.bris.celfs.database.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "coursework_entry")
@Data
public class CourseworkEntry {
    @Id
    @GeneratedValue
    @Column(name = "coursework_entry_id")
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private User teacher;

    @Column
    @ElementCollection(targetClass=Integer.class)
    private List<Integer> categoryAverage;
    
    @Column(name = "coursework_entry_overall")
    @NotNull
    private Float overallScore;
    
    @Column(name = "coursework_entry_comment")
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
    
    public CourseworkEntry(Student student, List<Integer> categoryAverage, Float score, Coursework coursework, User teacher) {
        this.coursework = coursework;
        this.student = student;
        this.categoryAverage = categoryAverage;
        this.overallScore = score;
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }
    public Coursework getCoursework() {
        return coursework;
    }
    public Integer getCategoryAverage(int i) {
        return categoryAverage.get(i);
    }
    public Long getId() { return id; }
    public String getComment() { return comment; }
    public User getTeacher() { return teacher; }
}
