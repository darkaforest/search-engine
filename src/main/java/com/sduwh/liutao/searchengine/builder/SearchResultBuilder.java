package com.sduwh.liutao.searchengine.builder;

import com.sduwh.liutao.searchengine.entity.SearchData;
import com.sduwh.liutao.searchengine.model.SearchResultOut;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/3/30 19:56
 */
public class SearchResultBuilder {

    private static final int DEFAULT_UNI_LENGTH = 120;

    public SearchResultOut build() {
        return new SearchResultOut();
    }

    public SearchResultOut build(SearchData from) {
        return build(from, new String[0]);
    }

    public SearchResultOut build(SearchData from, String[] keywords) {
        return build(from, keywords, DEFAULT_UNI_LENGTH);
    }

    public SearchResultOut build(SearchData from, String[] keywords, int uniLength) {
        if (from == null) {
            return new SearchResultOut();
        }
        String preContent = from.getContent();
        int firstFoundIndex = keywords.length == 0 ? 0 : preContent.indexOf(keywords[0]);
        if (firstFoundIndex == -1) {
            for (String keyword : keywords) {
                if (StringUtils.isEmpty(keyword) || keyword.equals(" ")) {
                    continue;
                }
                firstFoundIndex = preContent.indexOf(keyword);
                if (firstFoundIndex != -1) {
                    break;
                }
            }
        }
        if (firstFoundIndex == -1) {
            firstFoundIndex = 0;
        }
        int length = firstFoundIndex + uniLength > preContent.length() ? preContent.length() - firstFoundIndex - 1 : uniLength;
        String uniContent = preContent.substring(firstFoundIndex, firstFoundIndex + length);
        return new SearchResultOut(from.getTitle(), uniContent, from.getUrl(), from.getTime(), from.getSource(), from.getImgUrl(), null);
    }

}
