package uk.ac.bris.celfs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import uk.ac.bris.celfs.database.*;

@Service
public class TablesService {
    @Autowired
    private BandRepository bandRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CriteriaRepository criteriaRepository;
    @Autowired
    private CellRepository cellRepository;

    public Band getBandById(Long id) {
        return bandRepository.findById(id).get();
    }

    public List<Band> getAllBands() {
        List<Band> bands = new ArrayList<>();
        bandRepository.findAll()
                .forEach(bands::add);
        return bands;
    }

    public Band getBandByName(String bandName) {
        List<Band> bands = getAllBands();
        Band result = null;
        for(Band band : bands) {
            if(band.getName().equals(bandName)) {
                result = band;
                break;
            }
        }
        return result;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll()
                .forEach(categories::add);
        return categories;
    }

    private List<Criteria> getAllCriteria() {
        List<Criteria> criteria = new ArrayList<>();
        criteriaRepository.findAll()
                .forEach(criteria::add);
        return criteria;
    }

    private List<Cell> getAllCells() {
        List<Cell> cells = new ArrayList<>();
        cellRepository.findAll()
                .forEach(cells::add);
        return cells;
    }

    private List<Criteria> getCriteria(Long categoryId) {
        List<Criteria> allCriteria = getAllCriteria();
        List<Criteria> result = new ArrayList<>();
        for(Criteria criterion : allCriteria) {
            if(criterion.getCategory().getId().equals(categoryId)) {
                result.add(criterion);
            }
        }
        return result;
    }

    public List<Category> getCategories(Long courseworkId) {
        List<Category> allCategories = getAllCategories();
        List<Category> result = new ArrayList<>();
        for(Category category : allCategories) {
            if(category.getCoursework().getId().equals(courseworkId)) {
                result.add(category);
            }
        }
        return result;
    }

    private List<Cell> getCells(Long criterionId) {
        List<Cell> allCells = getAllCells();
        List<Cell> result = new ArrayList<>();
        for(Cell cell : allCells) {
            if(cell.getCriteria().getId().equals(criterionId)) {
                result.add(cell);
            }
        }
        return result;
    }

    public List<List<List<String>>> getTable(Long courseworkId) {
        List<List<List<String>>> result = new ArrayList<>();
        List<Category> categories = getCategories(courseworkId);

        for(Category category : categories) {
            List<List<String>> oneCategoryTable = new ArrayList<>();
            List<Criteria> criteria = getCriteria(category.getId());

            for(Criteria criterion : criteria) {
                List<String> oneCriterionTable = new ArrayList<>();
                oneCriterionTable.add(criterion.getCriteriaName());

                List<Cell> cells = getCells(criterion.getId());
                for(Cell cell : cells) {
                    oneCriterionTable.add(cell.getDescription());
                }
                oneCategoryTable.add(oneCriterionTable);
            }
            result.add(oneCategoryTable);
        }
        return result;
    }


}
