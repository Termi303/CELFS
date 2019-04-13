package uk.ac.bris.celfs.coursework;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.database.Student;
import uk.ac.bris.celfs.database.User;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;


@DataJpaTest
@RunWith(SpringRunner.class)
public class CourseworkEntryTest extends CourseworkTestTemplate {

    @Before
    public void createDatabase() {
        createStudents();
        createTeachers();
        createCourseworks();
    }

    @Test
    public void testCreateOneCourseworkEntry() {
        Student student = studentList.get(0);
        Float score = 80.0f;
        Coursework coursework = courseworkList.get(0);
        User teacher = teacherList.get(0);

        CourseworkEntry courseworkEntry = new CourseworkEntry(student, new ArrayList<>(), score, coursework, teacher);
        courseworkEntryRepository.save(courseworkEntry);

        assert(courseworkEntry.getId() != null);
        assertEquals(student, courseworkEntry.getStudent());
        assertEquals(score, courseworkEntry.getOverallScore());
        assertEquals(coursework, courseworkEntry.getCoursework());
        assertEquals(teacher, courseworkEntry.getTeacher());
    }
}
