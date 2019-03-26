package uk.ac.bris.celfs.website;

import java.util.ArrayList;
import lombok.Data;

@Data
public class DoubleCommand {
    
    String studentID;
    
    //vs - list of criterias (according to certain category)
    //v_comments - list of comments for criterias
   
    ArrayList<ArrayList<String>> new_vs = new ArrayList<>();
    ArrayList<ArrayList<String>> new_v_comments = new ArrayList<>();
    
    public void addCat() {
        this.new_vs.add(new ArrayList<>());
        this.new_v_comments.add(new ArrayList<>());
    }
    
    public void addCrit(int i, String c, String comment) {
        this.new_vs.get(i).add(c);
        this.new_v_comments.get(i).add(comment);
    }
    
    String overallComment;
    
    
}