package com.sduwh.liutao.searchengine.builder;

import com.sduwh.liutao.searchengine.model.SameResultOut;
import com.sduwh.liutao.searchengine.model.SameResultsOut;
import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/2 15:22
 */
public class SameResultsBuilder {

    public SameResultsOut build(List<SameResultOut> from) {
        if (from == null || from.isEmpty()) {
            return new SameResultsOut();
        }
        SameResultsOut results = new SameResultsOut();
        results.setCount(from.size());
        results.setData(from);
        return results;
    }

}
