package ru.job4j.design.srp;

import java.util.function.Predicate;

/**
 * @author Vladimir Likhachev
 */
public interface Report {
    String generate(Predicate<Employee> filter);
}