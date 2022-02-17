package ru.job4j.models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private int id;
    private String title;
    private String link;
    private String description;
    private LocalDateTime created;

    public Post(int id, String title, String link, String description, LocalDateTime created) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.created = created;
    }

    public Post(String title, String link, String description, LocalDateTime created) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.created = created;
    }

    public Post(String url) throws IOException {
        this.link = url;
        try {
            parse(url);
        } catch (IOException e) {
            throw new IOException("Ошибка парсинга ссылки.");
        }
    }

    private void parse(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element first = doc.selectFirst(".msgTable");
        this.title = first.select(".messageHeader").get(0).ownText();
        this.description = first.select(".msgBody").get(1).ownText();
        var date = first.select(".msgFooter").get(0).ownText().substring(0, 16);
        System.out.println(date);
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        this.created = parser.parse(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id
                && Objects.equals(link, post.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link);
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + System.lineSeparator()
                + "title='" + title
                + System.lineSeparator() + "link='" + link
                + System.lineSeparator() + "description='" + description
                + System.lineSeparator() + "created=" + created
                + '}';
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Post getPost() {
        return null;
    }

    public static void main(String[] args) throws IOException {
        Post p = new Post("https://www.sql.ru/forum/1342199/sre-c-relokaciey-na-kipr");
        System.out.println(p);
        Post newPost = new Post("https://www.sql.ru/forum/1342252/vakansii-oebs-udalenka-180t-r");
        System.out.println(newPost);
    }
}
