package uk.ac.bris.celfs.database;

import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTestTemplate {

    @Resource
    protected CourseworkRepository courseworkRepository;
    @Resource
    protected CategoryRepository categoryRepository;
    @Resource
    protected BandRepository bandRepository;
    @Resource
    protected CriterionRepository criterionRepository;
    @Resource
    protected CellRepository cellRepository;
    @Resource
    protected KeywordRepository keywordRepository;

    protected List<Coursework> courseworkList;
    protected List<Category> categoryList;
    protected List<Criterion> criteria;
    protected List<Band> bands;

    protected int numberOfCourseworks = 5;
    protected int categoriesPerCoursework = 3;
    protected int criteriaPerCategory = 5;
    protected int numberOfBands = 9;

    protected int getIndex(String name) {
        int result = 0;
        int multiplier = 1;
        int where = name.length()-1;
        while(name.charAt(where) != '_') {
            result += multiplier * (name.charAt(where--) - '0');
            multiplier *= 10;
        }
        return result;
    }

    protected void createCourseworksBeforeTest() {
        courseworkList = new ArrayList<>();
        for(int i = 0; i < numberOfCourseworks; i++) {
            courseworkList.add(new Coursework("COURSEWORK_" + i));
        }
        courseworkRepository.saveAll(courseworkList);
    }

    protected void createCategoriesBeforeTest() {
        categoryList = new ArrayList<>();
        Float weight = (1.0f/categoriesPerCoursework);
        for(int i = 0; i < numberOfCourseworks * categoriesPerCoursework; i++) {
            String name = "CATEGORY_" + i;
            categoryList.add(new Category(name, courseworkList.get(i / categoriesPerCoursework), weight));
        }
        categoryRepository.saveAll(categoryList);
    }

    protected void createCriteriaBeforeTest() {
        criteria = new ArrayList<>();
        for(int j = 0; j < categoryList.size(); j++) {
            for (int i = 0; i < criteriaPerCategory; i++) {
                String name = "CRITERION_" + (i + j*criteriaPerCategory);
                criteria.add(new Criterion(name, categoryList.get(j)));
            }
        }
        criterionRepository.saveAll(criteria);
    }

    protected void createBandsBeforeTest() {
        bands = new ArrayList<>();
        for(int i = 0; i < numberOfBands; i++) {
            String name = "BAND_" + i;
            String description = "BAND_DESC_" + i;
            Band band = new Band(name);
            band.setDescription(description);
            bands.add(band);
        }
        bandRepository.saveAll(bands);
    }

}
