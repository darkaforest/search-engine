package com.sduwh.liutao.searchengine.utils;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/8 19:41
 */
public class ContentUtils {

    private static final Pattern PATTERN = Pattern.compile("(?<=[\\x{4e00}-\\x{9fa5}])\\s+(?=[\\x{4e00}-\\x{9fa5}])");

    public static String getContent(String html){
        try {
            return ContentExtractor.getContentByHtml(html).replace("　", "");
        } catch (Exception e) {
            return "";
        }
    }

}
