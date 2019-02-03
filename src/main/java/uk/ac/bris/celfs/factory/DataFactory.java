package uk.ac.bris.celfs.factory;

import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.database.Band;
import uk.ac.bris.celfs.database.Category;
import uk.ac.bris.celfs.services.TablesService;

import java.util.ArrayList;
import java.util.List;

public class DataFactory {
    private static boolean isBuilt = false;
    private static final String[] bandNames = {"Exceptional", "Very Good", "Good", "Satisfactory", "Borderline", "Borderline Fail", "Clear Fail", "Zero"};
    private static final String[] courseworkNames = {"Micro Research Report"};
    private static final String[][] categoryNames = {{"Task Fulfilment and Content", "Language and Style", "Text Organisation"}};

    public static void buildData(TablesService service) {
        if(isBuilt) return;
        List<Band> bands = buildBands();
        service.addBands(bands);

        List<Coursework> courseworks = buildCourseworks();
        service.addCourseworks(courseworks);

        List<Category> categories = buildCategories(courseworks);
        service.a
    }

    private static List<Category> buildCategories(List<Coursework> courseworks) {
        List<Category> categories = new ArrayList<>();
        for(int i = 0; i < categoryNames.length; i++) {
            for(int j = 0; j < categoryNames[i].length; j++) {
                categories.add(new Category(categoryNames[i][j], courseworks.get(i)));
            }
        }
        return categories;
    }

    private static List<Coursework> buildCourseworks() {
        List<Coursework> courseworks = new ArrayList<>();
        for(String name : courseworkNames) {
            courseworks.add(new Coursework(name));
        }
        return courseworks;
    }

    private static List<Band> buildBands() {
        List<Band> bands = new ArrayList<>();
        for(String name : bandNames) {
            bands.add(new Band(name));
        }
        return bands;
    }

}
