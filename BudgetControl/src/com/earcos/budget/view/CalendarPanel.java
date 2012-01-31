package com.earcos.budget.view;

import com.earcos.budget.control.DayData;
import com.earcos.budget.control.MonthData;
import com.earcos.budget.util.Util;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarPanel extends JPanel {

    private DayData[][] daysOfMonth;

    public void initCalendarPanel(MonthData monthData) {
        removeAll();
        daysOfMonth = monthData.getDaysOfMonth();

        GridLayout gridLayout = new GridLayout(daysOfMonth.length, 7);
        gridLayout.setHgap(Util.GAP);
        gridLayout.setVgap(Util.GAP);
        setLayout(gridLayout);

        for (int i = 0; i < daysOfMonth.length; i++) {
            for (int j = 0; j < 7; j++) {
                JComponent component;
                if (daysOfMonth[i][j] == null) {
                    component = new JLabel("");
                } else {
                    DayPanel dayPanel = new DayPanel();
                    dayPanel.init(daysOfMonth[i][j]);
                    component = dayPanel;
                }
                add(component);
            }
        }

        setAlignmentY(BOTTOM_ALIGNMENT);
    }
}
