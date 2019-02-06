package uk.ac.bris.celfs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.bris.celfs.database.Keyword;
import uk.ac.bris.celfs.database.KeywordRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    public void addKeyword(Keyword keyword) {
        keywordRepository.save(keyword);
    }

    public void addKeyword(String key, String value) {
        Keyword keyword = new Keyword(key, value);
        addKeyword(keyword);
    }

    private List<Keyword> getAllKeywords() {
        List<Keyword> keywords = new ArrayList<>();
        keywordRepository.findAll()
                .forEach(keywords::add);
    }

    public Map<String, String> getKeywordMap() {
        Map<String, String> map = new HashMap<>();
        for(Keyword keyword : getAllKeywords()) {
            map.put(keyword.getKey(), keyword.getValue());
        }
        return map;
    }
}