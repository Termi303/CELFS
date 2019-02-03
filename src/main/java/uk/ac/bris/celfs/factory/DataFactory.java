package uk.ac.bris.celfs.factory;

import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.database.Band;
import uk.ac.bris.celfs.database.Category;
import uk.ac.bris.celfs.database.Cell;
import uk.ac.bris.celfs.database.Criteria;
import uk.ac.bris.celfs.services.TablesService;

import java.util.ArrayList;
import java.util.List;

public class DataFactory {
    private static boolean isBuilt = false;
    private static final String[] bandNames = {"Exceptional", "Very Good", "Good", "Satisfactory", "Borderline", "Borderline Fail", "Clear Fail", "Zero"};
    private static final String[] courseworkNames = {"Micro Research Report"};
    private static final String[][] categoryNames = {{"Task Fulfilment and Content", "Language and Style", "Text Organisation"}};

    private static final String[][][] criteriaAndBands = {{{"Response",
            "Rigorous, lucid, creative & original response",
            "Complete, relevant, fairly sophisticated response to task with noticeable quality of ideas",
            "No major omissions and mostly relevant response to task but may lack sophistication",
            "No major omissions with some successful attempts to communicate main ideas but some repetition, irrelevance",
            "Minimal response to task, with only one major omission (missing one IMRD section, missing source, no reference list, no visual summary)",
            "Inadequate response to task which misses more than one major element of task",
            "Fails to address the general scope of the task",
            "No attempt at task; evidence of cheating"},
            {"Magic", "responses responding responded Responded rESPONSE rEsPOndeD", "two", "three", "four", "five", "six", "seven", "eight"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
            {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"}},
            {{"Control",
                    "Full control of a wide variety of sentence structures",
                    "Very good control of a variety of sentence structures though possibly some inappropriacies",
                    "Control of a variety of sentence structures",
                    "Control of simple sentence structures with some successful attempts at variety",
                    "Control of basic sentence structure but attempts at variety more unsuccessful than not",
                    "Basic sentence structure is sound but no attempt at variety",
                    "Little evidence of basic sentence structure",
                    "Incomprehensible due to lack of control of structure or word choice/form"},
                    {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
                    {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
                    {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
                    {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
                    {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"}},
            {{"Sentence Structure",
                    "Writer responsibility is fully met and thesis is outstanding for originality / creativity / elegance",
                    "Non-specialist audience very well prepared for topic, task and argument: concise, sophisticated thesis with well-placed map explaining order of key points, which is then followed",
                    "Non-specialist audience well prepared for topic and task: rationale for choice, key definitions, thesis, purpose and map",
                    "Thesis easily located but lacks clarity on the relationship between texts and/or how this will be explained (no map) and/or purpose given too late so initial confusion caused",
                    "Research question shows topic focus but thesis is not easily located and it is unclear how the question will be answered (no purpose)",
                    "Topic has not been narrowed to research question",
                    "No attempt to introduce topic or draw conclusions",
                    "No awareness of academic conventions is evidenced"},
                    {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
                    {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
                    {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
                    {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"},
                    {"Magic", "one", "two", "three", "four", "five", "six", "seven", "eight"}}};

    public static void buildData(TablesService service) {
        if(isBuilt) return;
        List<Band> bands = buildBands();
        service.addBands(bands);

        List<Coursework> courseworks = buildCourseworks();
        service.addCourseworks(courseworks);

        /*List<Category> categories = buildCategories(courseworks);
        service.addCategories(categories);

        buildTable(service, categories, bands);*/
    }

    private static void buildTable(TablesService service, List<Category> categories, List<Band> bands) {
        for(int subtable = 0; subtable < criteriaAndBands.length; subtable++) {
            Category category = categories.get(subtable);
            for(int row = 0; row < criteriaAndBands[subtable].length; row++) {
                Criteria criterion = new Criteria(criteriaAndBands[subtable][row][0], category);
                service.addCriterion(criterion);

                for(int column = 0; column < bands.size(); column++) {
                    Cell cell = new Cell(criterion, bands.get(column), criteriaAndBands[subtable][row][column+1]);
                    service.addCell(cell);
                }
            }
        }
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
