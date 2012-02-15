package co.earcos.budget.view;

import co.earcos.budget.control.MonthData;
import co.earcos.budget.util.Util;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Erik
 */
public class ExpenseControlFrame extends JFrame implements ActionListener {

    // Menu
    private JMenu menu;
    private JMenuBar menuBar;
    private JMenuItem exitItem;
    //Frame
    public static ExpenseControlFrame controlFrame;
    //Panels
    private DateSelectionPanel dateSelectionPanel;
    private CalendarPanel calendarPanel;
    private MonthResumePanel monthResumePanel;
    private CompleteResumePanel completeResumePanel;
    private StackedAccountChartPanel stackedAccountChart;
    //Data
    private MonthData monthData;

    public ExpenseControlFrame(String title) {
        super(title);
        controlFrame = this;

        menuBar = new JMenuBar();
        menu = new JMenu();
        exitItem = new JMenuItem();
        menu.setText("Opciones");
        exitItem.setText("Salir");
        exitItem.addActionListener(this);
        menu.add(exitItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        calendarPanel = new CalendarPanel();
        dateSelectionPanel = new DateSelectionPanel();
        monthResumePanel = new MonthResumePanel();
        completeResumePanel = new CompleteResumePanel();
        stackedAccountChart = new StackedAccountChartPanel();
        resetCalendarPanel();

        Box calendarTabBox = Box.createHorizontalBox();
        calendarTabBox.add(calendarPanel);
        calendarTabBox.add(Util.getBoxFiller());
        calendarTabBox.add(Util.getBoxFiller());
        calendarTabBox.add(monthResumePanel);
        JPanel calendarTabPanel = new JPanel();
        calendarTabPanel.add(calendarTabBox);

        JTabbedPane mainTabbedPane = new JTabbedPane();
        mainTabbedPane.addTab("Estado Mes", calendarTabPanel);
        mainTabbedPane.addTab("Activos", stackedAccountChart);
        mainTabbedPane.addTab("Resumen", completeResumePanel);

        Box mainBox = Box.createVerticalBox();
        mainBox.add(dateSelectionPanel);
        mainBox.add(mainTabbedPane);
        JPanel mainPanel = new JPanel();
        mainPanel.add(mainBox);

        setContentPane(mainPanel);
    }

    public static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        ExpenseControlFrame frame = new ExpenseControlFrame("Control de Gastos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Util.centerFrame(frame);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(exitItem)) {
            System.exit(0);
        }
    }

    public final void resetCalendarPanel() {
        int[] date = dateSelectionPanel.getSelectedDate();
        monthData = new MonthData(date[0], date[1]);
        calendarPanel.initCalendarPanel(monthData);
        calendarPanel.setSize(calendarPanel.getPreferredSize());
        calendarPanel.validate();

        monthResumePanel.restartMonthResumePanel(monthData);

        stackedAccountChart.restartStackedAccountChartPanel();

        completeResumePanel.resetCompleteResumePanel();

        pack();
        Util.centerFrame(this);

        String advice = monthData.getMovementAdvice();
        if (advice != null) {
            JOptionPane.showMessageDialog(this, advice, "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public DateSelectionPanel getDateSelectionPanel() {
        return dateSelectionPanel;
    }

    public MonthData getMonthData() {
        return monthData;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
