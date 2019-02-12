package uk.ac.bris.celfs.coursework;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseworkEntryRepository extends CrudRepository<CourseworkEntry, String> {
    public List<CourseworkEntry> findByCourseworkId(Long courseworkId);
}
