package com.sduwh.liutao.searchengine.contorller;

import com.sduwh.liutao.searchengine.model.SnapInfoOut;
import com.sduwh.liutao.searchengine.service.SnapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/14 15:58
 */
@Slf4j
@RestController
public class SnapController
{

    @Autowired
    private SnapService snapService;

    @RequestMapping("/snap")
    public SnapInfoOut createSnap(@RequestParam(value = "url", required = false) String url) {
        return snapService.createSnap(url);
    }

}
