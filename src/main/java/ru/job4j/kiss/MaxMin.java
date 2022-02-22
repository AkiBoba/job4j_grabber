package ru.job4j.kiss;

import java.util.Comparator;
import java.util.List;
/**
 * @author Vladimir Likhachev
 */

public class MaxMin {
    public <T> T max(List<T> value, Comparator<T> comparator) {
        return minmax(value, comparator);
    }

    public <T> T min(List<T> value, Comparator<T> comparator) {
        return minmax(value, comparator.reversed());
    }

    static <T> T minmax(List<T> value, Comparator<T> comparator) {
        T maxValue = value.get(0);
        for (T val : value) {
            if (comparator.compare(val, maxValue) > 0) {
                maxValue = val;
            }
        }
        return maxValue;
    }
}