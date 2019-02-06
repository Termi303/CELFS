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
        keywords.put("analysis", "According to the Longman Dictionary of Contemporary English, this means a careful examination of something in order to understand it better/see what it is made of. In academic writing we encourage students to take things apart and consider the causes and effects or identify key points of comparison and contrast. Students may be able to do this and produce good paragraphs but still not be able to hook these good paragraphs onto a line of reasoning – the backbone of the text.");
        keywords.put("argument", "Argument (line of reasoning) does not equal arguments (claims and evidence). ‘It is the development of a position… rather than evidence for individual claims that determines the quality of an essay’ (Wingate 2011:3)\n" +
"Wingate identifies 3 key components of an argument:\n" +
"•	The analysis and evaluation of content knowledge,\n" +
"•	The writer’s development of a position,\n" +
"•	The presentation of that position in a coherent manner.\n" +
"According to Brick (2006:38), western academic culture strongly favours deductive argument (thesis followed by reasons) and some lecturers may mark inductively organised argument (reasons followed by thesis) very harshly. Hence, we need to teach students to take responsibility as a writer to tell their reader what they are going to do, do it and then tell them what they have done. This is particularly important for second language writers where there may be language issues.");
        keywords.put("argue", "Argument (line of reasoning) does not equal arguments (claims and evidence). ‘It is the development of a position… rather than evidence for individual claims that determines the quality of an essay’ (Wingate 2011:3)\n" +
"Wingate identifies 3 key components of an argument:\n" +
"•	The analysis and evaluation of content knowledge,\n" +
"•	The writer’s development of a position,\n" +
"•	The presentation of that position in a coherent manner.\n" +
"According to Brick (2006:38), western academic culture strongly favours deductive argument (thesis followed by reasons) and some lecturers may mark inductively organised argument (reasons followed by thesis) very harshly. Hence, we need to teach students to take responsibility as a writer to tell their reader what they are going to do, do it and then tell them what they have done. This is particularly important for second language writers where there may be language issues.");
        keywords.put("coherent", "Logical progression of thought as indicated by signal words (logical connectors). This involves patterns of organisation that meet the expectations of the audience eg paragraphs moving from general to specific.");
        keywords.put("cohesive", "Logical progression of thought as indicated by signal words (logical connectors). This involves patterns of organisation that meet the expectations of the audience eg paragraphs moving from general to specific.");
        keywords.put("cohesion", "This is the glue that sticks the sentences and paragraphs together, achieved by grammatical connectors such as pronoun reference, article use, lexical chains and given - new sentence structure to maintain topic/theme. Eg ‘This’ plus a summary noun eg this issue (which also adds voice as my issue may be your phenomenon!) or the choice of passive vs active to front the topic.");
        keywords.put("stance", "A position (stance) may be taken in a thesis statement (based on analysis and evaluation of sources), but if the writer’s voice (critical comment, interpretation and evaluation) is not there throughout the text (see point above) then the line of reasoning is not well developed. This is a good (60s) but not very good (70s) text. A very good writer’s stance will have topic sentences clearly linked back to the thesis and linguistic control of emphasis through maximisers and minimisers. A borderline pass (40s) might have a position stated in the introduction but not even followed through in the conclusion. A satisfactory pass (50s) has the stance in the introduction and paraphrased in the conclusion but lacking in the body.");
        keywords.put("voice", "This is the ultimate goal of an academic writer and is hard to achieve. Hence, it only features in the exceptional and very good bands. According to Alexander & Argent (forthcoming), having a clear writer’s voice involves:\n" +
"•	Taking a nuanced stance,\n" +
"•	Selecting and reporting evidence (data or sources) critically,\n" +
"•	Interpreting evidence to support your stance,\n" +
"•	Choosing a persuasive structure for your argument,\n" +
"•	Integrating the evidence into your argument, with the appropriate signals,\n" +
"•	Using your own words.");
        
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
