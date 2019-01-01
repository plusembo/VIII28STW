package com.eighttwentyeightsoftware.pensiltikbackend.util;

import java.text.SimpleDateFormat;

public class SimpleDateFormatFactory {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT;
    private static final SimpleDateFormat SIMPLE_TIMESTAMP_FORMAT;

    static {
        SIMPLE_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static SimpleDateFormat getSimpleTimesTampFormat() {
        return SIMPLE_DATE_FORMAT;
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        return SIMPLE_DATE_FORMAT;
    }

}
