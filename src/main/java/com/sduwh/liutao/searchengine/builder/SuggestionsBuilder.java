package com.sduwh.liutao.searchengine.builder;

import com.sduwh.liutao.searchengine.model.SuggestionsOut;

import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/7 09:59
 */
public class SuggestionsBuilder {

    public SuggestionsOut build(List<String> suggestionList) {
        if (suggestionList == null || suggestionList.isEmpty()) {
            return new SuggestionsOut();
        }
        SuggestionsOut suggestionsOut = new SuggestionsOut();
        suggestionsOut.setCount(suggestionList.size());
        suggestionsOut.setSuggestions(suggestionList);
        return suggestionsOut;
    }

}
