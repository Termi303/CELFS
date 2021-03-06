package uk.ac.bris.celfs.website;

import java.util.ArrayList;
import lombok.Data;

@Data
public class CourseworkCommand {

    
    String studentID;
    
    //vs - list of criterias (according to certain category)
    //v_comments - list of comments for criterias
    ArrayList<ArrayList<String>> vs = new ArrayList<>();
    ArrayList<ArrayList<String>> v_comments = new ArrayList<>();
    
    public void addCat() {
        this.vs.add(new ArrayList<>());
        this.v_comments.add(new ArrayList<>());
    }
    
    public void addCrit(int i, String c, String comment) {
        this.vs.get(i).add(c);
        this.v_comments.get(i).add(comment);
    }    
    
    String overallComment;

    public CourseworkCommand(ArrayList<ArrayList<String>> vs, ArrayList<ArrayList<String>> v_comments, String studentID) {
        this.vs = vs;
        this.v_comments = v_comments;
        this.studentID = studentID;
    }

    public CourseworkCommand() {

    }
    
    
}