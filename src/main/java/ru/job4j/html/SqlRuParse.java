package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.MemStore;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Store;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import ru.job4j.models.Post;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SqlRuParse implements Parse {

    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        for (int page = 1; page < 6; page++) {
            try {
                Document doc = Jsoup.connect(String.format(link + "%s", page)).get();
                Elements row = doc.select(".postslisttopic");
                for (Element td : row) {
                    Element href = td.child(0);
                    if (href.attr("href").toLowerCase(Locale.ROOT).contains("java")
                            && !href.attr("href").toLowerCase(Locale.ROOT).contains("javascript")) {
                        posts.add(detail(href.attr("href")));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    public Post detail(String url) {
        String title = null;
        String description = null;
        LocalDateTime created = null;
        try {
            Document doc = Jsoup.connect(url).get();
            Element first = doc.selectFirst(".msgTable");
            title = first.select(".messageHeader").get(0).ownText();
            description = first.select(".msgBody").get(1).ownText();
            String date = first.select(".msgFooter").get(0).ownText();
            created = dateTimeParser.parse(date.substring(0, date.indexOf("[")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Post(title, url, description, created);
    }

    public static void main(String[] args) {
        String url = "https://www.sql.ru/forum/job-offers/";
        DateTimeParser dateTimeParser = new SqlRuDateTimeParser();
        Parse parse = new SqlRuParse(dateTimeParser);
        parse.list(url).forEach(System.out :: println);
        }
}