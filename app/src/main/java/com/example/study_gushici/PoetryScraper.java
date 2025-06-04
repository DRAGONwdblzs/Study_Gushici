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
    private static final int MAX_PAGES = 10; // 最大抓取页数

    public static List<Poetry> scrapePoems() {
        List<Poetry> poems = new ArrayList<>();

        // 循环抓取多页内容
        for (int page = 1; page <= MAX_PAGES; page++) {
            String url = getPageUrl(page);
            List<Poetry> pagePoems = scrapePage(url);
            poems.addAll(pagePoems);

            // 如果当前页没有内容，停止抓取
            if (pagePoems.isEmpty()) {
                break;
            }

            // 添加适当的延迟，避免频繁请求导致IP被封
            try {
                Thread.sleep(1000); // 暂停1秒
            } catch (InterruptedException e) {
                Log.e(TAG, "抓取页面 " + url + " 时线程被中断", e);
            }
        }

        return poems;
    }

    // 根据页码生成URL
    private static String getPageUrl(int page) {
        if (page == 1) {
            return "https://www.gushici.net/shici/"; // 第一页URL
        } else {
            return "https://www.gushici.net/shici/index_" + page + ".html"; // 其他页URL
        }
    }

    // 抓取单页内容
    private static List<Poetry> scrapePage(String url) {
        List<Poetry> pagePoems = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url)
                    .timeout(10000)
                    .get();

            Elements poemElements = doc.select("div.gushici");

            for (Element poemElement : poemElements) {
                // 提取标题
                Element titleElement = poemElement.selectFirst("p.tit a b");
                String title = "未知标题";
                if (titleElement != null) {
                    title = titleElement.text();
                }

                // 提取朝代
                Element dynastyElement = poemElement.selectFirst("p.source a");
                String dynasty = "未知朝代";
                if (dynastyElement != null) {
                    dynasty = dynastyElement.text();
                }

                // 提取作者
                Element authorElement = poemElement.selectFirst("p.source a + a");
                String author = "未知作者";
                if (authorElement != null) {
                    author = authorElement.text();
                }

                // 提取内容
                Element contentElement = poemElement.selectFirst("div.gushici-box-text");
                String content = "无内容";
                if (contentElement != null) {
                    content = contentElement.html()
                            .replace("<p>", "").replace("</p>", "\n")  // 处理<p>标签
                            .replace("<br>", "\n")  // 处理换行
                            .trim();
                }

                // 创建Poetry对象并添加到列表
                pagePoems.add(new Poetry(title, author, content, dynasty));
            }
        } catch (IOException e) {
            Log.e(TAG, "抓取页面 " + url + " 时发生IO异常", e);
        }
        return pagePoems;
    }
}