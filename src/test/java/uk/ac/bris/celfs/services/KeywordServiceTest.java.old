package uk.ac.bris.celfs.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.database.Keyword;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

@DataJpaTest
@RunWith(SpringRunner.class)
public class KeywordServiceTest extends ServicesTestTemplate {

    @Test
    public void testAddKeywordByObject() {
        Keyword keyword = new Keyword("KEY", "VALUE");
        keywordService.addKeyword(keyword);

        List<Keyword> keywords = keywordService.getAllKeywordsForTests();
        assertEquals(1, keywords.size());
        assertEquals(keyword, keywords.get(0));
    }

    @Test
    public void testAddKeywordByStrings() {
        String key = "KEY";
        String value = "VALUE";
        keywordService.addKeyword(key, value);

        List<Keyword> keywords = keywordService.getAllKeywordsForTests();
        assertEquals(1, keywords.size());
        assertEquals(key, keywords.get(0).getKey());
        assertEquals(value, keywords.get(0).getValue());
    }
}
