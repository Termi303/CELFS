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
            {
                "Method",
                    "Rigorous research process elegantly described with originality in purpose and rationale for text selection",
                    "Method: Personalised rationale for 3 appropriate sources given; limitations explained",
                    "Method: Basic criteria for selection of most sources mentioned (what and why: CRAAP); limitations acknowledged",
                    "Method: Narrates use of bibliography & citing authors to select two further sources from original article (what but not why)",
                    "Method: Poorly selected sources with no explanatory rationale",
                    "Neither what nor why is covered in the method section",
                    "Focus can be perceived only with difficulty",
                    "No attempt at task; evidence of cheating; memorised script"
            },
            {
                "Results",
                    "Rigorous research process elegantly described with originality in purpose and rationale for text selection",
                    "Results: Clear writer’s stance on relative contribution of texts is evident in visualisation and sustained throughout text",
                    "Results: clear summary supported by visualisation of relationship between three articles on topic (stance)",
                    "Results: Some evidence of textual and visual summary to show understanding of perspectives of different texts, though perhaps no link between visual and text and/or no explicit stance on relationship",
                    "Results: Very limited evidence of summary at level of concepts with no visual support so stance is difficult to discern",
                    "Writer’s stance difficult to identify",
                    "No discernible attempt to establish stance",
                    "No attempt at task; evidence of cheating; memorised script"
            },
            {
                "Discussion",
                    "Exceptional depth of analysis",
                    "Discussion: Very good level of critical analysis of sources demonstrated in main points made but some opportunities to add own voice may still be missed",
                    "Discussion: Clear evidence of understanding through ability to analyse and compare/contrast key points",
                    "Discussion: Some ability to identify key points",
                    "Discussion: Attempts to communicate main ideas are unsuccessful more often than not with substantial repetition, irrelevance and/or lack of support",
                    "ubstantially irrelevant",
                    "Completely irrelevant",
                    "No attempt at task; evidence of cheating; memorised script"
            },
            {
                "Synthesis",
                    "Exceptionally sophisticated synthesis of sources to develop own argument in an innovative way",
                    "Effective synthesis of summarised key points from different perspectives into own voice",
                    "Emerging ability to synthesise sources in support of own voice, without significant borrowing from sources",
                    "Over-reliance on borrowing, so voice often missing",
                    "Significant borrowing from sources, voice is missing",
                    "Sources used wholly inappropriately",
                    "Sources not used",
                    "No attempt at task; evidence of cheating; memorised script"
            },
            {
                "Reasoning",
                    "Exceptionally lucid line of reasoning",
                    "Explicit line of reasoning on development of topic knowledge (beyond basic similarities and differences)",
                    "Line of reasoning may lack sophistication",
                    "Some successful attempts to build a basic line of reasoning drawing on sources but not sustained",
                    "Few successful attempts to build a line of reasoning",
                    "Any attempt to build a line of reasoning is unsuccessful",
                    "No attempt to build a line of reasoning",
                    "No attempt at task; evidence of cheating; memorised script"}},
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
