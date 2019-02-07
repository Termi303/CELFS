package uk.ac.bris.celfs.coursework;

import lombok.Data;
import uk.ac.bris.celfs.database.Student;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "micro_research_report")
@Data
public class CourseworkEntry {
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private User teacher;
*/
    @Id
    @Column(name = "student_id")
    private String id;

    @PrimaryKeyJoinColumn(name = "student_id", referencedColumnName = "id")
    @OneToOne
    private Student student;
    
    @Column(name = "task_fulfillment")
    @NotNull
    private Integer taskFulfillment;
    
    @Column(name = "language_use")
    @NotNull
    private Integer languageUse;
    
    @Column(name = "organisation")
    @NotNull
    private Integer organisation;
    
    @Column(name = "overall")
    @NotNull
    private Float overallScore;
    
    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private Coursework coursework;

    private CourseworkEntry() {
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public CourseworkEntry(Student student/*, User teacher*/, Integer task, Integer lang, Integer org, Float score) {
        this.student = student;
        this.id = this.student.getId();
        /*this.teacher = teacher;*/
        this.taskFulfillment = task;
        this.languageUse = lang;
        this.organisation = org;
        this.overallScore = score;
    }

}
