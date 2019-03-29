package uk.ac.bris.celfs.database;

import uk.ac.bris.celfs.coursework.CourseworkRepository;

import javax.annotation.Resource;

public class DatabaseTestTemplate {

    @Resource
    protected CourseworkRepository courseworkRepository;
    @Resource
    protected CategoryRepository categoryRepository;
    @Resource
    protected BandRepository bandRepository;

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

}
