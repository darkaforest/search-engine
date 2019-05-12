package com.sduwh.liutao.searchengine.service;

import com.sduwh.liutao.searchengine.builder.SearchResultBuilder;
import com.sduwh.liutao.searchengine.builder.SearchResultsBuilder;
import com.sduwh.liutao.searchengine.dao.SameRecordRepository;
import com.sduwh.liutao.searchengine.dao.SearchDataRepository;
import com.sduwh.liutao.searchengine.dao.SearchHistoryRepository;
import com.sduwh.liutao.searchengine.entity.SameRecord;
import com.sduwh.liutao.searchengine.entity.SearchData;
import com.sduwh.liutao.searchengine.entity.SearchHistory;
import com.sduwh.liutao.searchengine.model.SearchResultOut;
import com.sduwh.liutao.searchengine.model.SearchResultsOut;
import com.sduwh.liutao.searchengine.utils.ContentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/10 19:26
 */

@Component
@Slf4j
public class SearchService {

    private static final String SPLITER = " ";

    private static final int IPV4_MAX_LEN = 15;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private SearchDataRepository searchDataRepository;

    @Autowired
    private SameRecordRepository sameRecordRepository;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    public SearchResultsOut search(String query, Integer pageIndex, Integer pageSize, String ip) {
        if (StringUtils.isEmpty(query)) {
            return new SearchResultsOut();
        }
        //quick cache
        SearchResultsOut cache = cacheService.getPageData(query, pageIndex);
        if (cache != null) {
            return cache;
        }
        //refresh ttl or put cache if not exists
        List<SearchData> preList = cacheService.getSearchData(query);
        if (preList == null) {
            preList = searchDataRepository.findByTextSearch(query);
            cacheService.putSearchData(query, preList);
        }
        List<SearchData> orderList = new ArrayList<>();
        String[] keywords = query.split(SPLITER);
        for (SearchData preData : preList) {
            String preContent = preData.getContent();
            String preTitle = preData.getTitle();
            int count = 0;
            for (String keyword : keywords) {
                if (StringUtils.isEmpty(keyword) || keyword.equals(SPLITER)) {
                    continue;
                }
                if (preTitle.contains(keyword)) {
                    count += 100;
                }
                count += ContentUtils.apperCount(preContent, keyword);
            }
            preData.setRelevancy(count);
            orderList.add(preData);
        }
        orderList.sort(Comparator.comparing(SearchData::getRelevancy).reversed());
        int startIndex = (pageIndex - 1) * pageSize;
        //not included
        int toIndex = startIndex + pageSize > orderList.size() ? orderList.size() : startIndex + pageSize;
        List<SearchData> view = orderList.subList(startIndex, toIndex);
        List<SearchResultOut> results = new ArrayList<>();
        Iterator<SearchData> iterator = view.iterator();
        List<String> sameIds = new ArrayList<>();
        while (iterator.hasNext()) {
            SearchData orderData = iterator.next();
            List<SameRecord> sameRecords = sameRecordRepository.findByOrOriginId(orderData.getId());
            for (SameRecord sames : sameRecords) {
                sameIds.add(sames.getSameId());
            }
            boolean duplicate = false;
            for (String sameId : sameIds) {
                if (orderData.getId().equals(sameId)) {
                    iterator.remove();
                    duplicate = true;
                    break;
                }
            }
            if (duplicate) {
                continue;
            }
            List<SearchData> sameData = new ArrayList<>();
            if (sameRecords != null && !sameRecords.isEmpty()) {
                for (SameRecord sameRecord : sameRecords) {
                    sameData.add(searchDataRepository.findById(sameRecord.getSameId()).orElse(null));
                }
            }
            results.add(new SearchResultBuilder().build(orderData, sameData));
        }
        saveHistory(ip, query);
        SearchResultsOut resultsOut = new SearchResultsBuilder().build(results, orderList.size());
        cacheService.putPageData(query, resultsOut, pageIndex);
        return resultsOut;
    }

    private void saveHistory(String ip, String content) {
        if (StringUtils.isEmpty(ip) || ip.length() > IPV4_MAX_LEN || StringUtils.isEmpty(content)) {
            return;
        }
        searchHistoryRepository.save(new SearchHistory(ip, System.currentTimeMillis(), content));
    }

}
