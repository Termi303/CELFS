package uk.ac.bris.celfs.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.database.Keyword;
import uk.ac.bris.celfs.database.Student;
import uk.ac.bris.celfs.database.User;
import uk.ac.bris.celfs.database.UserType;

import static org.junit.Assert.assertEquals;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentServiceTest {
    @Autowired
    protected StudentService studentService;

    @Test
    public void testAddStudentByObject() {
        studentService.init();
        Student student = new Student("TEST_ID_0", "TEST_SEAT_0", "TEST_CLASS_0");
        studentService.add(student);

        assertEquals(student, studentService.get(student.getId()));
    }

    @Test
    public void testAddStudentByStrings() {
        studentService.init();
        String id = "TEST_ID_1";
        String seat = "TEST_SEAT_1";
        String studentClass = "TEST_CLASS_1";
        studentService.add(id, seat, studentClass);

        Student student = studentService.get(id);
        assertEquals(id, student.getId());
        assertEquals(seat, student.getSeat());
        assertEquals(studentClass, student.getStudentClass());
    }
    
}
