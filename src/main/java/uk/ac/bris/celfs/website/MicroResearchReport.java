package uk.ac.bris.celfs.website;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "micro_research_report")
@Data
public class MicroResearchReport {

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    private Teacher teacher;
    
    @Id
    @Column(name="student_no")
    private Long id;
    
    @OneToOne
    @PrimaryKeyJoinColumn(name="student_no", referencedColumnName="id")
    private Student owner;
    
    @Column(name = "task_fulfillment")
    @NotEmpty
    private Integer taskFulfillment;
    
    @Column(name = "language_use")
    @NotEmpty
    private Integer languageUse;
    
    @Column(name = "organisation")
    @NotEmpty
    private Integer organisation;
    
    @Column(name = "overall")
    @NotEmpty
    private Integer overallScore;
    
    @Column(name = "comment")
    private String comment;
    
    private MicroResearchReport() {
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public MicroResearchReport(Student student, Teacher teacher, Integer task, Integer lang, Integer org, Integer score) {
        this.owner = student;
        this.teacher = teacher;
        this.languageUse = lang;
        this.organisation = org;
        this.overallScore = score;
    }
}
