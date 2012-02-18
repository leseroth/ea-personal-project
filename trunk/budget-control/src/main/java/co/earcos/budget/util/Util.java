package co.earcos.budget.util;

import co.earcos.budget.util.Constants.Account;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Util {

    private static final Properties properties;
    private static Log log = LogFactory.getLog(Util.class);

    static {
        properties = new Properties();
        try {
            properties.load(getResource("user.properties"));
        } catch (IOException ex) {
            log.error("The properties file was not loaded", ex);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static InputStream getResource(String path) {
        return ClassLoader.getSystemResourceAsStream(path);
    }

    public static String getCurrencyValue(Account account, double value) {
        return account.getCurrencyFormat().format(value);
    }

    public static String getCurrencyValue(double value) {
        return Constants.DEFAULT_CURRENCY.format(value);
    }

    public static Account getAccountById(String id) {
        Account account = null;
        for (Account acc : Account.values()) {
            if (acc.getId().equals(id)) {
                account = acc;
                break;
            }
        }
        return account;
    }

    public static java.sql.Date getSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static Date getDate(java.sql.Date date) {
        return new Date(date.getTime());
    }
}
