package com.sduwh.liutao.searchengine.service;

import com.sduwh.liutao.searchengine.builder.SearchDataBuilder;
import com.sduwh.liutao.searchengine.dao.RawDataRepository;
import com.sduwh.liutao.searchengine.dao.SameRecordRepository;
import com.sduwh.liutao.searchengine.dao.SearchDataRepository;
import com.sduwh.liutao.searchengine.entity.SameRecord;
import com.sduwh.liutao.searchengine.entity.SearchData;
import com.sduwh.liutao.searchengine.utils.SimhashUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/9 17:03
 */

@Slf4j
@Component
public class BuildService {

    private static final int HAMMING_DISTANCE_LIMIT = 0;

    private static final int SPAM_LIMIT = 5;

    @Autowired
    private RawDataRepository rawDataRepository;

    @Autowired
    private SearchDataRepository searchDataRepository;

    @Autowired
    private SameRecordRepository sameRecordRepository;

    @Async
    public void buildAsync() {
        buildTransactional();
    }

    @Transactional(rollbackFor = Exception.class)
    public void buildTransactional() {
        log.info("[buildTransactional] start clean search data @ {}", new Date(System.currentTimeMillis()));
        searchDataRepository.deleteAll();
        log.info("[buildTransactional] end clean search data @ {}", new Date(System.currentTimeMillis()));
        log.info("[buildTransactional] start gen search data @ {}", new Date(System.currentTimeMillis()));
        List<Object> list = rawDataRepository.findAllId();
        Map<Long, Integer> simCountMap = new HashMap<>(list.size() / 2);
        for (Object id : list) {
            SearchData searchData = new SearchDataBuilder().build(rawDataRepository.findById((String) id).orElse(null));
            if (searchData.getSimhash() == 0) {
                continue;
            }
            simCountMap.merge(searchData.getSimhash(), 1,  (oldVal, newVal) -> oldVal + 1);
            searchDataRepository.save(searchData);
        }
        log.info("[buildTransactional] end gen search data @ {}", new Date(System.currentTimeMillis()));
        log.info("[buildTransactional] start spam data remove @ {}", new Date(System.currentTimeMillis()));
        for (Map.Entry<Long, Integer> simCount : simCountMap.entrySet()) {
            if (simCount.getValue() > SPAM_LIMIT) {
                List<SearchData> temp = searchDataRepository.findBySimhash(simCount.getKey());
                searchDataRepository.deleteBySimhash(simCount.getKey());
                searchDataRepository.save(temp.get(0));
            }
        }
        log.info("[buildTransactional] end spam data remove @ {}", new Date(System.currentTimeMillis()));
        calculateSame();
    }

    public void calculateSame() {
        log.info("[calculateSame] start clean same records @ {}", new Date(System.currentTimeMillis()));
        sameRecordRepository.deleteAll();
        log.info("[calculateSame] end clean same records @ {}", new Date(System.currentTimeMillis()));
        log.info("[calculateSame] start calculate hamming distance @ {}", new Date(System.currentTimeMillis()));
        List<Object[]> originList = searchDataRepository.findAllIdAndSimhash();
        for (Object[] origin : originList) {
            Object originSimhashObj = origin[1];
            if (originSimhashObj == null) {
                continue;
            }
            Long originSimhash = ((BigInteger) originSimhashObj).longValue();
            String originId = (String) origin[0];
            for (Object[] same : originList) {
                Long sameHash = ((BigInteger) same[1]).longValue();
                String sameId = (String) same[0];
                int hammingDistance = SimhashUtils.hamming(originSimhash, sameHash);
                if (hammingDistance <= HAMMING_DISTANCE_LIMIT && !originId.equals(sameId)) {
                    sameRecordRepository.save(new SameRecord(originId, sameId, hammingDistance));
                }
            }
        }
        log.info("[calculateSame] end calculate hamming distance @ {}", new Date(System.currentTimeMillis()));
    }

}
