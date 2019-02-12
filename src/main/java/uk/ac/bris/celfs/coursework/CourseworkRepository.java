package uk.ac.bris.celfs.coursework;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseworkRepository extends CrudRepository<Coursework, Long> {
    public List<Coursework> findByName(String name);
}
