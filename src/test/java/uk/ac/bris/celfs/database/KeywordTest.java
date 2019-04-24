package uk.ac.bris.celfs.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class KeywordTest extends DatabaseTestTemplate {
    @Test
    public void testCreateOneKeyword() {
        String key = "KEY_0";
        String value = "VALUE_0";

        Keyword keyword = new Keyword(key, value);
        keywordRepository.save(keyword);

        assertEquals(key, keyword.getKey());
        assertEquals(value, keyword.getValue());
    }

    @Test
    public void testCreateMultipleKeywords() {
        String default_key = "KEY_";
        String default_value = "VALUE_";
        int howMany = 100;

        List<Keyword> keywordList = new ArrayList<>();
        for(int i = 0; i < howMany; i++) {
            keywordList.add(new Keyword(default_key+i, default_value+i));
        }
        keywordRepository.saveAll(keywordList);

        keywordList = new ArrayList<>();
        keywordRepository.findAll()
                .forEach(keywordList::add);

        assertEquals(howMany, keywordList.size());
        for(Keyword keyword : keywordList) {
            assertEquals(getIndex(keyword.getKey()), getIndex(keyword.getValue()));
        }
    }
}
