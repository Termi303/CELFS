package uk.ac.bris.celfs.database;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CriterionTest extends DatabaseTestTemplate {

    @Before
    public void createDatabase() {
        createCourseworksBeforeTest();
        createCategoriesBeforeTest();
    }

    @Test
    public void testCreateOneCriterion() {
        String name = "CRITERION_0";
        Criterion criterion = new Criterion(name, categoryList.get(0));
        criterionRepository.save(criterion);

        assert(criterion.getId() != null);
        assertEquals(name, criterion.getName());
        assertEquals(categoryList.get(0), criterion.getCategory());
    }
}
