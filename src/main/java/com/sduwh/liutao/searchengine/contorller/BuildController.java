package com.sduwh.liutao.searchengine.contorller;

import com.sduwh.liutao.searchengine.service.BuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/9 16:55
 */

@RestController
public class BuildController {

    private static final String PASS_CODE = "aaa";

    @Autowired
    private BuildService buildService;

    @RequestMapping(value = "/build/start", method = RequestMethod.GET)
    public String build(@RequestParam(name = "passcode", required = false) String passcode) {
        if (passcode == null) {
            return "admin only";
        }
        if (passcode.equals(PASS_CODE)) {
            buildService.buildAsync();
            return "passcode right, starting build";
        }
        else {
            return "passcode wrong";
        }
    }

}
