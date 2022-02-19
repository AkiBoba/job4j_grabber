package ru.job4j.grabber;

import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import ru.job4j.html.SqlRuParse;
import ru.job4j.models.Post;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Vladimir Likhachev
 */
public class PsqlStore implements Store, AutoCloseable {

    Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement ps = cnn.prepareStatement(
                "insert into post(name, text, link, created) values (?, ?, ?, ?);")
        ) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getDescription());
            ps.setString(3, post.getLink());
            ps.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> list = new ArrayList<>();
        try (PreparedStatement ps = cnn.prepareStatement(
                "select * from post;")) {
            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    list.add(newPost(res)
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Post findById(int id) {
        Post item = null;
        try (PreparedStatement ps = cnn.prepareStatement(
                "select * from post where id = ?;")) {
            ps.setInt(1, id);
            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    item = newPost(res);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    private Post newPost(ResultSet res) throws SQLException {
         return new Post(
                res.getInt(1),
                res.getString(2),
                res.getString(3),
                res.getString(4),
                getLocalDateTime(res
                        .getTimestamp(5)
                )
        );
    }

    private LocalDateTime getLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    public static void main(String[] args) throws SQLException, IOException {
        String url = "https://www.sql.ru/forum/job-offers/";
        DateTimeParser dateTimeParser = new SqlRuDateTimeParser();
        Parse parse = new SqlRuParse(dateTimeParser);
        List<Post> posts = parse.list(url);
        InputStream in = PsqlStore
                .class
                .getClassLoader()
                .getResourceAsStream("app.properties");
            Properties conf = new Properties();
            conf.load(in);
        Store store = new PsqlStore(conf);
        posts.forEach(store :: save);
        System.out.println("first id : " + store.findById(34));
        store.getAll().forEach(System.out :: println);
    }
}
