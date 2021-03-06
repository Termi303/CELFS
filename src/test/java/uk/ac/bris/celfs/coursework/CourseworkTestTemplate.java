package uk.ac.bris.celfs.coursework;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
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
    @Resource
    protected CellEntryRepository cellEntryRepository;

    protected int numberOfStudents = 100;
    protected int numberOfTeachers = 20;
    protected int numberOfCourseworkEntries = 15;

    protected List<Student> studentList;
    protected List<User> teacherList;
    protected List<CourseworkEntry> courseworkEntryList;
    protected List<CategoryEntry> categoryEntryList;

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

    protected void createCategoryEntries() {
        categoryEntryList = new ArrayList<>();
        for(CourseworkEntry courseworkEntry : courseworkEntryList) {
            List<Category> categories = getCategoryFromCourseworkEntry(courseworkEntry);
            Integer mark = 70;
            for(Category category : categories) {
                categoryEntryList.add(new CategoryEntry(courseworkEntry, category, mark));
            }
        }
        categoryEntryRepository.saveAll(categoryEntryList);
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

    protected List<Cell> getCellsFromCategoryEntry(CategoryEntry categoryEntry) {
        Category category = categoryEntry.getCategory();
        List<Criterion> criteria = new ArrayList<>();
        for(Criterion criterion : criterionList) {
            if(criterion.getCategory().equals(category)) {
                criteria.add(criterion);
            }
        }
        List<Cell> cells = new ArrayList<>();
        for(Cell cell : cellList) {
            if(criteria.contains(cell.getCriterion())) {
                cells.add(cell);
            }
        }
        return cells;
    }
}
