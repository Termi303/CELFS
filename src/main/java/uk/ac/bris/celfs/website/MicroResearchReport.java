package uk.ac.bris.celfs.website;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class MicroResearchReport {

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    Teacher teacher;
    
    @Id
    @Column(name="student_no")
    Long id;
    
    @OneToOne
    @PrimaryKeyJoinColumn(name="student_no", referencedColumnName="id")
    private Student owner;
    
    @Column(name = "task_fulfillment")
    @NotEmpty
    Integer taskFulfillment;
    
    @Column(name = "language_use")
    @NotEmpty
    Integer languageUse;
    
    @Column(name = "organisation")
    @NotEmpty
    Integer organisation;
    
    @Column(name = "overall")
    @NotEmpty
    Integer overallScore;
    
    @Column(name = "comment")
    String comment;
}
