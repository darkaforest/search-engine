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
 * @date 2019/4/9 16:22
 */

@Entity
@Table(name = "s_same_record")
@GenericGenerator(name = "uuid", strategy = "uuid")
public class SameRecord {

    @Id
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "s_origin_id")
    private String originId;

    @Column(name = "s_same_id")
    private String sameId;

    @Column(name = "s_hd")
    private String hammingDis;

    public SameRecord() {
    }

    public SameRecord(String originId, String sameId, String hammingDis) {
        this.originId = originId;
        this.sameId = sameId;
        this.hammingDis = hammingDis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getSameId() {
        return sameId;
    }

    public void setSameId(String sameId) {
        this.sameId = sameId;
    }

    public String getHammingDis() {
        return hammingDis;
    }

    public void setHammingDis(String hammingDis) {
        this.hammingDis = hammingDis;
    }

    @Override
    public String toString() {
        return "SameRecord{" +
                "id='" + id + '\'' +
                ", originId='" + originId + '\'' +
                ", sameId='" + sameId + '\'' +
                ", hammingDis='" + hammingDis + '\'' +
                '}';
    }
}
