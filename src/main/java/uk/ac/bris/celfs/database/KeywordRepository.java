package uk.ac.bris.celfs.database;

import org.springframework.data.repository.CrudRepository;
import uk.ac.bris.celfs.database.Keyword;

public interface KeywordRepository extends CrudRepository<Keyword, String> {

}