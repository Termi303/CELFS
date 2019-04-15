package uk.ac.bris.celfs.database;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkRepository;
import static org.junit.Assert.assertEquals;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CourseworkTest extends DatabaseTestTemplate {
    @Test
    public void testCreateOneCoursework() {
        String name = "test_coursework";
        Coursework coursework = new Coursework(name);
        courseworkRepository.save(coursework);

        assert(coursework.getId() != null);
        assertEquals(name, coursework.getName());
    }

    @Test
    public void testCreateMultipleCourseworks() {
        int howMany = 100;
        String defaultName = "COURSEWORK_NAME_";
        for(int i = 0; i < howMany; i++) {
            Coursework coursework = new Coursework((defaultName + i));
            courseworkRepository.save(coursework);
        }
        boolean[] usedCourseworks = new boolean[howMany];
        List<Coursework> allCourseworks = new ArrayList<>();
        courseworkRepository.findAll()
                .forEach(allCourseworks::add);

        assertEquals(howMany, allCourseworks.size());
        boolean[] seenCourseworks = new boolean[howMany];
        for(Coursework coursework : allCourseworks) {
            String name = coursework.getName();
            int i = getIndex(name);
            assertEquals((defaultName + i), name);
            assertEquals(false, seenCourseworks[i]);
            seenCourseworks[i] = true;
        }
    }
}
