package com.cityu.mongodb.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

/**
 * Handle mongodb template date zone problem
 * @author James Mung
 * */
public class DateUtils {
    private DateUtils(){}

    public static Date getCurrentZoneDate(Date date) {
        return getZoneDate(date, ZoneId.systemDefault());
    }

    public static Date getZoneDate(Date date, ZoneId zoneId) {
        Instant instant = date.toInstant();
        instant.atZone(zoneId);
        return Date.from(instant);
    }
}
