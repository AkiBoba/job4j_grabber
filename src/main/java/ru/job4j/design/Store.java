package ru.job4j.design;

import ru.job4j.design.srp.Employee;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Vladimir Likhachev
 */
public interface Store {

    List<Employee> findBy(Predicate<Employee> filter);
}
