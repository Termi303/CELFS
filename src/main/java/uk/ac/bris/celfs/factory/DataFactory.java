package uk.ac.bris.celfs.factory;

import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.database.Band;
import uk.ac.bris.celfs.services.TablesService;

import java.util.ArrayList;
import java.util.List;

public static class DataFactory {
    private static boolean isBuilt = false;

    public static void buildData(TablesService service) {
        if(isBuilt) return;
        List<Band> bands = buildBands();
        service.addBands(bands);

        List<Coursework> courseworks = buildCourseworks();
    }

    private static List<Coursework> buildCourseworks() {
        List<Coursework> courseworks = new ArrayList<>();
        String[] names = {"Micro Research Report"};
        for(String name : names) {
            courseworks.add(new Coursework(name));
        }
        return courseworks;
    }

    private static List<Band> buildBands() {
        List<Band> bands = new ArrayList<>();
        String[] names = {"Exceptional", "Very Good", "Good", "Satisfactory", "Borderline", "Borderline Fail", "Clear Fail", "Zero"};
        for(String name : names) {
            bands.add(new Band(name));
        }
        return bands;
    }

}
