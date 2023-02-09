package com.second.security.common;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalTimeUtil {
    public static ZonedDateTime toDateTime(Instant utc_time){
        return ZonedDateTime.ofInstant(utc_time.truncatedTo(ChronoUnit.MILLIS), ZoneOffset.systemDefault());
    }

    public static String toDateTimeString(Instant utc_time){
        return ZonedDateTime.ofInstant(utc_time.truncatedTo(ChronoUnit.SECONDS), ZoneOffset.systemDefault())
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static String toDateString(Instant utc_time){
        return ZonedDateTime.ofInstant(utc_time.truncatedTo(ChronoUnit.SECONDS), ZoneOffset.systemDefault())
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
