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
 * @date 2019/4/9 16:50
 */

@Entity
@Table(name = "s_search_history")
@GenericGenerator(name = "uuid", strategy = "uuid")
public class SearchHistory {

    @Id
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "s_ip")
    private String ip;

    @Column(name = "s_time")
    private Long time;

    @Column(name = "k_content")
    private String content;

    public SearchHistory() {
    }

    public SearchHistory(String ip, Long time, String content) {
        this.ip = ip;
        this.time = time;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SearchHistory{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", time=" + time +
                ", content='" + content + '\'' +
                '}';
    }
}
