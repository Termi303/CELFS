package uk.ac.bris.celfs.coursework;

import uk.ac.bris.celfs.database.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


public abstract class CourseworkTestTemplate extends DatabaseTestTemplate {
    @Resource
    protected UserRepository userRepository;
    @Resource
    protected StudentRepository studentRepository;
    @Resource
    protected CourseworkEntryRepository courseworkEntryRepository;
    @Resource
    protected CategoryEntryRepository categoryEntryRepository;

    protected int numberOfStudents = 100;
    protected int numberOfTeachers = 20;
    protected int numberOfCourseworkEntries = 15;

    protected List<Student> studentList;
    protected List<User> teacherList;
    protected List<CourseworkEntry> courseworkEntryList;

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

    protected void createCourseworkEntries() {
        courseworkEntryList = new ArrayList<>();
        for(int i = 0; i < numberOfCourseworkEntries; i++) {
            Student student = studentList.get( i%numberOfStudents );
            Coursework coursework = courseworkList.get( i%numberOfCourseworks );
            User teacher = teacherList.get( i%numberOfTeachers );
            courseworkEntryList.add(new CourseworkEntry(student, new ArrayList<>(), (100.0f/numberOfCourseworkEntries), coursework, teacher));
        }
        courseworkEntryRepository.saveAll(courseworkEntryList);
    }

    protected List<Category> getCategoryFromCourseworkEntry(CourseworkEntry courseworkEntry) {
        List<Category> result = new ArrayList<>();
        for(Category category : categoryList) {
            if(category.getCoursework().equals(courseworkEntry.getCoursework())) {
                result.add(category);
            }
        }
        return result;
    }
}
