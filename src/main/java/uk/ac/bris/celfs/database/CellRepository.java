package uk.ac.bris.celfs.database;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CellRepository extends CrudRepository<Cell, Long> {
    public List<Cell> findByBandId(Long bandId);
    public List<Cell> findByCriterionId(Long criterionId);
}
