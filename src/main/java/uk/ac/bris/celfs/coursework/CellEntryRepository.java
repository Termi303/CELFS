package uk.ac.bris.celfs.coursework;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CellEntryRepository extends CrudRepository<CellEntry, Long> {
    public List<CellEntry> findByCategoryEntryId(Long categoryEntryId);
}