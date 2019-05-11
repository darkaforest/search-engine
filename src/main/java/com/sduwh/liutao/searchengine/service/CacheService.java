package com.sduwh.liutao.searchengine.service;

import com.sduwh.liutao.searchengine.dao.KeyWordDataRepository;
import com.sduwh.liutao.searchengine.dao.SearchDataRepository;
import com.sduwh.liutao.searchengine.entity.KeyWordData;
import com.sduwh.liutao.searchengine.entity.SearchData;
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

    private final static String PREFIX = "c:";

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
        redisUtils.getRedis().delete(redisUtils.getRedis().keys(PREFIX + "*"));
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
        redisUtils.getRedis().opsForValue().set(PREFIX + key, data, TTL, TTL_UNIT);
    }

    @SuppressWarnings("unchecked")
    public List<SearchData> getSearchData(String key) {
        List<SearchData> data = (List<SearchData>) redisUtils.getRedis().opsForValue().get(PREFIX + key);
        if (data != null && !data.isEmpty()) {
            redisUtils.getRedis().expire(PREFIX + key, TTL, TTL_UNIT);
        }
        return data;
    }

}
