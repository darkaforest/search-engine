package com.sduwh.liutao.searchengine.service;

import com.sduwh.liutao.searchengine.builder.SuggestionsBuilder;
import com.sduwh.liutao.searchengine.dao.KeyWordDataRepository;
import com.sduwh.liutao.searchengine.entity.KeyWordData;
import com.sduwh.liutao.searchengine.model.SuggestionsOut;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/13 18:18
 */

@Slf4j
@Component
public class KeyWordService {

    private static final int KEYWORD_MAX_LEN = 8;

    private static final int KEYWORD_MAX_COUNT = 6;

    private static final long KEYWORD_MAX_APPERR = 10000000L;

    @Autowired
    private KeyWordDataRepository keyWordRepository;

    public void updateKeyWord(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            log.error("[updateKeyWord] keyword is null or empty");
        }
        if (keyword.length() > KEYWORD_MAX_LEN) {
            keyword = keyword.substring(0, KEYWORD_MAX_LEN);
        }
        KeyWordData data = keyWordRepository.findByContent(keyword);
        if (data == null) {
            keyWordRepository.save(new KeyWordData(keyword, 1L));
        }
        else {
            if (data.getCount() < KEYWORD_MAX_APPERR) {
                data.setCount(data.getCount() + 1);
            }
            keyWordRepository.save(data);
        }
    }

    public SuggestionsOut getKeyWords(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            log.error("[updateKeyWord] keyword is null or empty");
        }
        if (keyword.length() > KEYWORD_MAX_LEN) {
            keyword = keyword.substring(0, KEYWORD_MAX_LEN);
        }
        List<KeyWordData> list = keyWordRepository.findByContentContainingOrderByCountDesc(keyword);
        if (list == null || list.isEmpty()) {
            return new SuggestionsOut();
        }
        int count = 0;
        List<String> results = new ArrayList<>();
        for (KeyWordData key : list) {
            count++;
            if (count > KEYWORD_MAX_COUNT) {
                break;
            }
            results.add(key.getContent());
        }
        return new SuggestionsBuilder().build(results);
    }

}
