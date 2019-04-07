package com.sduwh.liutao.searchengine.model;

import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/7 09:57
 */
public class SuggestionsOut {

    private Integer count;

    private List<String> suggestions;

    public SuggestionsOut() {
    }

    public SuggestionsOut(Integer count, List<String> suggestions) {
        this.count = count;
        this.suggestions = suggestions;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}
