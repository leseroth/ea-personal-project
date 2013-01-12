package co.earcos.budget.view;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import co.earcos.budget.control.ResumeData;
import co.earcos.budget.dao.ControlDao;
import co.earcos.budget.dao.DBConnection;
import co.earcos.budget.util.Constants;
import co.earcos.budget.view.table.CurrencyCellRenderer;
import co.earcos.budget.view.table.ResumeTableDataModel;

/**
 * 
 * @author Erik
 */
public class CompleteResumePanel extends JPanel {

	private static final long serialVersionUID = -1844314896125275108L;
	private ResumeTableDataModel balanceTableModel;
	private ResumeTableDataModel conceptTableModel;
	private ResumeTableDataModel observationTableModel;

	public CompleteResumePanel() {
		JTable balanceTable = initBalanceTable("Cuenta", "Saldo", "Mes actual");
		JScrollPane balanceScroll = new JScrollPane(balanceTable);
		balanceScroll.setPreferredSize(new Dimension(500, 120));
		balanceScroll.setMaximumSize(balanceScroll.getPreferredSize());
		balanceScroll.setAlignmentX(CENTER_ALIGNMENT);

		JTable conceptTable = initBalanceTable("Cuenta", "Concepto", "Saldo",
				"Mes actual");
		JScrollPane conceptScroll = new JScrollPane(conceptTable);
		conceptScroll.setPreferredSize(new Dimension(1000, 170));
		conceptScroll.setMaximumSize(conceptScroll.getPreferredSize());
		conceptScroll.setAlignmentX(CENTER_ALIGNMENT);

		JTable observationTable = initBalanceTable("Concepto", "Observacion",
				"Saldo", "Mes actual");
		JScrollPane observationScroll = new JScrollPane(observationTable);
		observationScroll.setPreferredSize(new Dimension(1000, 170));
		observationScroll.setMaximumSize(observationScroll.getPreferredSize());
		observationScroll.setAlignmentX(CENTER_ALIGNMENT);

		Box box = Box.createVerticalBox();
		addTable("Cuentas", balanceScroll, box);
		addTable("Conceptos", conceptScroll, box);
		addTable("Observaciones", observationScroll, box);
		add(box);

		balanceTableModel = (ResumeTableDataModel) balanceTable.getModel();
		conceptTableModel = (ResumeTableDataModel) conceptTable.getModel();
		observationTableModel = (ResumeTableDataModel) observationTable
				.getModel();
	}

	private void addTable(String title, JScrollPane tableScroll, Box box) {
		JLabel titleLabel = new JLabel(title, JLabel.TRAILING);
		titleLabel.setForeground(new Color(0x33, 0x99, 0x00));
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(3 * Constants.GAP,
				0, Constants.GAP, 0));

		box.add(titleLabel);
		box.add(tableScroll);
	}

	public void resetCompleteResumePanel() {
		try {
			Connection conn = DBConnection.getConnection();
			ControlDao controlDao = new ControlDao();

			String fixedMonth = ExpenseControlFrame.controlFrame.getMonthData()
					.getFixedMonth();

			balanceTableModel.clearResumeDataList();
			conceptTableModel.clearResumeDataList();
			observationTableModel.clearResumeDataList();

			balanceTableModel.addResumeDataList(controlDao.loadResumeBalance(
					conn, fixedMonth));
			conceptTableModel.addResumeDataList(controlDao.loadResumeConcept(
					conn, fixedMonth));
			observationTableModel.addResumeDataList(controlDao
					.loadResumeObservation(conn, fixedMonth));
			DBConnection.closeConnection();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private JTable initBalanceTable(String... titles) {
		ResumeTableDataModel tableModel = new ResumeTableDataModel(
				new ArrayList<ResumeData>(), titles);

		JTable table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);

		CurrencyCellRenderer mtcr = new CurrencyCellRenderer();
		mtcr.setHorizontalAlignment(SwingConstants.RIGHT);
		table.getColumn("Saldo").setCellRenderer(mtcr);
		table.getColumn("Mes actual").setCellRenderer(mtcr);

		return table;
	}
}
