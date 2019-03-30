package com.sduwh.liutao.searchengine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/3/30 19:46
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultsOut {

    private Integer count;
    private Integer index;
    private Integer size;
    private List<SearchResultOut> data;

    public SearchResultsOut() {
    }

    public SearchResultsOut(Integer count, Integer index, Integer size, List<SearchResultOut> data) {
        this.count = count;
        this.index = index;
        this.size = size;
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<SearchResultOut> getData() {
        return data;
    }

    public void setData(List<SearchResultOut> data) {
        this.data = data;
    }
}
