package ru.job4j.grabber.utils;

import java.time.*;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, LocalDate> DAY = Map.ofEntries(
            Map.entry("сегодня", LocalDate.now()),
            Map.entry("вчера", LocalDate.now().minusDays(1))
    );

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

        LocalDate date;
        LocalTime time;
        String[] arrayList = parse.split(", ");
        time = LocalTime.of(
                Integer.parseInt(arrayList[1].split(":")[0]),
                Integer.parseInt(arrayList[1].split(":")[1].substring(0, 1))
        );

        if (arrayList[0].split(" ").length > 2) {
            date = LocalDate.of(Integer.parseInt("20".concat(arrayList[0].split(" ")[2])),
                    Integer.parseInt(MONTHS.get(arrayList[0].split(" ")[1])),
                    Integer.parseInt(arrayList[0].split(" ")[0]));
        } else {
            date = DAY.get(arrayList[0]);
        }
        return LocalDateTime.of(
                date,
                time
        );
    }
}
