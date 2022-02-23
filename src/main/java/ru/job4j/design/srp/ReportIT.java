package ru.job4j.design.srp;

import ru.job4j.design.Store;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
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

    public void generateWeb() {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(9000)) {
                while (!server.isClosed()) {
                    Socket socket = server.accept();
                    try (OutputStream out = socket.getOutputStream()) {
                        out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                        for (String employee : generate(employee -> true)
                                .split(System.lineSeparator())) {
                            out.write(employee
                                    .getBytes(Charset.forName("Windows-1251")));
                            out.write(System
                                    .lineSeparator()
                                    .getBytes());
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
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
