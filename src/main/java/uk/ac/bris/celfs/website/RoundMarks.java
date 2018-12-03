import java.util.Arrays;
import java.util.*;


public class RoundMarks {
    private static int[]  marks= {0,5,10};

    private static int applyMark(int x) {
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

    public static void main(String[] args) {
      System.out.print(applyMark(Integer.parseInt(args[0])));
    }
}
