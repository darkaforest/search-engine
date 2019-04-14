package com.sduwh.liutao.searchengine.service;

import com.sduwh.liutao.searchengine.dao.RawDataRepository;
import com.sduwh.liutao.searchengine.entity.RawData;
import com.sduwh.liutao.searchengine.model.SnapInfoOut;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/14 16:01
 */

@Slf4j
@Component
public class SnapService {

    @Autowired
    private RawDataRepository rawDataRepository;

    public SnapInfoOut createSnap(String url) {
        //TODO: 定时清空临时网页
        if (StringUtils.isEmpty(url)) {
            return new SnapInfoOut();
        }
        RawData rawData;
        try {
           rawData = rawDataRepository.findByUrl(url);
        }
        catch (Exception e) {
            log.error("[createSnap] url query error");
            return new SnapInfoOut();
        }
        if (rawData == null) {
            log.error("[createSnap] url not exists");
            return new SnapInfoOut();
        }
        Path folderPath = Paths.get(new File("./target/classes/static/html/").getAbsolutePath());
        String filename = rawData.getId() + ".html";
        try {
            Files.write(folderPath.resolve(rawData.getId() + ".html"), rawData.getContent().getBytes());
        } catch (IOException e) {
            log.error("gen temp html fail");
        }
        return new SnapInfoOut(filename, rawData.getTime());
    }

}
