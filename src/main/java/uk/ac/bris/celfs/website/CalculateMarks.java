/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package uk.ac.bris.celfs.website;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;

@Data
public class CalculateMarks {
    private static final int[] marks = {0,7,15,22,29,36,42,45,48,52,55,58,62,65,68,72,75,78,83,89,94};
    private static final int[] midBand = {0,10,36,45,55,65,75,89};
    private static final String[] bands = {"Exceptional Pass", "Very Good Pass", "Good Pass", 
        "Satisfactory Pass", "Borderline Pass", "Borderline Fail", "Clear Fail", "Zero"};
    
    //Based on code from: https://stackoverflow.com/questions/8612891/how-can-i-round-down-to-the-nearest-integer-from-an-int-array
        
    public static int applyMark(int x) {
        int applied = 0;
        for(int i=0; i<marks.length; i++){
            if(marks[i] <= x)
                applied = marks[i];
            else{
              if((marks[i]-x)<(x-marks[i-1])){
                applied=marks[i];
              }
                break;
            }
        }
        return applied;
    }

    private static Integer getCat(String id){
      return (id.charAt(0) - '0');
    }

    private static Integer getBand(String id){
      return (id.charAt(id.length()-1) - '0');
    }
    
    private static String getCrit(String id){
        Pattern pattern = Pattern.compile("([0-9]*)_([a-z]*)_([0-9]*)");
        Matcher matcher = pattern.matcher(id);
        if(matcher.find()){
            return matcher.group(2);
        } else {
            return id;
        }
        
    }

    private static int bandToMark(int b){
      return midBand[midBand.length-b];
    }

    public static int[][] sepCat(MrrCommand data){
      int [][] markArray = new int[3][6];
      markArray[0][0]=bandToMark(getBand(data.response));
      markArray[0][1]=bandToMark(getBand(data.method));
      markArray[0][2]=bandToMark(getBand(data.results));
      markArray[0][3]=bandToMark(getBand(data.discussion));
      markArray[0][4]=bandToMark(getBand(data.synthesis));
      markArray[0][5]=bandToMark(getBand(data.reasoning));
      markArray[1][0]=bandToMark(getBand(data.control));
      markArray[1][1]=bandToMark(getBand(data.errors));
      markArray[1][2]=bandToMark(getBand(data.nounphrases));
      markArray[1][3]=bandToMark(getBand(data.vocabulary));
      markArray[1][4]=bandToMark(getBand(data.wordchoice));
      markArray[1][5]=bandToMark(getBand(data.style));
      markArray[2][0]=bandToMark(getBand(data.sentencestructure));
      markArray[2][1]=bandToMark(getBand(data.organisation));
      markArray[2][2]=bandToMark(getBand(data.development));
      markArray[2][3]=bandToMark(getBand(data.cohesivedevices));
      markArray[2][4]=bandToMark(getBand(data.conclusion));
      markArray[2][5]=bandToMark(getBand(data.presentation));
      return markArray;
    }

    public static int getBandAvg(int[] marks){
      int total=0;
      for (int x : marks){
        total +=x;
      }
      return applyMark(total/marks.length);
    }

    public static int getAvg(int x, int y, int z){
      return (int)(0.4*x+0.2*y+0.4*z);
    }
    
    public static String numToDesc(int band){
        return bands[band-1];
    }
    
    public static String getBandDesc(String raw){
        
        int band = getBand(raw);        
        String result = numToDesc(band);
        
        return result;
    }

}
