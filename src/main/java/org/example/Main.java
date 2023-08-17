package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String url = "https://computer.cnu.ac.kr/computer/index.do";
        try {
            Document document = Jsoup.connect(url).get();
            Elements articles = document.select("a.articleTitle");

            for (Element article : articles) {
                String title = article.select("span.mini-title").text();
                String link = article.attr("href");

                String url2 = "https://computer.cnu.ac.kr"+link;
                Document document2 = Jsoup.connect(url2).get();

                Elements contentDiv = document2.select(".fr-view");
                String content = contentDiv.text();

                System.out.println("제목: " + title);
                System.out.println("링크: " + link);
                System.out.println("내용 : " + content + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}