package com.p4zd4n.globogym.enums;

import lombok.Getter;

import java.time.LocalTime;

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

    public boolean isOpenNow() {

        if (openingTime == null || closingTime == null) {
            return false;
        }

        LocalTime now = LocalTime.now();
        return now.isAfter(openingTime) && now.isBefore(closingTime);
    }
}
