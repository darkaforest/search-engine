package com.sduwh.liutao.searchengine.model;

import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/2 15:21
 */
public class SameResultsOut {

    private Integer count;

    private List<SameResultOut> data;

    public SameResultsOut() {
    }

    public SameResultsOut(Integer count, List<SameResultOut> data) {
        this.count = count;
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<SameResultOut> getData() {
        return data;
    }

    public void setData(List<SameResultOut> data) {
        this.data = data;
    }
}
