package co.earcos.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

/**
 *
 * @author earcos
 */
public class FormattingTools {

    public static String formatDecimal(String format, int number) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(number);
    }

    public static String getFormattedDate(Date date) {
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
    }
}
