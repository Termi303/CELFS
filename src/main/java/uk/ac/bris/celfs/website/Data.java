/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bris.celfs.website;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lucas
 */
public class Data {
    
    public static Map<String, Map<String, String[]>> data = new HashMap<>();
    public static Map<String, String[]> crit1 = new HashMap<>();
    public static Map<String, String[]> crit2 = new HashMap<>();
    public static Map<String, String[]> crit3 = new HashMap<>();
    
    public Data() {
        
        String[] a = {"Rigorous, lucid, creative & original response",
            "Complete, relevant, fairly sophisticated response to task with noticeable quality of ideas ",
            "No major omissions and mostly relevant response to task but may lack sophistication",
            "No major omissions with some successful attempts to communicate main ideas but some repetition, irrelevance",
            "Minimal response to task, with only one major omission (missing one IMRD section, missing source, no reference list, no visual summary)",
            "Inadequate response to task which misses more than one major element of task",
            "Fails to address the general scope of the task",
            "No attempt at task; evidence of cheating"};
        
        crit1.put("Response", a);
        
        String[] b = {"Full control of a wide variety of sentence structures",
        "Very good control of a variety of sentence structures though possibly some inappropriacies",
        "Control of a variety of sentence structures",
        "Control of simple sentence structures with some successful attempts at variety",
        "Control of basic sentence structure but attempts at variety more unsuccessful than not",
        "Basic sentence structure is sound but no attempt at variety",
        "Little evidence of basic sentence structure",
        "Incomprehensible due to lack of control of structure or word choice/form"};
        
        crit2.put("Control", b);
        
        String[] c = {"Writer responsibility is fully met and thesis is outstanding for originality / creativity / elegance",
            "Non-specialist audience very well prepared for topic, task and argument: concise, sophisticated thesis with well-placed map explaining order of key points, which is then followed",
            "Non-specialist audience well prepared for topic and task: rationale for choice, key definitions, thesis, purpose and map",
            "Thesis easily located but lacks clarity on the relationship between texts and/or how this will be explained (no map) and/or purpose given too late so initial confusion caused",
            "Research question shows topic focus but thesis is not easily located and it is unclear how the question will be answered (no purpose)",
            "Topic has not been narrowed to research question",
            "No attempt to introduce topic or draw conclusions",
            "No awareness of academic conventions is evidenced"};
        
        crit3.put("Sentence Structure", c);
        
        data.put("Task Fulfilment and Content", crit1);
        data.put("Language and Style", crit2);
        data.put("Text Organisation", crit3); 
        
    }
    
    public static Map<String, Map<String, String[]>> getData(){
        return data;
    }
    
    
    
}
