package co.earcos.budget.view;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import co.earcos.budget.control.DayData;
import co.earcos.budget.control.MonthData;
import co.earcos.budget.util.Constants;

public class CalendarPanel extends JPanel {

	private static final long serialVersionUID = 9048795964687553570L;
	private DayData[][] daysOfMonth;

	public void initCalendarPanel(MonthData monthData) {
		removeAll();
		daysOfMonth = monthData.getDaysOfMonth();

		GridLayout gridLayout = new GridLayout(daysOfMonth.length, 7);
		gridLayout.setHgap(Constants.GAP);
		gridLayout.setVgap(Constants.GAP);
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