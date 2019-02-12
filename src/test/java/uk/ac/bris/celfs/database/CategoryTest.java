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
public class CategoryTest {
    @Resource
    private CourseworkRepository courseworkRepository;

    @Resource
    private CategoryRepository categoryRepository;

    private List<Coursework> courseworkList;

    @Before
    public void createCourseworksBeforeTest() {
        courseworkList = new ArrayList<>();
        courseworkList.add(new Coursework("Micro Research Report"));
        courseworkList.add(new Coursework("Short Question Answer"));
        courseworkRepository.saveAll(courseworkList);
    }

    @Test
    public void testCreateOneCategory() {
        Category category = new Category("Category 1", courseworkList.get(0));
        System.out.println(category);
        categoryRepository.save(category);
        System.out.println("AFTER INSERT:");
        System.out.println(category);

        assert(category.getId() != null);
        assertEquals("Category 1", category.getName());
        assertEquals(courseworkList.get(0), category.getCoursework());
    }
}
