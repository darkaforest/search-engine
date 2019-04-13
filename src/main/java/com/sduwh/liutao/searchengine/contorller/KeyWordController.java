package com.sduwh.liutao.searchengine.contorller;

import com.sduwh.liutao.searchengine.model.SuggestionsOut;
import com.sduwh.liutao.searchengine.service.KeyWordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/13 18:32
 */

@Slf4j
@RestController
public class KeyWordController {

    @Autowired
    private KeyWordService keyWordService;

    @RequestMapping(value = "/keywords/get", method = RequestMethod.GET)
    public SuggestionsOut autoKeywords(@RequestParam(name = "key", required = false) String key) {
        if (StringUtils.isEmpty(key)) {
            return new SuggestionsOut();
        }
        return keyWordService.getKeyWords(key);
    }

    @RequestMapping(value = "/keyword/put", method = RequestMethod.GET)
    public void putKeyword(@RequestParam(name = "key", required = false) String key) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        keyWordService.updateKeyWord(key);
    }

}
