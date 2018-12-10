package uk.ac.bris.celfs.website;

import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class StudentTest {
    @Test
    public void testBijectionOfIdHashing() {
        StringBuilder builder = new StringBuilder();
        char[] digits = new char[5];
        for(int a = 0; a < 26; a++) {
            for(int b = 0; b < 26; b++) {
                for(int c = 0; c < 10000; c += 101) {
                    builder.setLength(0);
                    builder.append( (char)(a + 'a') );
                    builder.append( (char)(b + 'a') );

                    int tmp = c;
                    for(int i = 4; i >= 0; i--) {
                        digits[i] = (char)((tmp%10) + '0');
                        tmp /= 10;
                    }
                    builder.append(digits);

                    String studentId = builder.toString();
                    Student student = new Student(studentId, "A1", "class_1");
                    String bijectedId = student.getId();

                    assertEquals(studentId, bijectedId);
                }
            }
        }
    }
}
