package uk.ac.bris.celfs.website;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Entity
@Table
@Data
public class MrrRaw {

    @Id
    @GeneratedValue
    Long id;
    
    @Column
    @NotEmpty
    String studentID;
    
    @Column
    String response_comment;
    
    @Column
    String response;
    
    @Column
    String method_comment;
    
    @Column
    String method;
    
    @Column
    String results_comment;
    
    @Column
    String results;
    
    @Column
    String discussion_comment;
    
    @Column
    String discussion;
    
    @Column
    String synthesis_comment;
    
    @Column
    String synthesis;
    
    @Column
    String reasoning_comment;
    
    @Column
    String reasoning;
    
    @Column
    String control_comment;
    
    @Column
    String control;
    
    @Column
    String errors_comment;
    
    @Column
    String errors;
    
    @Column
    String nounphrases_comment;
    
    @Column
    String nounphrases;
    
    @Column
    String vocabulary_comment;
    
    @Column
    String vocabulary;
    
    @Column
    String wordchoice_comment;
    
    @Column
    String wordchoice;
    
    @Column
    String style_comment;
    
    @Column
    String style;
    
    @Column
    String sentencestructure_comment;
    
    @Column
    String sentencestructure;
    
    @Column
    String organisation_comment;
    
    @Column
    String organisation;
    
    @Column
    String development_comment;
    
    @Column
    String development;
    
    @Column
    String cohesivedevices_comment;
    
    @Column
    String cohesivedevices;
    
    @Column
    String conclusion_comment;
    
    @Column
    String conclusion;
    
    @Column
    String presentation_comment;
    
    @Column
    String presentation;
    
    @Column
    String overallComment;
    
    
}