package ru.job4j.design.srp;

import ru.job4j.design.Store;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Calendar;
import java.util.function.Predicate;

/**
 * @author Vladimir Likhachev
 */
public class ReportIT implements Report {
    private Store store;

    public ReportIT(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        ReportEngine reportEngine = new ReportEngine(store);
        return reportEngine.generate(filter);
    }

    public String generateWeb() {
        StringBuilder text = new StringBuilder();
        text.append("<head>Report for IT</head>");
        for (String employee : generate(employee -> true)
                .split(System.lineSeparator())) {
            text
                    .append("<body>")
                    .append(employee)
                    .append("</body>")
                    .append(System.lineSeparator());
        }
        return text.toString();
    }

    public static void main(String[] args) {
        MemStore store = new MemStore();
        Calendar now = Calendar.getInstance();
        Employee worker = new Employee("Ivan", now, now, 100);
        store.add(worker);
        ReportIT engine = new ReportIT(store);
        engine.generateWeb();
    }
}
