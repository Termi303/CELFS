package uk.ac.bris.celfs.website;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import uk.ac.bris.celfs.services.CourseworkEntryService;

@Data
public class ShowMarksCommand {
    
    String search;
    Long cwType;
    
    List<List<Long>> ids = new ArrayList<>();
    List<List<Float>> marks = new ArrayList<>();
    
    public void addCat(){
        this.ids.add(new ArrayList<>());
        this.marks.add(new ArrayList<>());
    }

    public void addId(int i, Long id, CourseworkEntryService courseworkEntryService){
        this.ids.get(i).add(id);
        this.marks.get(i).add(courseworkEntryService.getCourseworkEntry(id).getOverallScore());
    }
    
}