package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        int count = 0;
        String url = "https://www.sql.ru/forum/job-offers/";
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        for (int page = 1; page < 6; page++) {
            Document doc = Jsoup.connect(String.format(url + "%s", page)).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                Element parent = td.parent();
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(sqlRuDateTimeParser.parse(
                        parent.child(5).text())
                    );
                System.out.println(count++);
                }
            }
    }
}