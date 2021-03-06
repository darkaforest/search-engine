package com.sduwh.liutao.searchengine.dao;

import com.sduwh.liutao.searchengine.entity.KeyWordData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/13 18:17
 */
public interface KeyWordDataRepository extends JpaRepository<KeyWordData, String> {

    @Query
    KeyWordData findByContent(String key);

    @Query
    List<KeyWordData> findByContentContainingOrderByCountDesc(String key);

}
