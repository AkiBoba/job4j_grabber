package ru.job4j.grabber.utils;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "01"),
            Map.entry("фев", "02"),
            Map.entry("мар", "03"),
            Map.entry("апр", "04"),
            Map.entry("май", "05"),
            Map.entry("июн", "06"),
            Map.entry("июл", "07"),
            Map.entry("авг", "08"),
            Map.entry("сен", "09"),
            Map.entry("окт", "10"),
            Map.entry("ноя", "11"),
            Map.entry("дек", "12")
    );

    @Override
    public LocalDateTime parse(String parse) {

        LocalDate date = null;
        LocalTime time = null;
        List<String> dates;
        List<String> times;

        String string = parse;
        string = string.substring(string.indexOf(",") + 1);
        string = (parse.substring(0, parse.indexOf(","))).concat(string);

        List<String> list = List.of(string.split(" "));

        if (list.size() > 2) {
            dates = list.subList(0, 3);
            times = List.of(list.get(3).split(":"));
            date = LocalDate.of(Integer.parseInt("20".concat(dates.get(2))),
                    Integer.parseInt(MONTHS.get(dates.get(1))),
                    Integer.parseInt(dates.get(0)));
            time = LocalTime.of(Integer.parseInt(times.get(0)),
                    Integer.parseInt(times.get(1)));
        } else {
            switch (list.get(0)) {
                case ("сегодня") -> {
                    date = LocalDate.now();
                    times = List.of(list.get(1).split(":"));
                    time = LocalTime.of(Integer.parseInt(times.get(0)),
                            Integer.parseInt(times.get(1)));
                }
                case ("вчера") -> {
                    date = LocalDate.now().minusDays(1);
                    times = List.of(list.get(1).split(":"));
                    time = LocalTime.of(Integer.parseInt(times.get(0)),
                            Integer.parseInt(times.get(1)));
                }
                default -> {
                    break;
                }
            }
        }
        assert date != null;
        return LocalDateTime.of(
                date,
                time
            );
    }
}
