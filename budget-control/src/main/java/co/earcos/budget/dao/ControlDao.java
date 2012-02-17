package co.earcos.budget.dao;

import co.earcos.budget.control.DayData;
import co.earcos.budget.control.MonthData;
import co.earcos.budget.control.ResumeData;
import co.earcos.budget.util.Constants.Account;
import co.earcos.budget.util.Constants.Concept;
import co.earcos.budget.util.Util;
import co.earcos.budget.view.ExpenseControlFrame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Erik
 */
public class ControlDao {

  public static final int CURRENT_MONTH = 1;
  public static final int LAST_YEAR = 2;
  private static final SimpleDateFormat queryDateFormat = new SimpleDateFormat("yyyy/MM/dd");

  public void loadMonthTotal(Connection conn, MonthData monthData) {
    try {
      String sql = "select mov.cuenta, sum(valor) total from TBL_MOVIMIENTO mov "
              + "where formatdatetime(mov.fecha,'yyyyMM') <= ? group by mov.cuenta";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, monthData.getFixedMonth());
      ResultSet result = stmt.executeQuery();

      while (result.next()) {
        String account = result.getString("cuenta");
        monthData.setAccountTotal(Util.getAccountById(account), result.getDouble("total"));
      }

      if (result != null) {
        result.close();
      }
      if (stmt != null) {
        stmt.close();
      }

    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }
  }

  public void loadMonthVariation(Connection conn, MonthData monthData) {

    try {
      String sql = "select mov.cuenta, sum(valor) variacion from TBL_MOVIMIENTO mov "
              + "where formatdatetime(mov.fecha,'yyyyMM') = ? group by mov.cuenta";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, monthData.getFixedMonth());
      ResultSet result = stmt.executeQuery();

      while (result.next()) {
        String account = result.getString("cuenta");
        monthData.setAccountVariation(Util.getAccountById(account), result.getDouble("variacion"));
      }

      if (result != null) {
        result.close();
      }
      if (stmt != null) {
        stmt.close();
      }

    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }
  }

  public void loadMonthConcept(Connection conn, Concept concept, MonthData monthData) {
    try {
      String sql = "select sum(valor) total from TBL_MOVIMIENTO mov "
              + "where formatdatetime(mov.fecha,'yyyyMM') <= ? and concepto = ?";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, monthData.getFixedMonth());
      stmt.setString(2, concept.getLabel());
      ResultSet result = stmt.executeQuery();

      while (result.next()) {
        monthData.setConceptTotal(concept, result.getDouble("total"));
      }

      if (result != null) {
        result.close();
      }
      if (stmt != null) {
        stmt.close();
      }

    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }
  }

  public Double loadMonthAccountConcept(Connection conn, Account account, Concept concept, MonthData monthData) {
    Double monthAccountConcept = null;

    try {
      String sql = "select sum(valor) total from TBL_MOVIMIENTO mov "
              + "where formatdatetime(mov.fecha,'yyyyMM') = ? and cuenta = ? and concepto = ?";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, monthData.getFixedMonth());
      stmt.setString(2, account.getId());
      stmt.setString(3, concept.getLabel());
      ResultSet result = stmt.executeQuery();

      while (result.next()) {
        monthAccountConcept = result.getDouble("total");
      }

      if (result != null) {
        result.close();
      }
      if (stmt != null) {
        stmt.close();
      }

    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }

    return monthAccountConcept;
  }

  public DayData getDayData(Connection conn, Date date) {
    DayData dayData = new DayData(date);

    try {
      String sql = "select mov.cuenta, sum(valor) total from TBL_MOVIMIENTO mov "
              + "where mov.fecha = ? group by mov.fecha, mov.cuenta order by fecha asc";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setDate(1, Util.getSqlDate(date));
      ResultSet result = stmt.executeQuery();

      while (result.next()) {
        String account = result.getString("cuenta");
        dayData.setAccountDayTotal(Util.getAccountById(account), result.getDouble("total"));
      }

      dayData.setMovementList((new MovementDao()).loadAllByDate(conn, Util.getSqlDate(date)));

      if (result != null) {
        result.close();
      }
      if (stmt != null) {
        stmt.close();
      }

    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }

    return dayData;
  }

  public List<String[]> loadAccountChartData(Connection conn, int option) {
    List<String[]> chartData = new ArrayList<String[]>();

    try {
      String sql = getAccountChartQuery(option);
      PreparedStatement stmt = conn.prepareStatement(sql);
      ResultSet result = stmt.executeQuery();

      while (result.next()) {
        String column = result.getString("valor");

        for (int i = Account.values().length - 1; i >= 0; i--) {
          Account account = Account.values()[i];

          if (!account.isCreditCard()) {
            String total = result.getString(account.getLabel());
            chartData.add(new String[]{total == null ? "0.0" : total, account.getLabel(), column});
          }
        }
      }

      if (result != null) {
        result.close();
      }
      if (stmt != null) {
        stmt.close();
      }

    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }

    return chartData;
  }

  private String getAccountChartQuery(int option) {
    List<String> values = getAxisXValues(option);

    StringBuilder sb = new StringBuilder();

    sb.append("select ");
    sb.append("valores.valor");
    for (Account account : Account.values()) {
      if (!account.isCreditCard()) {
        sb.append(",(select sum(mov.valor) from tbl_movimiento mov where formatdatetime(mov.fecha,'yyyy/MM/dd')<=valores.valor and cuenta = '");
        sb.append(account.getId());
        sb.append("') ");
        sb.append(account.getLabel());
      }
    }
    sb.append(" from (select '");
    sb.append(values.get(0));
    sb.append("' valor from dual");
    for (int i = 1; i < values.size(); i++) {
      sb.append(" union select '");
      sb.append(values.get(i));
      sb.append("' from dual");
    }
    sb.append(") valores order by valores.valor asc");

    return sb.toString();
  }

  private List<String> getAxisXValues(int option) {
    List<String> values = new ArrayList<String>();
    Calendar startCalendar = Calendar.getInstance();

    switch (option) {
      case CURRENT_MONTH:
        int[] date = ExpenseControlFrame.controlFrame.getDateSelectionPanel().getSelectedDate();
        startCalendar.set(date[0], date[1], 1);
        int lastDay = startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < lastDay; i++) {
          values.add(queryDateFormat.format(startCalendar.getTime()));
          startCalendar.add(Calendar.DATE, 1);
        }
        break;
      case LAST_YEAR:
        startCalendar.add(Calendar.MONTH, -10);

        for (int i = 0; i < 12; i++) {
          startCalendar.set(Calendar.DATE, 1);
          startCalendar.add(Calendar.DATE, -1);
          values.add(queryDateFormat.format(startCalendar.getTime()));
          startCalendar.add(Calendar.DATE, 33);
        }
        break;
    }

    return values;
  }

  private double getDouble(ResultSet result, String colName) throws SQLException {
    Double value = result.getDouble(colName);
    return value == null ? 0.0d : value.doubleValue();
  }

  public List<ResumeData> loadResumeBalance(Connection conn, String fixedMonth) throws SQLException {
    List<ResumeData> balanceData = new ArrayList<ResumeData>();

    String sql = "select tm.cue,"
            + "(select sum(mov.valor) from tbl_movimiento mov where mov.cuenta = tm.cue ) total,"
            + "(select sum(mov.valor) from tbl_movimiento mov where mov.cuenta = tm.cue and formatdatetime(mov.fecha,'yyyyMM') = ?) mesactual"
            + " from (select distinct(tm.cuenta) cue from tbl_movimiento tm) tm order by tm.cue";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, fixedMonth);
    ResultSet result = stmt.executeQuery();

    while (result.next()) {
      ResumeData resumeData = new ResumeData();

      resumeData.setAccount(Util.getAccountById(result.getString("cue")));
      resumeData.setBalance(getDouble(result, "total"));
      resumeData.setCurrentMonth(getDouble(result, "mesactual"));

      balanceData.add(resumeData);
    }

    if (result != null) {
      result.close();
    }
    if (stmt != null) {
      stmt.close();
    }

    return balanceData;
  }

  public List<ResumeData> loadResumeConcept(Connection conn, String fixedMonth) throws SQLException {
    List<ResumeData> conceptData = new ArrayList<ResumeData>();

    String sql = "select info.* from (select tm.cue, tm.con, "
            + "(select sum(mov.valor) from tbl_movimiento mov where mov.cuenta || mov.concepto = tm.cue || tm.con ) total, "
            + "(select sum(mov.valor) from tbl_movimiento mov where mov.cuenta || mov.concepto = tm.cue || tm.con and formatdatetime(mov.fecha,'yyyyMM')=?) mesactual "
            + "from (select tm.cuenta cue, tm.concepto con from tbl_movimiento tm where tm.concepto not in ('Saldo','XD Apps','Movimiento','Prestamo','Universidad') group by tm.cuenta, tm.concepto) tm "
            + ") info where info.mesactual is not null order by info.cue";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, fixedMonth);
    ResultSet result = stmt.executeQuery();

    while (result.next()) {
      ResumeData resumeData = new ResumeData();

      resumeData.setAccount(Util.getAccountById(result.getString("cue")));
      resumeData.setConcept(result.getString("con"));
      resumeData.setBalance(getDouble(result, "total"));
      resumeData.setCurrentMonth(getDouble(result, "mesactual"));

      conceptData.add(resumeData);
    }

    if (result != null) {
      result.close();
    }
    if (stmt != null) {
      stmt.close();
    }

    return conceptData;
  }

  public List<ResumeData> loadResumeObservation(Connection conn, String fixedMonth) throws SQLException {
    List<ResumeData> observationData = new ArrayList<ResumeData>();

    String sql = "select * from (select tm.con, tm.obs, "
            + "(select sum(mov.valor) from tbl_movimiento mov where mov.concepto || mov.observacion = tm.con || tm.obs) total, "
            + "(select sum(mov.valor) from tbl_movimiento mov where mov.concepto || mov.observacion = tm.con || tm.obs and formatdatetime(mov.fecha,'yyyyMM')=?) mesactual "
            + "from (select tm.concepto con, tm.observacion obs from tbl_movimiento tm where tm.observacion is not null group by tm.concepto, tm.observacion) tm) tm "
            + "where tm.total != 0 and tm.mesactual != 0 order by tm.con, tm.obs";
    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, fixedMonth);
    ResultSet result = stmt.executeQuery();

    while (result.next()) {
      ResumeData resumeData = new ResumeData();

      resumeData.setConcept(result.getString("con"));
      resumeData.setObservation(result.getString("obs"));
      resumeData.setBalance(getDouble(result, "total"));
      resumeData.setCurrentMonth(getDouble(result, "mesactual"));

      observationData.add(resumeData);
    }

    if (result != null) {
      result.close();
    }
    if (stmt != null) {
      stmt.close();
    }

    return observationData;
  }
}
