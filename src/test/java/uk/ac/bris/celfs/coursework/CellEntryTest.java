package uk.ac.bris.celfs.coursework;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.database.Category;

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
    }

    @Test
    public void testCreateOneCellEntry() {

    }
}
