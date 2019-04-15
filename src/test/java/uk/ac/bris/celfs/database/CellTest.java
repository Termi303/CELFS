package uk.ac.bris.celfs.database;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CellTest extends DatabaseTestTemplate {

    @Before
    public void createDatabase() {
        createCourseworksBeforeTest();
        createCategoriesBeforeTest();
        createCriteriaBeforeTest();
        createBandsBeforeTest();
    }

    @Test
    public void testCreateOneCell() {
        String description = "NAME_0";
        Cell cell = new Cell(criterionList.get(0), bandList.get(0), description);
        cellRepository.save(cell);

        assert(cell.getId() != null);
        assertEquals(cell.getBand(), bandList.get(0));
        assertEquals(cell.getCriterion(), criterionList.get(0));
        assertEquals(cell.getDescription(), description);
    }

    @Test
    public void testCreateWholeTableOfCells() {
        for(int criteriaIndex = 0; criteriaIndex < criterionList.size(); criteriaIndex++) {
            for(int bandIndex = 0; bandIndex < bandList.size(); bandIndex++) {
                int index = criteriaIndex* bandList.size() + bandIndex;
                String description = "CELL_" + index;
                cellRepository.save(new Cell(criterionList.get(criteriaIndex), bandList.get(bandIndex), description));
            }
        }
        List<Cell> cells = new ArrayList<>();
        cellRepository.findAll()
                .forEach(cells::add);
        assertEquals(criterionList.size() * bandList.size(), cells.size());
        boolean[] usedBands = new boolean[ cells.size() ];
        for(Cell cell : cells) {
            int index = getIndex(cell.getDescription());
            assertEquals(false, usedBands[index]);
            usedBands[index] = true;
        }
    }
}
