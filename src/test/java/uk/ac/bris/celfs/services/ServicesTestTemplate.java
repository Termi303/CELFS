package uk.ac.bris.celfs.services;


import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.coursework.CourseworkTestTemplate;

import javax.annotation.Resource;

public class ServicesTestTemplate extends CourseworkTestTemplate {
    @Resource
    protected CourseworkEntryService courseworkEntryService;
    @Resource
    protected KeywordService keywordService;
    @Resource
    protected StudentService studentService;
    @Resource
    protected TablesService tablesService;
    @Resource
    protected UserService userService;
}
