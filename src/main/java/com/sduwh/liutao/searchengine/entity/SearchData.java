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
 * @date 2019/4/9 15:53
 */

@Entity
@Table(name = "s_search_data")
@GenericGenerator(name = "uuid", strategy = "uuid")
public class SearchData {

    @Id
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "s_title")
    private String title;

    @Column(name = "s_content")
    private String content;

    @Column(name = "s_url")
    private String url;

    @Column(name = "s_time")
    private Long time;

    @Column(name = "s_simhash")
    private Long simhash;

    @Column(name = "s_img_url")
    private String imgUrl;

    @Column(name = "s_source")
    private String source;

    @Column(name = "s_raw_id")
    private String rawId;

    public SearchData() {
    }

    public SearchData(String title, String content, String url, Long time, Long simhash, String imgUrl, String source, String rawId) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.time = time;
        this.simhash = simhash;
        this.imgUrl = imgUrl;
        this.source = source;
        this.rawId = rawId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getSimhash() {
        return simhash;
    }

    public void setSimhash(Long simhash) {
        this.simhash = simhash;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRawId() {
        return rawId;
    }

    public void setRawId(String rawId) {
        this.rawId = rawId;
    }

    @Override
    public String toString() {
        return "SearchData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", time=" + time +
                ", simhash=" + simhash +
                ", imgUrl='" + imgUrl + '\'' +
                ", source='" + source + '\'' +
                ", rawId='" + rawId + '\'' +
                '}';
    }
}
