package ru.job4j.design.srp;

import ru.job4j.design.Store;

import java.util.function.Predicate;

/**
 * @author Vladimir Likhachev
 */
public class ReportBuh implements Report {

    private Store store;
    public static final int TAX = 80;

    public ReportBuh(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Hired; Fired; SalaryInUSD;");
        for (Employee employee : store.findBy(filter)) {
            text
                    .append(System.lineSeparator())
                    .append(employee.getName()).append(";")
                    .append(employee.getHired()).append(";")
                    .append(employee.getFired()).append(";")
                    .append(employee.getSalary() / ReportBuh.TAX).append(";")
                    .append(System.lineSeparator());
        }
        return text.toString();
    }
}
