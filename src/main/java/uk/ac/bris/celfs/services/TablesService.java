package uk.ac.bris.celfs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkRepository;
import uk.ac.bris.celfs.database.*;

@Service
public class TablesService {
    @Autowired
    private BandRepository bandRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CriterionRepository criterionRepository;
    @Autowired
    private CellRepository cellRepository;
    @Autowired
    private CourseworkRepository courseworkRepository;

    public Band getBandById(Long id) {
        return bandRepository.findById(id).get();
    }

    public List<Band> getAllBands() {
        List<Band> bands = new ArrayList<>();
        bandRepository.findAll()
                .forEach(bands::add);
        return bands;
    }

    public String[] getAllBandsNames() {
        List<Band> bands = getAllBands();
        String[] result = new String[bands.size()];
        for(int i = 0; i < bands.size(); i++) {
            result[i] = bands.get(i).getName();
        }
        return result;
    }

    public List<Coursework> getAllCourseworks() {
        List<Coursework> courseworks = new ArrayList<>();
        courseworkRepository.findAll()
                .forEach(courseworks::add);
        return courseworks;
    }

    public List<String> getAllCourseworksNames() {
        List<Coursework> courseworks = getAllCourseworks();
        List<String> result = new ArrayList<>();
        for(Coursework coursework : courseworks) {
            result.add(coursework.getName());
        }
        return result;
    }
    
    public Coursework getCourseworkById(Long id) {
        Optional<Coursework> coursework = courseworkRepository.findById(id);
        return coursework.isPresent() ? coursework.get() : null;
    }

    public Coursework getCourseworkByName(String name) {
        List<Coursework> courseworks = courseworkRepository.findByName(name);
        return courseworks.isEmpty() ? null : courseworks.get(0);
    }

    public Band getBandByName(String name) {
        List<Band> bands = bandRepository.findByName(name);
        return bands.isEmpty() ? null : bands.get(0);
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll()
                .forEach(categories::add);
        return categories;
    }

    private List<Criterion> getAllCriteria() {
        List<Criterion> criteria = new ArrayList<>();
        criterionRepository.findAll()
                .forEach(criteria::add);
        return criteria;
    }

    private List<Cell> getAllCells() {
        List<Cell> cells = new ArrayList<>();
        cellRepository.findAll()
                .forEach(cells::add);
        return cells;
    }

    private List<Criterion> getCriteria(Long categoryId) {
        List<Criterion> allCriteria = getAllCriteria();
        List<Criterion> result = new ArrayList<>();
        for(Criterion criterion : allCriteria) {
            if(criterion.getCategory().getId().equals(categoryId)) {
                result.add(criterion);
            }
        }
        return result;
    }

    public String[] getCategoriesNames(Long courseworkId) {
        List<Category> categories = getCategories(courseworkId);
        String[] result = new String[categories.size()];
        for(int i = 0; i < categories.size(); i++) {
            result[i] = categories.get(i).getName();
        }
        return result;
    }

    public List<Category> getCategories(Long courseworkId) {
        return categoryRepository.findByCourseworkId(courseworkId);
    }

    private List<Cell> getCells(Long criterionId) {
        List<Cell> allCells = getAllCells();
        List<Cell> result = new ArrayList<>();
        for(Cell cell : allCells) {
            if(cell.getCriterion().getId().equals(criterionId)) {
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
            List<Criterion> criteria = getCriteria(category.getId());

            for(Criterion criterion : criteria) {
                List<String> oneCriterionTable = new ArrayList<>();
                oneCriterionTable.add(criterion.getCriterionName());

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

    public void addCell(Cell cell) {
        cellRepository.save(cell);
    }

    public void addBands(List<Band> bands) {
        bandRepository.saveAll(bands);
    }

    public void addCriterion(Criterion criterion) {
        criterionRepository.save(criterion);
    }

    public void addCategories(List<Category> categories) {
        categoryRepository.saveAll(categories);
    }

    public void addCourseworks(List<Coursework> courseworks) {
        courseworkRepository.saveAll(courseworks);
    }
}
