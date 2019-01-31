/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bris.celfs.website;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 *
 * @author lucas
 */

@Data
public class Keywords {
    private static Map<String, String> keywords = new HashMap<String, String>();
    
    public static String processKeywords(String page){
        keywords.put("analysis", "Analysing stuff.");
        keywords.put("response", "How they respond to the question? Idk.");
        keywords.put("control", "How to manipulate people.");
        
        for (Map.Entry<String, String> entry : keywords.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String tag = "<abbr title='" + value + "' class='keyword'>" + key + "</abbr>";
            
            page = page.replaceAll(key, tag);
        }
        
        return page;
    }
    
    
    
}
