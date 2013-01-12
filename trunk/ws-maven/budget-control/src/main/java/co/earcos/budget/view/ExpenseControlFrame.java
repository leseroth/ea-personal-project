package co.earcos.budget.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import co.earcos.budget.control.MonthData;
import co.earcos.budget.util.Constants;
import co.earcos.util.SwingUtil;

/**
 * 
 * @author Erik
 */
public class ExpenseControlFrame extends JFrame implements ActionListener, ChangeListener {

    private static final long serialVersionUID = 3913298396116054954L;
    // Menu
    private JMenu menu;
    private JMenuBar menuBar;
    private JMenuItem exitItem;
    // Frame
    public static ExpenseControlFrame controlFrame;
    // Panels
    private DateSelectionPanel dateSelectionPanel;
    private CalendarPanel calendarPanel;
    private MonthResumePanel monthResumePanel;
    private CompleteResumePanel completeResumePanel;
    private StackedAccountChartPanel stackedAccountChart;
    // Data
    private MonthData monthData;

    private ExpenseControlFrame(String title) {
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

        Box calendarTabBox = Box.createHorizontalBox();
        calendarTabBox.add(calendarPanel);
        calendarTabBox.add(SwingUtil.getBoxFiller(Constants.GAP));
        calendarTabBox.add(SwingUtil.getBoxFiller(Constants.GAP));
        calendarTabBox.add(monthResumePanel);
        JPanel calendarTabPanel = new JPanel();
        calendarTabPanel.add(calendarTabBox);

        JTabbedPane mainTabbedPane = new JTabbedPane();
        mainTabbedPane.addTab("Estado Mes", calendarTabPanel);
        mainTabbedPane.addTab("Activos", stackedAccountChart);
        mainTabbedPane.addTab("Resumen", completeResumePanel);
        mainTabbedPane.addChangeListener(this);

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
        SwingUtil.centerFrame(frame);
        frame.resetCalendarPanel();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(exitItem)) {
            System.exit(0);
        }
    }

    public final void resetCalendarPanel() {
        if (calendarPanel.isShowing()) {
            int[] date = dateSelectionPanel.getSelectedDate();
            monthData = new MonthData(date[0], date[1]);
            calendarPanel.initCalendarPanel(monthData);
            calendarPanel.setSize(calendarPanel.getPreferredSize());
            calendarPanel.validate();
        }

        if (monthResumePanel.isShowing()) {
            monthResumePanel.restartMonthResumePanel(monthData);
        }

        if (stackedAccountChart.isShowing()) {
            stackedAccountChart.restartStackedAccountChartPanel();
        }

        if (completeResumePanel.isShowing()) {
            completeResumePanel.resetCompleteResumePanel();
        }

        pack();
        SwingUtil.centerFrame(this);
    }

    public DateSelectionPanel getDateSelectionPanel() {
        return dateSelectionPanel;
    }

    public MonthData getMonthData() {
        return monthData;
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        resetCalendarPanel();
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