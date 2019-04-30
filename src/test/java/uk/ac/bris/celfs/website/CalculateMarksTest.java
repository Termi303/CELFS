package uk.ac.bris.celfs.website;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CalculateMarksTest {
    @Test
    public void testApplyMarkForExactMark() {
        assertEquals(0, CalculateMarks.applyMark(0));
        assertEquals(36, CalculateMarks.applyMark(36));
        assertEquals(55, CalculateMarks.applyMark(55));
    }
    @Test
    public void testGetBandAverage() {
      assertEquals(65, CalculateMarks.testGetBandAverage([65,65,65]));
      assertEquals(74, CalculateMarks.testGetBandAverage([65,85,72]));
    }

}
