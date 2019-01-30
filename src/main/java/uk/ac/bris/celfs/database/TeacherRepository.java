package uk.ac.bris.celfs.database;

import uk.ac.bris.celfs.database.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {
}