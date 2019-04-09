package com.sduwh.liutao.searchengine.service;

import com.sduwh.liutao.searchengine.builder.SearchDataBuilder;
import com.sduwh.liutao.searchengine.dao.RawDataRepository;
import com.sduwh.liutao.searchengine.dao.SearchDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/9 17:03
 */

@Component
public class BuildService {

    @Autowired
    private RawDataRepository rawDataRepository;

    @Autowired
    private SearchDataRepository searchDataRepository;

    @Async
    public void buildAsync() {
        buildTransactional();
    }

    @Transactional(rollbackFor = Exception.class)
    public void buildTransactional() {
        System.out.println("start time: " + new Date(System.currentTimeMillis()));
        List<Object> list = rawDataRepository.findAllId();
        int i = 0;
        for (Object id : list) {
            if (i >= 10000) {
                break;
            }
            searchDataRepository.save(new SearchDataBuilder().build(rawDataRepository.findById((String) id).orElse(null)));
            i++;
        }
        System.out.println("end time: " + new Date(System.currentTimeMillis()));
    }

}
