package uk.ac.bris.celfs.database;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.coursework.Coursework;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CellTest extends DatabaseTestTemplate {

    @Before
    public void createDatabase() {
        createCriteriaBeforeTest();
        createBandsBeforeTest();
    }

    @Test
    public void testCreateOneCell() {
        String description = "NAME_0";
        Cell cell = new Cell(criteria.get(0), bands.get(0), description);
        cellRepository.save(cell);

        assert(cell.getId() != null);
        assertEquals(cell.getBand(), bands.get(0));
        assertEquals(cell.getCriterion(), criteria.get(0));
        assertEquals(cell.getDescription(), description);
    }

    @Test
    public void testCreateMultipleCells() {
        
    }
}
