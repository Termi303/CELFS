package uk.ac.bris.celfs.website;

import java.util.ArrayList;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.bris.celfs.coursework.CategoryEntry;
import uk.ac.bris.celfs.coursework.CellEntry;
import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkEntry;
import uk.ac.bris.celfs.services.CourseworkEntryService;

@Data
public class DoubleCommand {
    
    String studentID;
    Coursework cw;
    
    //vs - list of criterias (according to certain category)
    //v_comments - list of comments for criterias
    ArrayList<ArrayList<ArrayList<String>>> vs = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<String>>> v_comments = new ArrayList<>();
    
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
    
    public void addOldEntry(CourseworkEntry entry, CourseworkEntryService courseworkEntryService){
        ArrayList<ArrayList<String>> old_vs = new ArrayList<>();
        ArrayList<ArrayList<String>> old_v_comments = new ArrayList<>();
        
        int i = 0;
        System.out.println(entry);
        System.out.println(courseworkEntryService);
        System.out.println(courseworkEntryService.getCategoryEntries(entry.getId()));
        for (CategoryEntry categoryEntry : courseworkEntryService.getCategoryEntries(entry.getId())) {
            old_vs.add(new ArrayList<>());
            old_v_comments.add(new ArrayList<>());
            int j = 1;
            for (CellEntry cellEntry : courseworkEntryService.getCellEntries(categoryEntry.getId())) {
                String s = "v_" + Integer.toString(i+1) + Integer.toString(j) +  "_" + Integer.toString(cellEntry.getBandIndex());
                old_vs.get(i).add(s);
                old_v_comments.get(i).add(cellEntry.getComment());
                j++;
            }
            i++;
        }
        
        this.vs.add(old_vs);
        this.v_comments.add(old_v_comments);
        
    }
    
    String overallComment;
    
    
}