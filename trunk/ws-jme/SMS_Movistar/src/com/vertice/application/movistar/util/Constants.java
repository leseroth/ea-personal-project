package com.vertice.application.movistar.util;

import java.util.Random;

public class Constants {

    public static final String NUMERO_CORTO_CAEQ = "5800";
    public static final String NUMERO_CORTO_CASI = "5800";
    public static final String NUMERO_CORTO_CA = "7779";
    public static final String NUMERO_CORTO_CPCV = "582";
    public static final String NUMERO_CORTO_CSC = "5800";
    public static final String NUMERO_CORTO_PROM = "5810";
    public static final String NUMERO_CORTO_PLAN = "1123";
    
    public static final String TRAMA_CAEQ = "70001";
    public static final String TRAMA_CASI = "70002";
    public static final String TRAMA_CA = "70003";
    public static final String TRAMA_CPCV = "70004";
    public static final String TRAMA_CSC = "70005";
    public static final String TRAMA_PROM = "70007";
    
    public static final String ASTERISCO_MARCADO = "*808";
    public static final String CSC_ASTERISCO_MARCADO = "*809";
    public static final String ICCID_PREFIX = "895106";
    public static final String LLAVE_ACCESO = "1234567890";
    public static final String SEPARADOR_CSC = ",";
    
    private static final Random RANDOM = new Random();

    public static String getLlaveAcceso() {
        int high = RANDOM.nextInt(100000);
        int low = RANDOM.nextInt(100000);

        if (high == 0 || high == 100000) {
            high = 12345;
        }
        if (low == 0 || low == 100000) {
            high = 67890;
        }

        String higher = Formater.getFixedStringFillWithZerosAtTheEnd(high + "", 5);
        String lower = Formater.getFixedStringFillWithZerosAtTheEnd(low + "", 5);
        return higher + lower;
    }
}