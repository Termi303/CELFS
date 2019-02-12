package uk.ac.bris.celfs.database;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    public List<Category> findByCourseworkId(Long courseworkId);
}
