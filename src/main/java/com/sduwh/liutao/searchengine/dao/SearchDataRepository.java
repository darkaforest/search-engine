package com.sduwh.liutao.searchengine.dao;

import com.sduwh.liutao.searchengine.entity.SearchData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/9 17:07
 */
public interface SearchDataRepository extends JpaRepository<SearchData, String> {

    @Query(value = "select * from s_search_data where to_tsvector('chinese', s_title || s_content) @@ plainto_tsquery('chinese', ?1)", nativeQuery = true)
    List<SearchData> findByTextSearch(@Param("query") String query);

}
