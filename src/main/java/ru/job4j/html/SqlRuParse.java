package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import ru.job4j.models.Post;

import java.io.IOException;
import java.time.LocalDateTime;

public class SqlRuParse {

    static int countId = 0;

    public static Post parse(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element first = doc.selectFirst(".msgTable");
        String title = first.select(".messageHeader").get(0).ownText();
        String description = first.select(".msgBody").get(1).ownText();
        String date = first.select(".msgFooter").get(0).ownText();
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        LocalDateTime created = parser.parse(date.substring(0, date.indexOf("[")));
        return new Post(countId++, title, url, description, created);
    }

    public static void main(String[] args) throws IOException {
        Post newPost1 = parse("https://www.sql.ru/forum/1342199/sre-c-relokaciey-na-kipr");
        System.out.println(newPost1);
        Post newPost2 = parse("https://www.sql.ru/forum/1342252/vakansii-oebs-udalenka-180t-r");
        System.out.println(newPost2);
    }
}