package com.example.study_gushici;

import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PoetryScraper {
    private static final String TAG = "PoetryScraper";

    public static List<Poetry> scrapePoems() {
        List<Poetry> poems = new ArrayList<>();
        int page = 1;

        while (true) {
            String url = getPageUrl(page);
            List<Poetry> pagePoems = scrapePage(url);
            if (pagePoems.isEmpty()) break; // 无内容则停止
            poems.addAll(pagePoems);
            page++;
            try { Thread.sleep(1000); } catch (InterruptedException e) { /* 忽略 */ }
        }
        return poems;
    }

    // 修复作者和朝代提取逻辑
    private static List<Poetry> scrapePage(String url) {
        List<Poetry> pagePoems = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 Chrome/114.0.0.0") // 模拟浏览器请求头
                    .timeout(10000)
                    .get();

            Elements poemContainers = doc.select("div.gushici"); // 诗词容器

            for (Element container : poemContainers) {
                String title = container.selectFirst("p.tit a b").text(); // 标题
                String sourceInfo = container.selectFirst("p.source").text(); // 作者+朝代混合文本

                // 拆分朝代和作者（格式：朝代：作者）
                String[] parts = sourceInfo.split("："); // 注意使用全角冒号
                String dynasty = "未知朝代";
                String author = "未知作者";
                if (parts.length >= 2) {
                    dynasty = parts[0]; // 朝代
                    author = parts[1];   // 作者
                } else if (parts.length == 1) {
                    // 若只有作者或朝代，优先判断为作者（部分页面可能缺失朝代）
                    author = parts[0];
                }

                String content = container.selectFirst("div.gushici-box-text")
                        .html()
                        .replaceAll("<[^>]+>", "\n") // 移除所有HTML标签
                        .replaceAll("\\s+", "\n")    // 压缩空白字符为换行
                        .trim();

                pagePoems.add(new Poetry(title, author, content, dynasty));
            }
        } catch (IOException e) {
            Log.e(TAG, "抓取失败：" + url, e);
        }
        return pagePoems;
    }

    // 页码生成逻辑（保持不变）
    private static String getPageUrl(int page) {
        return page == 1 ?
                "https://www.gushici.net/shici/" :
                "https://www.gushici.net/shici/index_" + page + ".html";
    }
}