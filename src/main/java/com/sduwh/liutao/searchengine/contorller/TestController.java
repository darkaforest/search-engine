package com.sduwh.liutao.searchengine.contorller;

import com.sduwh.liutao.searchengine.builder.SearchResultsBuilder;
import com.sduwh.liutao.searchengine.model.SameResultOut;
import com.sduwh.liutao.searchengine.model.SameResultsOut;
import com.sduwh.liutao.searchengine.model.SearchResultOut;
import com.sduwh.liutao.searchengine.model.SearchResultsOut;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA 2018.1.5 (Ultimate Edition)
 * JRE: 1.8.0_172-release-1136-b39 x86_64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * SYS: macOS Mojave 10.14.4
 *
 * @author darkaforest
 * @date 2019/3/30 19:43
 */

@RestController
public class TestController {

    @RequestMapping(value = "/test/search", method = RequestMethod.GET)
    public SearchResultsOut testSearch() {
        SearchResultOut s1 = new SearchResultOut();
        s1.setTitle("希捷paragon ntfs for Ma|希捷paragon ntfs Mac版下载 V15.5.10 - PC6苹果网");
        s1.setUrl("http://www.pc6.com/mac/464125.html");
        s1.setSource("pc6.com");
        s1.setPic("http://thumb12.jfcdns.com/2018-11/14/16f5beb8123e3ca2.jpeg");
        s1.setTimestamp(System.currentTimeMillis());
        s1.setSummary("希捷paragon ntfs for Mac版是Mac平台上的一款专用于希捷硬盘的ntfs工具软件。希捷 paragon ntfs Mac版只能供希捷的硬盘使用，其他NTFS格式的 ...");
        List<SearchResultOut> list = new ArrayList<>();
        list.add(s1);
        SearchResultOut s2 = new SearchResultOut();
        s2.setTitle("美2024年重返月球-美2024年前要重返月球_黑龙江中公教育");
        s2.setUrl("http://hlj.offcn.com/html/2019/03/89912.html");
        s2.setSource("hlj.offcn.com");
        s2.setTimestamp(System.currentTimeMillis());
        s2.setSummary("重返月球战略要先在月球轨道上建造一个空间站，称为“门户”，将作为宇航员往返月球表面的一个中转站。此外，美国宇航局还一直致力于开发一种新型火箭，称为太空发射系统(SLS)，将用于发射一个名为“猎户座”的航天舱进入深空。但是，美国宇航局计划到2022年才开始建造这一中转站，并且到2025年左右才在月球上测试第一批载人级着陆器。此外，SLS还面临了一系列的延误和成本超支。");
        list.add(s2);
        SameResultOut s3 = new SameResultOut();
        s3.setTitle("2019互联网岳麓峰会将于4月1日至3日在长举行");
        s3.setUrl("https://hn.rednet.cn/content/2019/03/26/5259624.html");
        SameResultOut s4 = new SameResultOut();
        s4.setTitle("惊呆了!瑞幸抵押咖啡机 到底是个什么梗?");
        s4.setUrl("http://news.e23.cn/redian/2019-04-02/2019040200244.html");
        List<SameResultOut> sameList = new ArrayList<>();
        sameList.add(s3);
        sameList.add(s4);
        SameResultsOut sameResultsOut = new SameResultsOut();
        sameResultsOut.setCount(2);
        sameResultsOut.setData(sameList);
        s1.setSameData(sameResultsOut);
        s2.setSameData(sameResultsOut);
        return new SearchResultsBuilder().build(list, sameList);
    }

}
