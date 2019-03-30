package com.sduwh.liutao.searchengine.builder;

import com.sduwh.liutao.searchengine.model.SearchResultOut;
import com.sduwh.liutao.searchengine.model.SearchResultsOut;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/3/30 19:58
 */

@Slf4j
public class SearchResultsBuilder {

    public SearchResultsOut build(List<SearchResultOut> from) {
        if (from == null || from.isEmpty()) {
            log.error("[build] build SearchResultsOut fail, para is null or empty");
            return new SearchResultsOut();
        }
        SearchResultsOut results = new SearchResultsOut();
        results.setCount(from.size());
        results.setData(from);
        return results;
    }

}
