package com.sduwh.liutao.searchengine.dao;

import com.sduwh.liutao.searchengine.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/4/14 11:57
 */
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, String> {
}
