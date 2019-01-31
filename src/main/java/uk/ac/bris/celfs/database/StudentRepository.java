package uk.ac.bris.celfs.database;

import uk.ac.bris.celfs.database.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, String> {
}