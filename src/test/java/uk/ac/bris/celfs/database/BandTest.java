package uk.ac.bris.celfs.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkRepository;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BandTest {
    @Autowired
    private BandRepository bandRepository;

    @Test
    public void testCreateOneBand() {
        String name = "Exceptional";
        Band band = new Band(name);
        bandRepository.save(band);

        assert(band.getId() != null);
        assertEquals(name, band.getName());
    }
}
