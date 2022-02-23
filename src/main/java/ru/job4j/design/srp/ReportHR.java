package ru.job4j.design.srp;

import ru.job4j.design.Store;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Vladimir Likhachev
 */
public class ReportHR implements Report {

    private Store store;

    Comparator<Double> comparator = Comparator.comparingDouble(o -> o);

    public ReportHR(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Salary;");
        List<Employee> list = store.findBy(filter);
        list.sort((x, y) -> Double.compare(y.getSalary(), x.getSalary()));
        for (Employee employee : list) {
            text
                    .append(System.lineSeparator())
                    .append(employee.getName()).append(";")
                    .append(employee.getSalary()).append(";");
        }
        return text.toString();
    }
}