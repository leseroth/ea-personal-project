package com.earcos.budget.control;

import com.earcos.budget.dao.ControlDao;
import com.earcos.budget.dao.DBConnection;
import com.earcos.budget.dao.MovementDao;
import com.earcos.budget.util.Util.Account;
import com.earcos.budget.util.Util.Concept;
import com.earcos.budget.model.MovementVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Erik
 */
public class MonthData {

    private static final int ADVICE_DAYS = 3;
    private static final double MAX_VARIATION = 100000.0 / 3;
    private int year;
    private int month;
    private int dayCount;
    private Map<Account, Double> accountTotal;
    private Map<Account, Double> accountVariation;
    private Map<Account, Double> accountInterest;
    private Map<Concept, Double> conceptTotal;
    private DayData[][] daysOfMonth;
    private String movementAdvice;

    public MonthData(int year, int month) {
        this.year = year;
        this.month = month;

        accountTotal = new HashMap<Account, Double>();
        accountVariation = new HashMap<Account, Double>();
        accountInterest = new HashMap<Account, Double>();
        conceptTotal = new HashMap<Concept, Double>();

        Connection conn = DBConnection.getConnection();
        ControlDao controlDao = new ControlDao();
        getCalendarDayData(conn, controlDao);
        controlDao.loadMonthTotal(conn, this);
        controlDao.loadMonthVariation(conn, this);
        loadMonthInterest(controlDao, conn);
        controlDao.loadMonthConcept(conn, Concept.LOAN, this);
        controlDao.loadMonthConcept(conn, Concept.XD_APPS, this);
        calculateAdvice(conn);
        DBConnection.closeConnection();
    }

    private void loadMonthInterest(ControlDao controlDao, Connection conn) {
        for (Account account : Account.values()) {
            if (!account.isCreditCard() && account != Account.CASH) {
                setAccountInterest(account, controlDao.loadMonthAccountConcept(conn, account, Concept.INTERESTS, this));
            }
        }
    }

    private void getCalendarDayData(Connection conn, ControlDao controlDao) {
        Calendar calendar = Calendar.getInstance();

        if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == month) {
            dayCount = calendar.get(Calendar.DATE);
            calendar.set(year, month, 1, 0, 0, 0);
        } else {
            calendar.set(year, month, 1, 0, 0, 0);
            dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

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
        dayDuration = 0;
        weekLoop:
        for (int i = 0; i < weekCount; i++) {
            dayLoop:
            for (int j = 0; j < 7; j++) {
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

    private void calculateAdvice(Connection conn) {
        movementAdvice = null;

        try {
            Calendar todayCalendar = Calendar.getInstance();
            todayCalendar.add(Calendar.DATE, -15);
            List<MovementVO> movementList = (new MovementDao()).loadAdviceInfo(conn);

            int count = 0;
            double variation = 0d;
            boolean constantGrow = true;
            boolean constantDecrease = true;
            for (MovementVO mvo : movementList) {
                count++;
                variation += mvo.getValue();
                constantGrow = constantGrow && mvo.getValue() > 0;
                constantDecrease = constantDecrease && mvo.getValue() < 0;
                if (Math.abs(variation) >= MAX_VARIATION
                        || (count >= ADVICE_DAYS && (constantDecrease || constantGrow))) {
                    break;
                }
            }

            movementAdvice = "En los ultimos " + count + " dias " + Account.INDEACCION.getLabel();
            NumberFormat nf = NumberFormat.getCurrencyInstance();

            if (Math.abs(variation) >= MAX_VARIATION) {
                movementAdvice += " ha variado " + nf.format(variation);
            } else if (count >= ADVICE_DAYS && constantGrow) {
                movementAdvice += " ha ganado " + nf.format(variation);
            } else if (count >= ADVICE_DAYS && constantDecrease) {
                movementAdvice += " ha perdido " + nf.format(variation);
            } else {
                movementAdvice = null;
            }
        } catch (SQLException ex) {
            movementAdvice = "Error al calcular el consejo: " + ex;
        }
    }

    public String getMovementAdvice() {
        return movementAdvice;
    }

    public void setAccountTotal(Account account, double total) {
        accountTotal.put(account, total);
    }

    public void setAccountVariation(Account account, double variation) {
        accountVariation.put(account, variation);
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

    public double getAccountVariation(Account account) {
        Double total = accountVariation.get(account);
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

    public int getDayCount() {
        return dayCount;
    }
}
