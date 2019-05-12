package com.sduwh.liutao.searchengine.service;

import com.sduwh.liutao.searchengine.dao.KeyWordDataRepository;
import com.sduwh.liutao.searchengine.dao.SearchDataRepository;
import com.sduwh.liutao.searchengine.entity.KeyWordData;
import com.sduwh.liutao.searchengine.entity.SearchData;
import com.sduwh.liutao.searchengine.model.SearchResultsOut;
import com.sduwh.liutao.searchengine.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/5/12 01:58
 */

@Component
public class CacheService {

    private final static String C_PREFIX = "c:";

    private final static String P_PREFIX = "p:";

    private final static String P_SUFFIX = "@";

    private final static int TTL = 1;

    private final static TimeUnit TTL_UNIT = TimeUnit.HOURS;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private KeyWordDataRepository keyWordDataRepository;

    @Autowired
    private SearchDataRepository searchDataRepository;

    @PostConstruct
    private void initCache() {
        //clear cache and gen hot data cache when startup
        redisUtils.getRedis().delete(redisUtils.getRedis().keys(C_PREFIX + "*"));
        List<KeyWordData> keys = keyWordDataRepository.findAll();
        if (keys == null || keys.isEmpty()) {
            return;
        }
        for (KeyWordData key : keys) {
            String query = key.getContent();
            if (StringUtils.isEmpty(query)) {
                continue;
            }
            List<SearchData> preList = searchDataRepository.findByTextSearch(query);
            if (preList == null || preList.isEmpty()) {
                continue;
            }
            putSearchData(query, preList);
        }
    }

    public void putSearchData(String key, List<SearchData> data) {
        redisUtils.getRedis().opsForValue().set(C_PREFIX + key, data, TTL, TTL_UNIT);
    }

    @SuppressWarnings("unchecked")
    public List<SearchData> getSearchData(String key) {
        List<SearchData> data = (List<SearchData>) redisUtils.getRedis().opsForValue().get(C_PREFIX + key);
        if (data != null && !data.isEmpty()) {
            redisUtils.getRedis().expire(C_PREFIX + key, TTL, TTL_UNIT);
        }
        return data;
    }

    public void putPageData(String key, SearchResultsOut page, int index) {
        redisUtils.getRedis().opsForValue().set(P_PREFIX + key + P_SUFFIX + index, page, TTL, TTL_UNIT);
    }

    public SearchResultsOut getPageData(String key, int page) {
        SearchResultsOut data = (SearchResultsOut) redisUtils.getRedis().opsForValue().get(P_PREFIX + key + P_SUFFIX + page);
        if (data != null) {
            redisUtils.getRedis().expire(P_PREFIX + key + P_SUFFIX + page, TTL, TTL_UNIT);
        }
        return data;
    }

}
