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
public class CriterionTest extends DatabaseTestTemplate {
    private List<Coursework> courseworks;
    private List<Category> categories;

    private int courseworksSize = 2;
    private int categoriesPerCoursework = 3;

    private void createCourseworksBeforeTest() {
        courseworks = new ArrayList<>();
        for(int i = 0; i < courseworksSize; i++) {
            String name = "COURSEWORK_" + i;
            courseworks.add(new Coursework(name));
        }
        courseworkRepository.saveAll(courseworks);
    }

    @Before
    public void createCategoriesBeforeTest() {
        createCourseworksBeforeTest();
        categories = new ArrayList<>();
        Float weight = (1.0f/categoriesPerCoursework);
        for(int i = 0; i < courseworksSize; i++) {
            String name = "CATEGORY_" + i;
            categories.add(new Category(name, courseworks.get(i / categoriesPerCoursework), weight));
        }
    }

    @Test
    public void testCreateOneCriterion() {
        String name = "CRITERION_0";
        Criterion criterion = new Criterion(name, categories.get(0));
        criterionRepository.save(criterion);

        assert(criterion.getId() != null);
        assertEquals(name, criterion.getName());
        assertEquals(categories.get(0), criterion.getCategory());
    }
}
