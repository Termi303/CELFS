package uk.ac.bris.celfs.database;

import org.junit.Before;
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

    protected List<Coursework> courseworks;
    protected List<Category> categories;
    protected List<Criterion> criteria;
    protected List<Band> bands;

    protected int courseworksSize = 2;
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
        courseworks = new ArrayList<>();
        for(int i = 0; i < courseworksSize; i++) {
            String name = "COURSEWORK_" + i;
            courseworks.add(new Coursework(name));
        }
        courseworkRepository.saveAll(courseworks);
    }

    protected void createCategoriesBeforeTest() {
        createCourseworksBeforeTest();
        categories = new ArrayList<>();
        Float weight = (1.0f/categoriesPerCoursework);
        for(int i = 0; i < courseworksSize * categoriesPerCoursework; i++) {
            String name = "CATEGORY_" + i;
            categories.add(new Category(name, courseworks.get(i / categoriesPerCoursework), weight));
        }
        categoryRepository.saveAll(categories);
    }

    protected void createCriteriaBeforeTest() {
        createCategoriesBeforeTest();
        criteria = new ArrayList<>();
        for(int j = 0; j < categories.size(); j++) {
            for (int i = 0; i < criteriaPerCategory; i++) {
                String name = "CRITERION_" + (i + j*criteriaPerCategory);
                criteria.add(new Criterion(name, categories.get(j)));
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
