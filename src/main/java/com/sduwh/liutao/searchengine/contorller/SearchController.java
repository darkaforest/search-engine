package com.sduwh.liutao.searchengine.contorller;

import com.sduwh.liutao.searchengine.model.SearchResultsOut;
import com.sduwh.liutao.searchengine.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/10 19:23
 */

@Slf4j
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public SearchResultsOut search(@RequestParam(name = "query", required = false) String query) {
        log.info("[search] search with : {}", query);
        if (StringUtils.isEmpty(query)) {
            return new SearchResultsOut();
        }
        return searchService.search(query);
    }

}
