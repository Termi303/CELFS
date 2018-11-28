package uk.ac.bris.celfs.website;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
@Data
public class MicroResearchReport {

    /*@EmbeddedId
    @Column(name = "student")
    Student student;*/

    /*@NotEmpty
    @ManyToOne
    @JoinColumns({
        @JoinColumn(
                name = "teacher",
                referencedColumnName = "id"
        )
    })
    Teacher teacher;*/
    
    @Id
    @GeneratedValue
    Long id;
    
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
    
}
