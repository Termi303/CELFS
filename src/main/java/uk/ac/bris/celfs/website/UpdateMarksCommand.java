package uk.ac.bris.celfs.website;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import uk.ac.bris.celfs.services.CourseworkEntryService;

@Data
public class UpdateMarksCommand {
    List<Long> ids = new ArrayList<>();
    List<Float> marks = new ArrayList<>();

    public void addId(Long id, CourseworkEntryService courseworkEntryService){
        this.ids.add(id);
        this.marks.add(courseworkEntryService.getCourseworkEntry(id).getOverallScore());
    }
    
    public UpdateMarksCommand(){
        
    }
}
