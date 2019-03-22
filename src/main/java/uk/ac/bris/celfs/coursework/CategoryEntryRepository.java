package uk.ac.bris.celfs.coursework;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryEntryRepository extends CrudRepository<CategoryEntry, Long> {
    public List<CategoryEntry> findByCourseworkEntryId(Long courseworkEntryId);
}
