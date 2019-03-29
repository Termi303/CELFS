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
public class BandTest {
    @Resource
    private BandRepository bandRepository;

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

    private int getIndex(String name) {
        int result = 0;
        int multiplier = 1;
        int where = name.length()-1;
        while(name.charAt(where) == '_') {
            result += multiplier * (name.charAt(where--) - '0');
            multiplier *= 10;
        }
        return result;
    }

    @Test
    public void testCreateMultipleBands() {
        int howMany = 100;
        String default_name = "BAND_NAME_";
        String default_description = "BAND_DESCRIPTION_";
        for(int i = 0; i < howMany; i++) {
            String name = default_name + i;
            String description = default_description + i;
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
            assertEquals((default_name + i), name);
            assertEquals((default_description + i), description);
            assertEquals(false, seenBands[i]);
            seenBands[i] = true;
        }
    }
}
