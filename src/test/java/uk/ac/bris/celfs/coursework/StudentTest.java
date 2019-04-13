package uk.ac.bris.celfs.coursework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.database.Student;
import uk.ac.bris.celfs.database.StudentRepository;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentTest extends CourseworkTestTemplate {
    @Test
    public void testCreateOneStudent() {
        String studentId = "ab23145";
        String studentSeat = "SEAT_1";
        String studentClass = "MRR";

        Student student = new Student(studentId, studentSeat, studentClass);
        studentRepository.save(student);

        assertEquals(studentId, student.getId());
        assertEquals(studentClass, student.getStudentClass());
        assertEquals(studentSeat, student.getSeat());
    }

    @Test
    public void testStudentToString() {
        String studentId = "ab23145";
        String studentSeat = "SEAT_1";
        String studentClass = "MRR";

        Student student = new Student(studentId, studentSeat, studentClass);

        String expected = "Student id == " + studentId + "; seat == " + studentSeat + "; class == " + studentClass;
        assertEquals(expected, student.toString());
    }
}
