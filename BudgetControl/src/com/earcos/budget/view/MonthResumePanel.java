package com.earcos.budget.view;

import com.earcos.budget.control.MonthData;
import com.earcos.budget.util.Util;
import com.earcos.budget.util.Util.Account;
import com.earcos.budget.util.Util.Concept;
import java.awt.Color;
import javax.swing.*;

public class MonthResumePanel extends JPanel {

    private int rowCount;
    private int colCount = 3;

    public void restartMonthResumePanel(MonthData monthData) {
        removeAll();

        setLayout(new SpringLayout());
        rowCount = 0;

        double loanTotal = monthData.getConceptTotal(Concept.LOAN);
        double xdAppsTotal = monthData.getConceptTotal(Concept.XD_APPS);

        addInterestSection(monthData);
        addVariationSection(monthData);
        addBalanceSection(monthData);
        addOthersSection(loanTotal, xdAppsTotal);
        addTotalSection(monthData, loanTotal, xdAppsTotal);

        Util.makeCompactGrid(this, rowCount, colCount, 6, 6, Util.GAP, Util.GAP);
        setAlignmentY(BOTTOM_ALIGNMENT);
        setMaximumSize(getPreferredSize());
    }

    private void addInterestSection(MonthData monthData) {
        addTitleRow("Intereses");
        for (Account account : Account.values()) {
            if (!account.isCreditCard() && account != Account.CASH) {
                double value = monthData.getAccountInterest(account);
                addRow(new JLabel(account.getLabel(), JLabel.TRAILING), initValueLabel(value), initValueLabel(value / monthData.getDayCount()));
            }
        }
    }

    private void addVariationSection(MonthData monthData) {
        addTitleRow("Variación");
        for (Account account : Account.values()) {
            if (!account.isCreditCard()) {
                double value = monthData.getAccountVariation(account);
                addRow(new JLabel(account.getLabel(), JLabel.TRAILING), initValueLabel(value), initValueLabel(value / monthData.getDayCount()));
            }
        }
    }

    private void addBalanceSection(MonthData monthData) {
        addTitleRow("Saldo");
        for (Account account : Account.values()) {
            addRow(new JLabel(account.getLabel(), JLabel.TRAILING), initValueLabel(monthData.getAccountTotal(account)));
        }
    }

    private void addOthersSection(double loanTotal, double xdAppsTotal) {
        if (loanTotal + xdAppsTotal != 0) {
            addTitleRow("Otros");
        }
        if (loanTotal != 0) {
            addRow(new JLabel(Concept.LOAN.getLabel() + "s", JLabel.TRAILING), initValueLabel(loanTotal));
        }
        if (xdAppsTotal != 0) {
            addRow(new JLabel(Concept.XD_APPS.getLabel(), JLabel.TRAILING), initValueLabel(xdAppsTotal));
        }
    }

    private void addTotalSection(MonthData monthData, double loanTotal, double xdAppsTotal) {
        addTitleRow("Total");
        if (loanTotal + xdAppsTotal == 0) {
            addRow(new JLabel("Total", JLabel.TRAILING), initValueLabel(monthData.getSubTotal()));
        } else {
            addRow(new JLabel("Subtotal", JLabel.TRAILING), initValueLabel(monthData.getSubTotal()));
            addRow(new JLabel("Total", JLabel.TRAILING), initValueLabel(monthData.getSubTotal() - loanTotal - xdAppsTotal));
        }

    }

    private JLabel initValueLabel(double value) {
        JLabel label = null;
        label = new JLabel(Util.getCurrencyValue(value < 0 ? -value : value), JLabel.RIGHT);
        label.setForeground(value < 0 ? Color.red : Color.black);
        label.setBorder(BorderFactory.createEmptyBorder(0, 2 * Util.GAP, 0, 0));
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
