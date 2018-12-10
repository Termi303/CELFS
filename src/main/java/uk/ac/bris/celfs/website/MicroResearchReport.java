package uk.ac.bris.celfs.website;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "micro_research_report")
@Data
public class MicroResearchReport {
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;
*/
    @Id
    @Column(name = "student_id")
    private Long id;

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
    private Integer overallScore;
    
    @Column(name = "comment")
    private String comment;
    
    private MicroResearchReport() {
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public MicroResearchReport(Student student/*, Teacher teacher*/, Integer task, Integer lang, Integer org, Integer score) {
        this.student = student;
        this.id = this.student.getRawId();
        /*this.teacher = teacher;*/
        this.taskFulfillment = task;
        this.languageUse = lang;
        this.organisation = org;
        this.overallScore = score;
    }

}
