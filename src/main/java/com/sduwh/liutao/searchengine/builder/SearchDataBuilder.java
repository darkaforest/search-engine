package com.sduwh.liutao.searchengine.builder;

import com.sduwh.liutao.searchengine.entity.RawData;
import com.sduwh.liutao.searchengine.entity.SearchData;
import com.sduwh.liutao.searchengine.utils.ContentUtils;
import com.sduwh.liutao.searchengine.utils.SimhashUtils;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/9 17:33
 */
public class SearchDataBuilder {

    private static final int MAX_CONTENT_LEN = 2046;

    public SearchData build(RawData from) {
        if (from == null) {
            return new SearchData();
        }
        String content = ContentUtils.getContent(from.getContent());
        if (content.length() >= MAX_CONTENT_LEN) {
            content = content.substring(0, MAX_CONTENT_LEN);
        }
        long simhash = SimhashUtils.simhash(content);
        return new SearchData(from.getTitle(), content, from.getUrl(), from.getTime(), simhash, null, null, from.getId());
    }

}
