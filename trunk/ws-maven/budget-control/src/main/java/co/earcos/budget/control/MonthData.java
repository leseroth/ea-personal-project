package co.earcos.budget.control;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import co.earcos.budget.dao.ControlDao;
import co.earcos.budget.dao.DBConnection;
import co.earcos.budget.util.Constants.Account;
import co.earcos.budget.util.Constants.Concept;

/**
 * 
 * @author Erik
 */
public class MonthData {

    private int year;
    private int month;
    private Map<Account, Double> accountTotal;
    private Map<Account, Double> accountInterest;
    private Map<Concept, Double> conceptTotal;
    private DayData[][] daysOfMonth;

    public MonthData(int year, int month) {
        this.year = year;
        this.month = month;

        accountTotal = new EnumMap<Account, Double>(Account.class);
        accountInterest = new EnumMap<Account, Double>(Account.class);
        conceptTotal = new EnumMap<Concept, Double>(Concept.class);

        Connection conn = DBConnection.getConnection();
        ControlDao controlDao = new ControlDao();
        getCalendarDayData(conn, controlDao);
        controlDao.loadMonthTotal(conn, this);
        loadMonthInterest(controlDao, conn);
        controlDao.loadMonthConcept(conn, Concept.LOAN, this);
        controlDao.loadMonthConcept(conn, Concept.XD_APPS, this);
        DBConnection.closeConnection();
    }

    private void loadMonthInterest(ControlDao controlDao, Connection conn) {
        for (Account account : Account.values()) {
            if (!account.isCreditCard() && account != Account.CASH) {
                setAccountInterest(account, controlDao.loadMonthAccountConcept(conn, account, Concept.INTERESTS, this));
            }
        }
    }

    @SuppressWarnings("unused")
    private void getCalendarDayData(Connection conn, ControlDao controlDao) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1, 0, 0, 0);

        int startDay = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(year, month, 2 - startDay);
        Date startDate = calendar.getTime();

        calendar.set(year, month + 1, 0);
        int finalDay = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(year, month + 1, 7 - finalDay);
        Date finalDate = calendar.getTime();

        int dayDuration = 1000 * 60 * 60 * 24;
        long weekCount = (long) (dayDuration + finalDate.getTime() - startDate.getTime()) / (dayDuration * 7);

        daysOfMonth = new DayData[(int) weekCount][7];

        calendar.setTime(startDate);
        weekLoop: for (int i = 0; i < weekCount; i++) {
            dayLoop: for (int j = 0; j < 7; j++) {
                if ((i == 0 && j < startDay - 1) || (i == weekCount - 1 && j >= finalDay)) {
                    daysOfMonth[i][j] = null;
                } else {
                    daysOfMonth[i][j] = controlDao.getDayData(conn, calendar.getTime());
                }
                calendar.add(Calendar.DATE, 1);
            }
        }
    }

    public String getFixedMonth() {
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append(month < 9 ? "0" + (month + 1) : month + 1);
        return sb.toString();
    }

    public void setAccountTotal(Account account, double total) {
        accountTotal.put(account, total);
    }

    public void setAccountInterest(Account account, double variation) {
        accountInterest.put(account, variation);
    }

    public void setConceptTotal(Concept concept, double total) {
        conceptTotal.put(concept, total);
    }

    public double getAccountTotal(Account account) {
        Double total = accountTotal.get(account);
        return total == null ? 0.0 : total;
    }

    public double getAccountInterest(Account account) {
        Double total = accountInterest.get(account);
        return total == null ? 0.0 : total;
    }

    public double getConceptTotal(Concept concept) {
        Double total = conceptTotal.get(concept);
        return total == null ? 0.0 : total;
    }

    public double getSubTotal() {
        double subtotal = 0;
        for (Account account : Account.values()) {
            if (!account.isCreditCard()) {
                subtotal = subtotal + getAccountTotal(account);
            }
        }
        return subtotal;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public DayData[][] getDaysOfMonth() {
        return daysOfMonth;
    }
}