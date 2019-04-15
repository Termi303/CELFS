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
public class CategoryEntryTest extends CourseworkTestTemplate {
    @Before
    public void createDatabase() {
        createStudents();
        createTeachers();
        createCourseworksBeforeTest();
        createCategoriesBeforeTest();
        createCourseworkEntries();
    }

    @Test
    public void testCreateOneCategoryEntry() {
        CourseworkEntry courseworkEntry = courseworkEntryList.get(0);
        Category category = getCategoryFromCourseworkEntry(courseworkEntry).get(0);
        Integer mark = 40;
        CategoryEntry categoryEntry = new CategoryEntry(courseworkEntry, category, mark);
        categoryEntryRepository.save(categoryEntry);

        assert(categoryEntry.getId() != null);
        assertEquals(courseworkEntry, categoryEntry.getCourseworkEntry());
        assertEquals(category, categoryEntry.getCategory());
        assertEquals(mark, categoryEntry.getMark());
    }
}
