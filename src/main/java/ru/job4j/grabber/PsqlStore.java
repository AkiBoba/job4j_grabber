package ru.job4j.grabber;

import ru.job4j.models.Post;

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
                "insert into items(name, text, link, created) values (?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getDescription());
            ps.setString(3, post.getLink());
            ps.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            ps.execute();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
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
                "select * from items where id = ?;")) {
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
}
