package uk.ac.bris.celfs.coursework;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.database.Student;
import uk.ac.bris.celfs.database.User;

import java.util.ArrayList;
import java.util.List;

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

    private List<CourseworkEntry> createBlankCourseworkEntries(int howMany) {
        List<CourseworkEntry> result = new ArrayList<>();
        for(int i = 0; i < howMany; i++) {
            result.add(new CourseworkEntry(studentList.get(i), new ArrayList<>(), (100.0f/howMany), courseworkList.get(i), teacherList.get(i)));
        }
        courseworkEntryRepository.saveAll(result);
        return result;
    }

    @Test
    public void testSetComment() {
        List<CourseworkEntry> courseworkEntryList = createBlankCourseworkEntries(1);
        String comment = "Good work./;12";
        courseworkEntryList.get(0).setComment(comment);

        assertEquals(comment, courseworkEntryList.get(0).getComment());
    }

    @Test
    public void testSetOverallScore() {
        List<CourseworkEntry> courseworkEntryList = createBlankCourseworkEntries(1);
        Float newScore = 90.0f;
        courseworkEntryList.get(0).setOverallScore(newScore);

        assertEquals(newScore, courseworkEntryList.get(0).getOverallScore());
    }

    @Test
    public void testSetDoubleMarking() {
        List<CourseworkEntry> courseworkEntryList = createBlankCourseworkEntries(1);

        assertEquals(false, courseworkEntryList.get(0).getResolvedDoubleMarking());
        courseworkEntryList.get(0).setResolvedDoubleMarking(true);
        assertEquals(true, courseworkEntryList.get(0).getResolvedDoubleMarking());
    }
}
