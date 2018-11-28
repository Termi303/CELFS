package uk.ac.bris.celfs.website;

import org.springframework.data.repository.CrudRepository;

public interface StudentsRepository extends CrudRepository<Student, Long> {
}