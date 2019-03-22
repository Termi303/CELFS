package uk.ac.bris.celfs.website;
import java.util.ArrayList;
import lombok.Data;

@Data
public class UpdateMarksCommand {
    String search;
    
    ArrayList<String> updatedMarks = new ArrayList<String>();
    
}
