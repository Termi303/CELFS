package uk.ac.bris.celfs.website;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class UpdateMarksCommand {
    String search;
    
    Map<String, String> updatedMarks = new HashMap();
    
}
