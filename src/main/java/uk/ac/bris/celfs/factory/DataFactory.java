package uk.ac.bris.celfs.factory;

import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.database.Band;
import uk.ac.bris.celfs.database.Category;
import uk.ac.bris.celfs.database.Cell;
import uk.ac.bris.celfs.database.Criterion;
import uk.ac.bris.celfs.services.TablesService;

import java.util.ArrayList;
import java.util.List;

public class DataFactory {
    private static boolean isBuilt = false;
    public static final String[] bandNames = {"Criterion", "Exceptional", "Very Good", "Good", "Satisfactory", "Borderline", "Borderline Fail", "Clear Fail", "Zero"};
    public static final String[] courseworkNames = {"Micro Research Report"};
    public static final String[][] categoryNames = {{"Task Fulfilment and Content", "Language and Style", "Text Organisation"}};

    public static final String[][][] criteriaAndBands = {{
            {
                "Response",
                    "Rigorous, lucid, creative & original response",
                    "Complete, relevant, fairly sophisticated response to task with noticeable quality of ideas",
                    "No major omissions and mostly relevant response to task but may lack sophistication",
                    "No major omissions with some successful attempts to communicate main ideas but some repetition, irrelevance",
                    "Minimal response to task, with only one major omission (missing one IMRD section, missing source, no reference list, no visual summary)",
                    "Inadequate response to task which misses more than one major element of task",
                    "Fails to address the general scope of the task",
                    "No attempt at task; evidence of cheating"
            },
            {
                "Method",
                    "Rigorous research process elegantly described with originality in purpose and rationale for text selection",
                    "Personalised rationale for 3 appropriate sources given; limitations explained",
                    "Basic criteria for selection of most sources mentioned (what and why: CRAAP); limitations acknowledged",
                    "Narrates use of bibliography & citing authors to select two further sources from original article (what but not why)",
                    "Poorly selected sources with no explanatory rationale",
                    "Neither what nor why is covered in the method section",
                    "Focus can be perceived only with difficulty",
                    "No attempt at task; evidence of cheating; memorised script"
            },
            {
                "Results",
                    "Rigorous research process elegantly described with originality in purpose and rationale for text selection",
                    "Clear writer’s stance on relative contribution of texts is evident in visualisation and sustained throughout text",
                    "Clear summary supported by visualisation of relationship between three articles on topic (stance)",
                    "Some evidence of textual and visual summary to show understanding of perspectives of different texts, though perhaps no link between visual and text and/or no explicit stance on relationship",
                    "Very limited evidence of summary at level of concepts with no visual support so stance is difficult to discern",
                    "Writer’s stance difficult to identify",
                    "No discernible attempt to establish stance",
                    "No attempt at task; evidence of cheating; memorised script"
            },
            {
                "Discussion",
                    "Exceptional depth of analysis",
                    "Very good level of critical analysis of sources demonstrated in main points made but some opportunities to add own voice may still be missed",
                    "Clear evidence of understanding through ability to analyse and compare/contrast key points",
                    "Some ability to identify key points",
                    "Attempts to communicate main ideas are unsuccessful more often than not with substantial repetition, irrelevance and/or lack of support",
                    "Substantially irrelevant",
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
                    "No attempt at task; evidence of cheating; memorised script"
            }},
            {{
                "Control",
                    "Full control of a wide variety of sentence structures",
                    "Very good control of a variety of sentence structures though possibly some inappropriacies",
                    "Control of a variety of sentence structures",
                    "Control of simple sentence structures with some successful attempts at variety",
                    "Control of basic sentence structure but attempts at variety more unsuccessful than not",
                    "Basic sentence structure is sound but no attempt at variety",
                    "Little evidence of basic sentence structure",
                    "Incomprehensible due to lack of control of structure or word choice/form"
            },
            {
                "Errors",
                    "Sophisticated and accurate use of appropriate grammar and punctuation",
                    "Almost entirely error-free",
                    "Errors with grammar are limited / superficial and meaning is clear; Good punctuation aids readability",
                    "Some errors with grammar start to hinder meaning in parts; Control of basic punctuation with some successful attempts at complexity",
                    "Grammatical errors hinder retrieval of meaning; Control of basic punctuation but attempts at variety more unsuccessful than not",
                    "Errors predominate in punctuation and grammar so meaning is often distorted",
                    "Basic errors in grammar and punctuation predominate obstructing meaning",
                    "Incomprehensible due to lack of control of structure or word choice/form"
            },
            {
                "Noun Phrases",
                    "Sophisticated and accurate use of appropriate grammar and punctuation",
                    "Very good use of noun phrase for conciseness",
                    "Mostly successful use of noun phrase grammar",
                    "Evidence of some successful use of noun phrase grammar",
                    "Relies mainly on verb phrase grammar so wordy",
                    "Errors predominate in punctuation and grammar so meaning is often distorted",
                    "Basic errors in grammar and punctuation predominate obstructing meaning",
                    "Incomprehensible due to lack of control of structure or word choice/form"
            },
            {
                "Vocabulary",
                    "Accurate and sophisticated usage of appropriate vocabulary",
                    "Sufficient range of vocabulary to allow some flexibility and precision including successful use of AWL",
                    "Vocabulary mostly appropriate to the task set including mostly successful use of AWL",
                    "Some successful attempts to use appropriate vocabulary including AWL",
                    "Limited vocabulary reasonably appropriate to the task set",
                    "Basic vocabulary used repetitively and often inappropriate to the task",
                    "Frequent poor word choice or form obscures meaning",
                    "Incomprehensible due to lack of control of structure or word choice/form"
            },
            {
                "Word Choice",
                    "Accurate and sophisticated usage of appropriate vocabulary",
                    "Accurate word choice and form though possibly some inappropriacies",
                    "Few errors in word choice or form and meaning is clear",
                    "Some errors in word choice or form but meaning is clear",
                    "Frequent errors in word choice or form may cause difficulty for the reader",
                    "Basic vocabulary used repetitively and often inappropriate to the task",
                    "Frequent poor word choice or form obscures meaning",
                    "Incomprehensible due to lack of control of structure or word choice/form"
            },
            {
                "Style",
                    "Elegant academic style throughout",
                    "Appropriate academic style throughout",
                    "Appropriate academic style quite well sustained so overall effect is achieved",
                    "Some successful attempts at academic style","Academic style is inconsistent",
                    "Stylistic variation is largely uncontrolled with little evidence of academic style",
                    "No evidence of academic style",
                    "Incomprehensible due to lack of control of structure or word choice/form"
            }},
            {{
                "Sentence Structure",
                    "Writer responsibility is fully met and thesis is outstanding for originality / creativity / elegance",
                    "Non-specialist audience very well prepared for topic, task and argument: concise, sophisticated thesis with well-placed map explaining order of key points, which is then followed",
                    "Non-specialist audience well prepared for topic and task: rationale for choice, key definitions, thesis, purpose and map",
                    "Thesis easily located but lacks clarity on the relationship between texts and/or how this will be explained (no map) and/or purpose given too late so initial confusion caused",
                    "Research question shows topic focus but thesis is not easily located and it is unclear how the question will be answered (no purpose)",
                    "Topic has not been narrowed to research question",
                    "No attempt to introduce topic or draw conclusions",
                    "No awareness of academic conventions is evidenced"
            },
            {
                "Organisation",
                    "Between paragraphs, lucid organisation and elegant transitions is a pleasure for the reader",
                    "Between paragraphs, logical organisation and transitions achieve natural coherence which aids readability",
                    "Between paragraphs, mostly logical organisation presents little difficulty for reader but transitions could be improved",
                    "Between paragraphs, unexpected organisation presents some difficulty for reader in places and some transitions lacking",
                    "Between paragraphs, basic headings are all that enable the reader to follow the overall text, though with some effort within sections due to lack of writer responsibility for paragraphing",
                    "Lack of writer responsibility for choice of effective organisation (not even basic IMRD headings) and lack of transitions cause notable difficulties for the reader throughout the text",
                    "No sections",
                    "No awareness of academic conventions is evidenced"
            },
            {
                "Development",
                    "Within paragraphs, elegant development of idea",
                    "Within paragraphs, general specific and given new pattern aids flow of ideas across sentences",
                    "Within paragraphs, mostly clear development of one main idea per paragraph from general to specific",
                    "Within paragraphs, some successful attempts to develop one main idea per paragraph but lacks consistency and may diverge from general to specific at times",
                    "Within paragraphs, lack of unity so not everything relates to one main idea and paragraphs tend to ramble",
                    "Serious inadequacies in paragraphing",
                    "No attempt at paragraphing",
                    "No awareness of academic conventions is evidenced"
            },
            {
                "Cohesive Devices",
                    "Sophisticated use of a wide variety of cohesive devices",
                    "Effective use of variety of cohesive devices",
                    "Mostly successful use of a variety of cohesive devices though some faulty or mechanical cohesion",
                    "Some successful attempts to connect and develop main ideas using cohesive devices",
                    "Limited successful use of cohesive devices",
                    "Very limited range of cohesive devices often used unsuccessfully",
                    "No logical coherence",
                    "No awareness of academic conventions is evidenced"
            },
            {
                "Conclusion",
                    "Elegant conclusion",
                    "Effective conclusion ending with personalised future focus",
                    "Relevant conclusion ending with a standard future focus",
                    "Attempted conclusion not fully successful",
                    "Limited conclusion with questionable relevance to argument presented e.g. new ideas introduced",
                    "Conclusion bears no resemblance to introduction or body of text",
                    "No attempt at even basic cohesion",
                    "No awareness of academic conventions is evidenced"
            },
            {
                "Presentation",
                    "Exemplary presentation and consistent referencing style",
                    "Very good presentation (as 60s plus informative title, caption)",
                    "Good presentation (as 50s plus alphabetical references, IMRD headings)",
                    "Satisfactory presentation (line spacing, alignment, font)",
                    "Muddled presentation (different fonts, different spacing)",
                    "Inadequate presentation",
                    "Inadequate presentation",
                    "No awareness of academic conventions is evidenced"
            }
            }};

    public static void buildData(TablesService service) {
        if(isBuilt) return;
        List<Band> bands = buildBands();
        service.addBands(bands);

        List<Coursework> courseworks = buildCourseworks();
        service.addCourseworks(courseworks);

        List<Category> categories = buildCategories(courseworks);
        for(Category category : categories) {
            System.out.println(category);
        }

        service.addCategories(categories);

        /*buildTable(service, categories, bands);*/
    }

    private static void buildTable(TablesService service, List<Category> categories, List<Band> bands) {
        for(int subtable = 0; subtable < criteriaAndBands.length; subtable++) {
            Category category = categories.get(subtable);
            for(int row = 0; row < criteriaAndBands[subtable].length; row++) {
                Criterion criterion = new Criterion(criteriaAndBands[subtable][row][0], category);
                service.addCriterion(criterion);

                for(int column = 0; column < bands.size(); column++) {
                    Cell cell = new Cell(criterion, bands.get(column), criteriaAndBands[subtable][row][column+1]);
                    service.addCell(cell);
                }
            }
        }
    }

    private static List<Coursework> buildCourseworks() {
        List<Coursework> courseworks = new ArrayList<>();
        for(String name : courseworkNames) {
            courseworks.add(new Coursework(name));
        }
        return courseworks;
    }

    private static List<Category> buildCategories(List<Coursework> courseworks) {
        List<Category> categories = new ArrayList<>();
        for(int i = 0; i < categoryNames.length; i++) {
            for(int j = 0; j < categoryNames[i].length; j++) {
                //categories.add(new Category(categoryNames[i][j]));
            }
        }
        return categories;
    }

    private static List<Band> buildBands() {
        List<Band> bands = new ArrayList<>();
        for(String name : bandNames) {
            bands.add(new Band(name));
        }
        return bands;
    }

}
