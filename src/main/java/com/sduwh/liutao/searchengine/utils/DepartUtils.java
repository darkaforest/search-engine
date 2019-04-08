package com.sduwh.liutao.searchengine.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/8 19:48
 */
public class DepartUtils {

    private static final JiebaSegmenter SEGMENTER = new JiebaSegmenter();

    private static final JiebaSegmenter.SegMode SEG_MODE = JiebaSegmenter.SegMode.SEARCH;

    public static List<String> departWords(String text) {
        if (StringUtils.isEmpty(text)) {
            return new ArrayList<>();
        }
        List<SegToken> tokens = SEGMENTER.process(text, SEG_MODE);
        if (tokens == null || tokens.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> words = new ArrayList<>();
        for (SegToken segToken : tokens) {
            words.add(segToken.word);
        }
        return words;
    }

}
