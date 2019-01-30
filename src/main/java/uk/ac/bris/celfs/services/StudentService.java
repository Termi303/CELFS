package uk.ac.bris.celfs.services;

import uk.ac.bris.celfs.database.StudentRepository;
import uk.ac.bris.celfs.database.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public void add(Student student) {
        studentRepository.save(student);
    }

    public Student get(String studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if(student.isPresent()) return student.get();
        return null;
    }
    
    public void delete(String studentId) {
        studentRepository.deleteById(studentId);
    }

    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll()
                .forEach(students::add);
        return students;
    }

    public void init() {
        int howMany = 5;
        for(int i = 0; i < howMany; i++) {
            int singleId = 12345+i;
            add( new Student("ab" + singleId, "seat_" + i, "microClass_" + i) );
        }
    }
}