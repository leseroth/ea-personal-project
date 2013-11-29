package com.vertice.application.movistar.util;

import java.util.Calendar;

public class CalendarData {

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        return getFixedString(calendar.get(Calendar.YEAR), 4)
                + getFixedString(calendar.get(Calendar.MONTH) + 1, 2)
                + getFixedString(calendar.get(Calendar.DAY_OF_MONTH), 2);
    }

    public static String getHour() {
        Calendar calendar = Calendar.getInstance();
        return getFixedString(calendar.get(Calendar.HOUR_OF_DAY), 2)
                + getFixedString(calendar.get(Calendar.MINUTE), 2)
                + getFixedString(calendar.get(Calendar.SECOND), 2);
    }

    private static String getFixedString(int aux, int c) {
        String res = aux + "";
        while (res.length() < c) {
            res = "0" + res;
        }
        return res;
    }
}