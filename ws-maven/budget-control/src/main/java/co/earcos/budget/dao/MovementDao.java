package co.earcos.budget.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import co.earcos.budget.model.MovementVO;
import co.earcos.budget.util.Constants.Account;
import co.earcos.budget.util.Constants.Concept;
import co.earcos.budget.util.Util;

public class MovementDao {

    public List<MovementVO> loadAllByDate(Connection conn, Date date) throws SQLException {
        String sql = "select mov.* from TBL_MOVIMIENTO mov where fecha = ? order by fecha asc, cuenta, concepto";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, Util.getSqlDate(date));
        return listQuery(stmt);
    }

    public List<MovementVO> loadAllByConceptAndObservation(Connection conn, String concept, String observation) throws SQLException {
        String sql = "select mov.* from tbl_movimiento mov where mov.concepto = ? and mov.observacion like ? order by mov.fecha asc";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, concept);
        stmt.setString(2, observation);
        return listQuery(stmt);
    }

    public List<MovementVO> loadAdviceInfo(Connection conn) throws SQLException {
        String sql = "select mov.* from tbl_movimiento mov where concepto = ? and cuenta = ? "
                + "and fecha > (select max(fecha) from tbl_movimiento where concepto = ? and cuenta = ?) " + "order by fecha desc, concepto desc";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, Concept.INTERESTS.getLabel());
        stmt.setString(2, Account.INDEACCION.getId());
        stmt.setString(3, Concept.MOVEMENT.getLabel());
        stmt.setString(4, Account.INDEACCION.getId());
        return listQuery(stmt);
    }

    public synchronized void create(Connection conn, MovementVO valueObject) throws SQLException {
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO TBL_MOVIMIENTO ( CODIGO, FECHA, CUENTA, VALOR, CONCEPTO, OBSERVACION) "
                    + "VALUES (SEC_MOVIMIENTO.nextval, ?, ?, ?, ?, ?) ";
            stmt = conn.prepareStatement(sql);

            stmt.setDate(1, Util.getSqlDate(valueObject.getDate()));
            stmt.setString(2, valueObject.getAccount().getId());
            stmt.setObject(3, valueObject.getValue(), Types.DOUBLE);
            stmt.setString(4, valueObject.getConcept());
            stmt.setString(5, valueObject.getObservation());

            int rowcount = stmt.executeUpdate();
            if (rowcount != 1) {
                throw new SQLException("PrimaryKey Error when updating DB!");
            }
            conn.commit();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void delete(Connection conn, MovementVO valueObject) throws SQLException {
        if (valueObject.getId() == null) {
            throw new SQLException("Can not delete without Primary-Key!");
        }

        String sql = "DELETE FROM TBL_MOVIMIENTO WHERE (CODIGO = ? ) ";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setObject(1, valueObject.getId(), Types.INTEGER);

            int rowcount = stmt.executeUpdate();
            if (rowcount == 0) {
                throw new SQLException("Object could not be deleted! (PrimaryKey not found)");
            }
            if (rowcount > 1) {
                throw new SQLException("PrimaryKey Error when updating DB! (Many objects were deleted!)");
            }
            conn.commit();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    private List<MovementVO> listQuery(PreparedStatement stmt) throws SQLException {

        ArrayList<MovementVO> searchResults = new ArrayList<MovementVO>();
        ResultSet result = null;

        try {
            result = stmt.executeQuery();

            while (result.next()) {
                MovementVO temp = new MovementVO();

                temp.setId(result.getInt("CODIGO"));
                temp.setDate(Util.getDate(result.getDate("FECHA")));
                temp.setAccount(Util.getAccountById(result.getString("CUENTA")));
                temp.setValue(result.getDouble("VALOR"));
                temp.setConcept(result.getString("CONCEPTO"));
                temp.setObservation(result.getString("OBSERVACION"));

                searchResults.add(temp);
            }

        } finally {
            if (result != null) {
                result.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return searchResults;
    }
}