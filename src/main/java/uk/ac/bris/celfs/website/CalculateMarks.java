/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package uk.ac.bris.celfs.website;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;

@Data
public class CalculateMarks {
    private static final int[] marks = {0,7,15,22,29,36,42,45,48,52,55,58,62,65,68,72,75,78,83,89,94};
    private static final int[] midBand = {0,10,36,45,55,65,75,89};
    private static final String[] bands = {"Exceptional", "Very Good", "Good", 
        "Satisfactory", "Borderline", "Borderline Fail", "Clear Fail", "Zero"};
    
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
      return (id.charAt(2) - '0');
    }

    public static Integer getBand(String id){
      return (id.charAt(id.length()-1) - '0');
    }
    
    public static String getCriterion(String id){
        Pattern pattern = Pattern.compile("v_([0-9])([0-9])_([0-9]*)");
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

    public static int[][] separateCategories(CourseworkCommand data){
        int [][] markArray = new int[data.vs.size()][data.vs.get(0).size()];
        System.out.println(data);
        for(int i = 0; i < data.vs.size(); i++){
            markArray[i] = new int[data.vs.get(i).size()];
            for(int j = 0; j < data.vs.get(i).size(); j++){
                markArray[i][j]=bandToMark(getBand(data.vs.get(i).get(j)));
            }
        }  
        return markArray;
    }
    
    public static int[][] separateCategories(DoubleCommand data){
        int [][] markArray = new int[data.new_vs.size()][data.new_vs.get(0).size()];
        System.out.println(data);
        for(int i = 0; i < data.new_vs.size(); i++){
            markArray[i] = new int[data.new_vs.get(i).size()];
            for(int j = 0; j < data.new_vs.get(i).size(); j++){
                markArray[i][j]=bandToMark(getBand(data.new_vs.get(i).get(j)));
            }
        }  
        return markArray;
        }

    public static int getBandAverage(int[] marks){
      int total=0;
      for (int x : marks){
        total +=x;
      }
      return applyMark(total/marks.length);
    }

    public static float getOverallScore(List<Integer> marks, List<Float> weights) {
        float result = 0.0f;
        for(int i = 0; i < weights.size(); i++) {
            result += weights.get(i) * marks.get(i);
        }
        return result;
    }
    
    public static String numberToDescription(int band){
        return bands[band-1];
    }
    
    public static String getBandDesc(String raw){
        
        int band = getBand(raw);        
        String result = numberToDescription(band);
        
        return result;
    }

}
