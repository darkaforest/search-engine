package com.sduwh.liutao.searchengine.service;

import com.sduwh.liutao.searchengine.builder.SearchResultBuilder;
import com.sduwh.liutao.searchengine.builder.SearchResultsBuilder;
import com.sduwh.liutao.searchengine.dao.SameRecordRepository;
import com.sduwh.liutao.searchengine.dao.SearchDataRepository;
import com.sduwh.liutao.searchengine.entity.SameRecord;
import com.sduwh.liutao.searchengine.entity.SearchData;
import com.sduwh.liutao.searchengine.model.SearchResultOut;
import com.sduwh.liutao.searchengine.model.SearchResultsOut;
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
public class SearchService {

    private static final String SPLITER = " ";

    @Autowired
    private SearchDataRepository searchDataRepository;

    @Autowired
    private SameRecordRepository sameRecordRepository;

    public SearchResultsOut search(String query, Integer pageIndex, Integer pageSize) {
        //TO DO  匹配度算法有问题，应该统计出现次数而不是是否出现，考虑效率问题暂todo
        if (StringUtils.isEmpty(query)) {
            return new SearchResultsOut();
        }
        List<SearchData> preList = searchDataRepository.findByTextSearch(query);
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
                if (preContent.contains(keyword)) {
                    count += 1;
                }
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
        for (SearchData orderData : view) {
            List<SameRecord> sameRecords = sameRecordRepository.findByOrOriginId(orderData.getId());
            List<SearchData> sameData = new ArrayList<>();
            if (sameRecords != null && !sameRecords.isEmpty()) {
                for (SameRecord sameRecord : sameRecords) {
                    sameData.add(searchDataRepository.findById(sameRecord.getSameId()).orElse(null));
                }
            }
            results.add(new SearchResultBuilder().build(orderData, sameData));
        }
        return new SearchResultsBuilder().build(results, orderList.size());
    }

}
