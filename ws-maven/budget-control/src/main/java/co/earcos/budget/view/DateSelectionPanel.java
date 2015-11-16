package co.earcos.budget.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class DateSelectionPanel extends JPanel implements ItemListener, ActionListener {

    private static final long serialVersionUID = 2661308912492617928L;
    private JComboBox yearCombo;
    private JComboBox monthCombo;
    private JButton next;
    private JButton previous;

    public DateSelectionPanel() {
        initDateSelectionPanel();
    }

    private void initDateSelectionPanel() {
        String[] years = { "2011", "2012", "2013", "2014", "2015" };
        String[] months = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };

        previous = new JButton("<");
        yearCombo = new JComboBox(years);
        monthCombo = new JComboBox(months);
        next = new JButton(">");

        yearCombo.setSelectedIndex(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(years[0]));
        monthCombo.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));

        setAlignmentX(CENTER_ALIGNMENT);
        add(previous);
        add(yearCombo);
        add(monthCombo);
        add(next);

        previous.addActionListener(this);
        yearCombo.addItemListener(this);
        monthCombo.addItemListener(this);
        next.addActionListener(this);
    }

    public int[] getSelectedDate() {
        return new int[] { Integer.parseInt((String) yearCombo.getSelectedItem()), monthCombo.getSelectedIndex() };
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            ExpenseControlFrame.controlFrame.resetCalendarPanel();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        int month = monthCombo.getSelectedIndex();
        int year = yearCombo.getSelectedIndex();

        if (event.getSource().equals(previous)) {
            if (month != 0) {
                monthCombo.setSelectedIndex(--month);
            } else if (year != 0) {
                monthCombo.setSelectedIndex(monthCombo.getItemCount() - 1);
                yearCombo.setSelectedIndex(--year);
            }
        } else if (event.getSource().equals(next)) {
            if (month != monthCombo.getItemCount() - 1) {
                monthCombo.setSelectedIndex(++month);
            } else if (year != yearCombo.getItemCount() - 1) {
                monthCombo.setSelectedIndex(0);
                yearCombo.setSelectedIndex(++year);
            }
        }
    }
}