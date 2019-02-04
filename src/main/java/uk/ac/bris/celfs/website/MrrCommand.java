package uk.ac.bris.celfs.website;

import java.util.ArrayList;
import lombok.Data;

@Data
public class MrrCommand {

    
    String studentID;
    
    
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
    
    
}