package org.mentalizr.persistence.rdbms.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class EpochMillis {

    public static ZonedDateTime asZonedDateTimeBerlin(long epochMillis) {
        Instant instant = Instant.ofEpochMilli(epochMillis);
        return ZonedDateTime.ofInstant(instant, ZoneId.of("Europe/Berlin"));
    }

    public static long from(ZonedDateTime zonedDateTime) {
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();
    }

}
