package ru.job4j.grabber;

import ru.job4j.models.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladimir Likhachev
 */
public class MemStore implements Store {

    List<Post> posts = new ArrayList<>();

    @Override
    public void save(Post post) {
        posts.add(post);
    }

    @Override
    public List<Post> getAll() {
        return posts;
    }

    @Override
    public Post findById(int id) {
        int index = indexOf(id);
        return index != -1 ? posts.get(index) : null;
    }

    private int indexOf(int id) {
        int rsl = -1;
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId() == id) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }
}
