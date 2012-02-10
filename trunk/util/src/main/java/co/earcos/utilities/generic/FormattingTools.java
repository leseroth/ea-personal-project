package co.earcos.util.generic;

import java.text.DecimalFormat;

/**
 *
 * @author earcos
 */
public class FormattingTools {

    public static void main(String... args) {
        FormattingTools ft = new FormattingTools();
        System.out.println(ft.formatDecimal("0000", 1));
        System.out.println(ft.formatDecimal("0000", 123 % 10000));
        System.out.println(ft.formatDecimal("0000", 123456 % 10000));
        System.out.println(ft.formatDecimal("0000", -1));
    }

    public String formatDecimal(String format, int number) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(number);
    }
}
