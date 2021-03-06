package co.earcos.budget.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import co.earcos.budget.control.MonthData;
import co.earcos.budget.util.Constants;
import co.earcos.budget.util.Constants.Account;
import co.earcos.budget.util.Constants.Concept;
import co.earcos.budget.util.Util;
import co.earcos.util.SwingUtil;

public class MonthResumePanel extends JPanel {

	private static final long serialVersionUID = -8225988319663294943L;
	private int rowCount;
	private int colCount = 3;

	public void restartMonthResumePanel(MonthData monthData) {
		removeAll();

		setLayout(new SpringLayout());
		rowCount = 0;

		double loanTotal = monthData.getConceptTotal(Concept.LOAN);
		double xdAppsTotal = monthData.getConceptTotal(Concept.XD_APPS);

		addInterestSection(monthData);
		addBalanceSection(monthData);
		addOthersSection(loanTotal, xdAppsTotal);
		addTotalSection(monthData, loanTotal, xdAppsTotal);

		SwingUtil.makeCompactGrid(this, rowCount, colCount, 6, 6,
				Constants.GAP, Constants.GAP);
		setAlignmentY(BOTTOM_ALIGNMENT);
		setMaximumSize(getPreferredSize());
	}

	private void addInterestSection(MonthData monthData) {
		addTitleRow("Intereses");
		for (Account account : Account.values()) {
			if (!account.isCreditCard() && account != Account.CASH) {
				double value = monthData.getAccountInterest(account);
				if(value == 0)
					continue;
				addRow(new JLabel(account.getLabel(), JLabel.TRAILING), initValueLabel(account, value));
			}
		}
	}

	private void addBalanceSection(MonthData monthData) {
		addTitleRow("Saldo");
		for (Account account : Account.values()) {
			double total = monthData.getAccountTotal(account);
			if(total == 0)
				continue;
			addRow(new JLabel(account.getLabel(), JLabel.TRAILING), initValueLabel(account, total));
		}
	}

	private void addOthersSection(double loanTotal, double xdAppsTotal) {
		if (loanTotal + xdAppsTotal != 0) {
			addTitleRow("Otros");
		}
		if (loanTotal != 0) {
			addRow(new JLabel(Concept.LOAN.getLabel() + "s", JLabel.TRAILING),
					initValueLabel(Account.CASH, loanTotal));
		}
		if (xdAppsTotal != 0) {
			addRow(new JLabel(Concept.XD_APPS.getLabel(), JLabel.TRAILING),
					initValueLabel(Account.CASH, xdAppsTotal));
		}
	}

	private void addTotalSection(MonthData monthData, double loanTotal,
			double xdAppsTotal) {
		addTitleRow("Total");
		if (loanTotal + xdAppsTotal == 0) {
			addRow(new JLabel("Total", JLabel.TRAILING),
					initValueLabel(Account.CASH, monthData.getSubTotal()));
		} else {
			addRow(new JLabel("Subtotal", JLabel.TRAILING),
					initValueLabel(Account.CASH, monthData.getSubTotal()));
			addRow(new JLabel("Total", JLabel.TRAILING),
					initValueLabel(Account.CASH, monthData.getSubTotal()
							- loanTotal - xdAppsTotal));
		}

	}

	private JLabel initValueLabel(Account account, double value) {
		JLabel label = new JLabel(Util.getCurrencyValue(account,
				value < 0 ? -value : value), JLabel.RIGHT);
		label.setForeground(value < 0 ? Color.red : Color.black);
		label.setBorder(BorderFactory.createEmptyBorder(0, 2 * Constants.GAP,
				0, 0));
		return label;
	}

	private void addRow(JComponent... component) {
		for (int i = 0; i < colCount; i++) {
			if (i < component.length) {
				add(component[i]);
			} else {
				add(new JLabel(""));
			}
		}
		++rowCount;
	}

	private void addTitleRow(String title) {
		JLabel titleLabel = new JLabel(title, JLabel.TRAILING);
		titleLabel.setForeground(new Color(0x33, 0x99, 0x00));

		addRow();
		addRow();
		addRow(titleLabel);
	}
}