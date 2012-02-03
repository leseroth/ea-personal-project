package co.earcos.budget.view;

import co.earcos.budget.control.DayData;
import co.earcos.budget.control.DayResumeData;
import co.earcos.budget.control.MonthData;
import co.earcos.budget.util.Util;
import co.earcos.budget.util.Util.Account;
import java.awt.Color;
import javax.swing.*;

public class DayResumePanel extends JPanel {

    private DayResumeData dayResumeData;
    private int rowCount;

    public void initDayResumePanel(MonthData monthData, DayData dayData) {
        removeAll();

        dayResumeData = new DayResumeData(monthData);
        dayResumeData.calculateVariation(dayData.getMovementList());

        setLayout(new SpringLayout());
        rowCount = 0;

        for (Account account : Account.values()) {
            addRow(new JLabel(account.getLabel(), JLabel.TRAILING), initValueLabel(dayResumeData.getAccountVariation(account)));
            addRow(new JLabel("", JLabel.TRAILING), initValueLabel(dayResumeData.getAccountTotal(account)));

            addEmptyRow();
        }

        Util.makeCompactGrid(this, rowCount, 2, 6, 6, 6, 6);
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(BOTTOM_ALIGNMENT);
        setMaximumSize(getPreferredSize());
        setBorder(BorderFactory.createEmptyBorder(Util.GAP, Util.GAP, Util.GAP, Util.GAP));
    }

    private JLabel initValueLabel(double value) {
        JLabel label = null;
        label = new JLabel(Util.getCurrencyValue(value < 0 ? -value : value), JLabel.RIGHT);
        label.setForeground(value < 0 ? Color.red : Color.black);
        return label;
    }

    private void addRow(JComponent component1, JComponent component2) {
        add(component1);
        add(component2);
        ++rowCount;
    }

    private void addEmptyRow() {
        add(new JLabel("", JLabel.TRAILING));
        add(new JLabel("", JLabel.RIGHT));
        ++rowCount;
    }

    public DayResumeData getDayResumeData() {
        return dayResumeData;
    }
}
