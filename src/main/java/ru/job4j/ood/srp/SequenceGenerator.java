package ru.job4j.ood.srp;

import java.util.List;

/**
 * @author Vladimir Likhachev
 */
public interface SequenceGenerator<T> {
    List<T> generate(int size);
}
