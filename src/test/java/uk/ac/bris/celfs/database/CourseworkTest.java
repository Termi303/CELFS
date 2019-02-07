package uk.ac.bris.celfs.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkRepository;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseworkTest {
    @Autowired
    private CourseworkRepository courseworkRepository;

    @Test
    public void testCreateOneCoursework() {
        String name = "test_coursework";
        Coursework coursework = new Coursework(name);
        courseworkRepository.save(coursework);

        assert(coursework.getId() != null);
        assertEquals(name, coursework.getName());
    }
}
