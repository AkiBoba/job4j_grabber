package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;
/**
 * @author Vladimir Likhachev
 */

public class MaxMin {
    public <T> T max(List<T> value, Comparator<T> comparator) {
        value.sort(comparator);
        return value.get(value.size() - 1);
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        value.sort(comparator);
        return value.get(0);
    }
}