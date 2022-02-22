package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;
/**
 * @author Vladimir Likhachev
 */

public class MaxMin {
    public <T> T max(List<T> value, Comparator<T> comparator) {
        return minmax(value, comparator, value.size() - 1);
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return minmax(value, comparator, 0);
    }

    static <T> T minmax(List<T> value, Comparator<T> comparator, int index) {
        value.sort(comparator);
        return value.get(index);
    }
}