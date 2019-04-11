package uk.ac.bris.celfs.database;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkRepository;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryTest extends DatabaseTestTemplate {

    @Before
    public void createDatabase() {
        createCourseworksBeforeTest();
    }

    @Test
    public void testCreateOneCategory() {
        Float weight = 0.4f;
        Category category = new Category("Category 1", courseworks.get(0), weight);
        categoryRepository.save(category);

        assert(category.getId() != null);
        assertEquals("Category 1", category.getName());
        assertEquals(courseworks.get(0), category.getCoursework());
        assertEquals(weight, category.getWeight());
    }

    @Test
    public void testCreateMultipleCategories() {
        int howMany = 100;
        Float weight = (1.0f/howMany);
        String defaultName = "CATEGORY_NAME_";
        for(int i = 0; i < howMany; i++) {
            String name = defaultName + i;
            categoryRepository.save(new Category(name, courseworks.get(i % courseworks.size()), weight));
        }
        boolean[] usedCategories = new boolean[howMany];
        List<Category> allCategories = new ArrayList<>();
        categoryRepository.findAll().forEach(allCategories::add);

        assertEquals(howMany, allCategories.size());
        for(Category category : allCategories) {
            String name = category.getName();
            int index = getIndex(name);
            assertEquals(defaultName + index, name);
            assertEquals(courseworks.get(index % courseworks.size()), category.getCoursework());
            assertEquals(weight, category.getWeight());
        }
    }
}
