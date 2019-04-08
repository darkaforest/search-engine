package com.sduwh.liutao.searchengine.utils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final int HASH_BIT_LENGTH = 64;

    private static final int HASH_MAP_MIN_CAPACITY = 1024;

    private static final int HASH_MAP_INITIAL_CAPACITY = 1024;

    private static final int RADIX = 2;

    private static final BigInteger HASH_BASE = new BigInteger("1000003");

    private static final BigInteger HASH_MASK = new BigInteger("2").pow(HASH_BIT_LENGTH).subtract(new BigInteger("1"));

    public static long simhash(String str) {
        return simHash0(wordCounts(DepartUtils.departWords(str)));
    }

    private static Map<String, Integer> wordCounts(List<String> words) {
        if (words == null || words.isEmpty()) {
            return new HashMap<>(HASH_MAP_MIN_CAPACITY);
        }
        Map<String, Integer> map = new HashMap<>(HASH_MAP_INITIAL_CAPACITY);
        for (String word : words) {
            map.merge(word, 1, (oldVal, newVal) -> oldVal + 1);
        }
        return map;
    }

    private static long simHash0(Map<String, Integer> map) {
        int[] v = new int[HASH_BIT_LENGTH];
        for (Map.Entry<String, Integer> word2count : map.entrySet()) {
            String binStr = hash(word2count.getKey());
            for (int i = 0; i < HASH_BIT_LENGTH; i++) {
                int bitVal = Integer.parseInt(String.valueOf(binStr.charAt(i)));
                if (bitVal == 1) {
                    v[i] += word2count.getValue();
                }
                else {
                    v[i] -= word2count.getValue();
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < HASH_BIT_LENGTH; i++) {
            if (v[i] > 0) {
                v[i] = 1;
            }
            else {
                v[i] = 0;
            }
            sb.append(String.valueOf(v[i]));
        }
        return Long.parseLong(sb.toString(), RADIX);
    }

    public static int hammingDistance(long hash1, long hash2) {
        String hash1Str = long2binary(hash1);
        String hash2Str = long2binary(hash2);
        if (hash1Str.length() != hash2Str.length()) {
            return -1;
        }
        int distance = 0;
        for (int i = 0; i < HASH_BIT_LENGTH; i++) {
            if (hash1Str.charAt(i) != hash2Str.charAt(i)) {
                distance += 1;
            }
        }
        return distance;
    }

    private static String long2binary(long val) {
        return add0toStr(Long.toBinaryString(val));
    }

    private static String add0toStr(String str) {
        StringBuilder sb = new StringBuilder(str);
        while (HASH_BIT_LENGTH - sb.length() > 0) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    private static String hash(String source) {
        if (source == null || source.length() == 0) {
            return "0";
        }
        else {
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(HASH_BASE).xor(temp).and(HASH_MASK);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return long2binary(x.longValue());
        }
    }
}
