package com.sduwh.liutao.searchengine.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/8 19:33
 */
public class SimhashUtils {

    private static final JiebaSegmenter SEGMENTER = new JiebaSegmenter();
    private static final int BIT_NUM = 64;
    private static final int RADIX = 2;

    public static long simhash(String content) {
        List<SegToken> lsegStr = SEGMENTER.process(content, JiebaSegmenter.SegMode.SEARCH);
        Integer[] weight = new Integer[BIT_NUM];
        Arrays.fill(weight, 0);
        for (SegToken st : lsegStr) {
            long wordHash = Murmur3.hash64(st.word.getBytes());
            for (int i = 0; i < BIT_NUM; i++) {
                if (((wordHash >> i) & 1) == 1) {
                    weight[i] += 1;
                }
                else {
                    weight[i] -= 1;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BIT_NUM; i++) {
            if (weight[i] > 0) {
                sb.append(1);
            } else {
                sb.append(0);
            }

        }
        return new BigInteger(sb.toString(), RADIX).longValue();
    }

    public static int hamming(long s1, long s2) {
        int dis = 0;
        for (int i = 0; i < BIT_NUM; i++) {
            if ((s1 >> i & 1) != (s2 >> i & 1)) {
                dis++;
            }
        }
        return dis;
    }

}
