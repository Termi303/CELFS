/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bris.celfs.website;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;
import uk.ac.bris.celfs.services.KeywordService;

/**
 *
 * @author lucas
 */

//All the software given out on this Snowball site is covered by the BSD License 
//(see http://www.opensource.org/licenses/bsd-license.html ), with Copyright (c) 2001, Dr Martin Porter, 
//and (for the Java developments) Copyright (c) 2002, Richard Boulton.
//
//Essentially, all this means is that you can do what you like with the code, except 
//claim another Copyright for it, or claim that it is issued under a different license. 
//The software is also issued without warranties, which means that if anyone suffers through 
//its use, they cannot come back and sue you. You also have to alert anyone to whom you 
//give the Snowball software to the fact that it is covered by the BSD license.

@Data
public class Keywords {

    private static Map<String, String> keywords = new HashMap<>();

    public static String processKeywords(String page){
        keywords.put("analysis", "Analysing stuff.");
        keywords.put("response", "How they respond to the question? Idk.");
        keywords.put("respond", "How they respond to the question? Idk.");
        keywords.put("control", "How to manipulate people.");
        
        SnowballStemmer stemmer = new englishStemmer();
        SnowballStemmer stemmerKey = new englishStemmer();
        
        ArrayList<String> stems = new ArrayList<>();
        
        String[] pageSplit = page.split(" ");
        
        for(String word : pageSplit){
            stemmer.setCurrent(word.toLowerCase());
            stemmer.stem();
            stems.add(stemmer.getCurrent());
        }

        for (Map.Entry<String, String> entry : keywords.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            
            stemmerKey.setCurrent(key.toLowerCase());
            stemmerKey.stem();
            String comp = stemmerKey.getCurrent();

            for(int i = 0; i < pageSplit.length; i++){
                String tag = "<abbr title='" + value + "' class='keyword'>" + pageSplit[i] + "</abbr>";
                //System.out.println("Matching " + stems.get(i) + " with " + comp);
                if(stems.get(i).equals(comp)){
                                     
                    //System.out.println("match!");
                    pageSplit[i] = tag;
                }
            }
        }
        
        //System.out.println(String.join(" ", pageSplit));
        return String.join(" ", pageSplit);
    }
    
    public static void init() {

    }
    
}
