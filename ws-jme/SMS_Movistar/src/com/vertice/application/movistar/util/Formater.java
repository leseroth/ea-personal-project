package com.vertice.application.movistar.util;

public class Formater {

    public static String getFixedStringFillWithSpacesAtTheEnd(String aux, int c) {
        String res = aux;
        while (res.length() < c) {
            res = res + " ";
        }
        return res;
    }

    public static String getFixedStringFillWithZerosAtTheEnd(String aux, int c) {
        String res = aux;
        while (res.length() < c) {
            res = res + "0";
        }
        return res;
    }

    public static String getWhiteSpaces(int num) {
        String res = "";
        for (int i = 0; i < num; i++) {
            res += " ";
        }
        return res;
    }
}
