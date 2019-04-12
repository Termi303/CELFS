package uk.ac.bris.celfs.coursework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import static org.junit.Assert.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
public class CourseworkTest {
    @Resource
    private CourseworkRepository courseworkRepository;

    @Test
    public void testCreateOneCoursework() {
        String name = "COURSEWORK_0";
        Coursework coursework = new Coursework(name);

        courseworkRepository.save(coursework);
        assert(coursework.getId() != null);
        assertEquals(name, coursework.getName());
    }
}
