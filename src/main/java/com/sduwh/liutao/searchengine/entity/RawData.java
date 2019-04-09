package com.sduwh.liutao.searchengine.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/9 15:49
 */

@Entity
@Table(name = "s_raw_data")
@GenericGenerator(name = "uuid", strategy = "uuid")
public class RawData {

    @Id
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "s_time")
    private Long time;

    @Column(name = "s_depth")
    private Integer depth;

    @Column(name = "s_length")
    private Integer length;

    @Column(name = "s_url")
    private String url;

    @Column(name = "s_content")
    private String content;

    @Column(name = "s_title")
    private String title;

    public RawData() {
    }

    public RawData(Long time, Integer depth, Integer length, String url, String content, String title) {
        this.time = time;
        this.depth = depth;
        this.length = length;
        this.url = url;
        this.content = content;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "RawData{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", depth=" + depth +
                ", length=" + length +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
