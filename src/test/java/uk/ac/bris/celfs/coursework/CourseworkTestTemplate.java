package uk.ac.bris.celfs.coursework;

import uk.ac.bris.celfs.database.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


public class CourseworkTestTemplate {
    @Resource
    protected UserRepository userRepository;
    @Resource
    protected StudentRepository studentRepository;
    @Resource
    protected CourseworkRepository courseworkRepository;
    @Resource
    protected CourseworkEntryRepository courseworkEntryRepository;

    protected int numberOfStudents = 100;
    protected int numberOfCourseworks = 5;
    protected int numberOfTeachers = 20;

    protected List<Student> studentList;
    protected List<Coursework> courseworkList;
    protected List<User> teacherList;

    protected void createTeachers() {
        teacherList = new ArrayList<>();
        for(int i = 0; i < numberOfTeachers; i++) {
            teacherList.add(new User("TEACHER_" + i, "PASSWORD_" + i, UserType.TEACHER));
        }
        userRepository.saveAll(teacherList);
    }

    protected void createStudents() {
        studentList = new ArrayList<>();
        for(int i = 0; i < numberOfStudents; i++) {
            studentList.add(new Student("STUDENT_" + i, "SEAT_" + i, "CLASS_" + i));
        }
        studentRepository.saveAll(studentList);
    }

    protected void createCourseworks() {
        courseworkList = new ArrayList<>();
        for(int i = 0; i < numberOfCourseworks; i++) {
            courseworkList.add(new Coursework("COURSEWORK_" + i));
        }
        courseworkRepository.saveAll(courseworkList);
    }
}
