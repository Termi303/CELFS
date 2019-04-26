package uk.ac.bris.celfs.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.bris.celfs.database.Keyword;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KeywordServiceTest {
    @Autowired
    protected KeywordService keywordService;

    private int checkIfKeywordIsInserted(List<Keyword> keywords, Keyword expected) {
        for(int i = 0; i < keywords.size(); i++) {
            if(keywords.get(i).equals(expected)) {
                return i;
            }
        }
        assert false;
        return -1;
    }

    @Test
    public void testAddKeywordByObject() {
        Integer numberOfKeywords = keywordService.getAllKeywordsForTests().size();

        Keyword keyword = new Keyword("KEY", "VALUE");
        keywordService.addKeyword(keyword);

        List<Keyword> keywords = keywordService.getAllKeywordsForTests();
        assertEquals(numberOfKeywords+1, keywords.size());
        checkIfKeywordIsInserted(keywords, keyword);
    }

    @Test
    public void testAddKeywordByStrings() {
        Integer numberOfKeywords = keywordService.getAllKeywordsForTests().size();

        String key = "KEY1";
        String value = "VALUE1";
        keywordService.addKeyword(key, value);

        List<Keyword> keywords = keywordService.getAllKeywordsForTests();
        assertEquals(numberOfKeywords+1, keywords.size());
        boolean found = false;
        for(int i = 0; i < keywords.size(); i++) {
            if(keywords.get(i).getKey().equals(key) && keywords.get(i).getValue().equals(value)) {
                found = true;
                break;
            }
        }
        assertEquals(true, found);
    }

    @Test
    public void testGetKeywordMap() {
        Integer numberOfKeywords = keywordService.getAllKeywordsForTests().size();
        Map<String, String> keywordMap = keywordService.getKeywordMap();

        assertEquals(numberOfKeywords, (Integer)keywordMap.size());
    }
}
