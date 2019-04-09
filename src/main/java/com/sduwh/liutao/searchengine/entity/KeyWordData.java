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
 * @date 2019/4/9 16:19
 */

@Entity
@Table(name = "s_keyword_data")
@GenericGenerator(name = "uuid", strategy = "uuid")
public class KeyWordData {

    @Id
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "k_content")
    private String content;

    @Column(name = "k_count")
    private Long count;

    public KeyWordData() {
    }

    public KeyWordData(String content, Long count) {
        this.content = content;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "KeyWordData{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", count=" + count +
                '}';
    }
}
