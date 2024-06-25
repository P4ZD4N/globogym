package com.p4zd4n.globogym.enums;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

@Getter
public enum OpeningHours {

    MONDAY(LocalTime.of(9, 0), LocalTime.of(21, 0)),
    TUESDAY(LocalTime.of(9, 0), LocalTime.of(21, 0)),
    WEDNESDAY(LocalTime.of(9, 0), LocalTime.of(21, 0)),
    THURSDAY(LocalTime.of(9, 0), LocalTime.of(21, 0)),
    FRIDAY(LocalTime.of(9, 0), LocalTime.of(21, 0)),
    SATURDAY(null, null),
    SUNDAY(null, null);

    private final LocalTime openingTime;
    private final LocalTime closingTime;

    OpeningHours(LocalTime openingTime, LocalTime closingTime) {

        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public static boolean isOpenNow(DayOfWeek dayOfWeek) {

        OpeningHours hours = getByDayOfWeek(dayOfWeek);

        if (hours.getOpeningTime() == null || hours.getClosingTime() == null) {
            return false;
        }

        LocalTime now = LocalTime.now();
        return now.isAfter(hours.getOpeningTime()) && now.isBefore(hours.getClosingTime());
    }

    public static OpeningHours getByDayOfWeek(DayOfWeek dayOfWeek) {

        return switch (dayOfWeek) {
            case MONDAY -> OpeningHours.MONDAY;
            case TUESDAY -> OpeningHours.TUESDAY;
            case WEDNESDAY -> OpeningHours.WEDNESDAY;
            case THURSDAY -> OpeningHours.THURSDAY;
            case FRIDAY -> OpeningHours.FRIDAY;
            case SATURDAY -> OpeningHours.SATURDAY;
            case SUNDAY -> OpeningHours.SUNDAY;
        };
    }

    public static Map<DayOfWeek, String> getAllOpeningHours() {

        Map<DayOfWeek, String> openingHoursMap = new EnumMap<>(DayOfWeek.class);

        for (DayOfWeek day : DayOfWeek.values()) {

            OpeningHours hours = getByDayOfWeek(day);

            if (hours.getOpeningTime() == null || hours.getClosingTime() == null) {
                openingHoursMap.put(day, "Closed");
            } else {
                openingHoursMap.put(day, hours.getOpeningTime() + " - " + hours.getClosingTime());
            }
        }

        return openingHoursMap;
    }
}
