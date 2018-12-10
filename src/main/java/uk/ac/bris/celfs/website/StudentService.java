package uk.ac.bris.celfs.website;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public void add(Student student) {
        studentRepository.save(student);
    }

    public Student get(String studentId) {
        return studentRepository.findById(studentId).get();
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