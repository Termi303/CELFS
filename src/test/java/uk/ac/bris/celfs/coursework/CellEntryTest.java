package uk.ac.bris.celfs.coursework;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.database.Category;
import uk.ac.bris.celfs.database.Cell;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@DataJpaTest
@RunWith(SpringRunner.class)
public class CellEntryTest extends CourseworkTestTemplate {
    @Before
    public void createDatabase() {
        createStudents();
        createTeachers();
        createBandsBeforeTest();
        createCourseworksBeforeTest();
        createCategoriesBeforeTest();
        createCriteriaBeforeTest();
        createCellsBeforeTest();
        createCourseworkEntries();
        createCategoryEntries();
    }

    @Test
    public void testCreateOneCellEntry() {
        CategoryEntry categoryEntry = categoryEntryList.get(0);
        Cell cell = getCellsFromCategoryEntry(categoryEntry).get(0);

        CellEntry cellEntry = new CellEntry(cell, categoryEntry);
        cellEntryRepository.save(cellEntry);

        assert(cellEntry.getId() != null);
        assertEquals(cell, cellEntry.getCell());
        assertEquals(categoryEntry, cellEntry.getCategoryEntry());
        assertEquals(null, cellEntry.getComment());
    }

    @Test
    public void testSetCommentInCellEntry() {
        CategoryEntry categoryEntry = categoryEntryList.get(0);
        Cell cell = getCellsFromCategoryEntry(categoryEntry).get(0);
        String comment = "COMMENT_0";
        CellEntry cellEntry = new CellEntry(cell, categoryEntry);
        cellEntry.setComment(comment);
        cellEntryRepository.save(cellEntry);

        assertEquals(comment, cellEntry.getComment());
    }
}
