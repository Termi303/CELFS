package uk.ac.bris.celfs.website;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public void add(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public List<Teacher> getAll() {
        List<Teacher> teachers = new ArrayList<>();
        teacherRepository.findAll()
                .forEach(teachers::add);
        return teachers;
    }

}