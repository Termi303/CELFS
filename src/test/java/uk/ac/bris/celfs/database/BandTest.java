package uk.ac.bris.celfs.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkRepository;
import static org.junit.Assert.assertEquals;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BandTest extends DatabaseTestTemplate {

    @Test
    public void testCreateOneBand() {
        String name = "Exceptional";
        Band band = new Band(name);
        bandRepository.save(band);

        assert(band.getId() != null);
        assertEquals(name, band.getName());
    }

    @Test
    public void testCreateOneBandWithDescription() {
        String name = "Exceptional";
        String description = "Sample description";
        Band band = new Band(name);
        band.setDescription(description);
        bandRepository.save(band);

        assertEquals(description, band.getDescription());
    }

    @Test
    public void testCreateMultipleBands() {
        int howMany = 100;
        String defaultName = "BAND_NAME_";
        String defaultDescription = "BAND_DESCRIPTION_";
        for(int i = 0; i < howMany; i++) {
            String name = defaultName + i;
            String description = defaultDescription + i;
            Band band = new Band(name);
            band.setDescription(description);
            bandRepository.save(band);
        }
        List<Band> allBands = new ArrayList<>();
        bandRepository.findAll()
                .forEach(allBands::add);

        assertEquals(howMany, allBands.size());
        boolean[] seenBands = new boolean[howMany];
        for(Band band : allBands) {
            String name = band.getName();
            String description = band.getDescription();
            int i = getIndex(name);
            assertEquals((defaultName + i), name);
            assertEquals((defaultDescription + i), description);
            assertEquals(false, seenBands[i]);
            seenBands[i] = true;
        }
    }
}
