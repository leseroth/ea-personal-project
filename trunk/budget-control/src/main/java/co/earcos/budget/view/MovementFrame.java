package co.earcos.budget.view;

import co.earcos.budget.control.DayData;
import co.earcos.budget.control.FieldValidator;
import co.earcos.budget.dao.DBConnection;
import co.earcos.budget.dao.MovementDao;
import co.earcos.budget.model.MovementVO;
import co.earcos.budget.util.Constants;
import co.earcos.budget.util.Constants.Account;
import co.earcos.budget.util.Constants.Concept;
import co.earcos.budget.util.Util;
import co.earcos.budget.view.table.CurrencyCellRenderer;
import co.earcos.budget.view.table.MovementTableDataModel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class MovementFrame extends JFrame implements ActionListener {

    //buttons
    private JButton addMovement;
    private JButton removeMovement;
    private JButton saveButton;
    private JTable table;
    private boolean saved;
    //Atributos
    DayResumePanel dayResumePanel;
    private DayPanel dayPanel;
    private DayData dayData;
    private MovementTableDataModel tableModel;
    //Fields
    private JCheckBox adjustCheck;
    private JComboBox accountCombo;
    private JFormattedTextField valueField;
    private JComboBox conceptCombo;
    private JTextField observationField;

    public MovementFrame(DayData day, DayPanel main) {
        super("Movimientos del " + day.getDate().get(Calendar.DATE));
        dayPanel = main;
        dayData = day;

        JScrollPane movementListPanel = initMovementListPanel();
        JPanel movementPanel = initMovementPanel();
        JPanel controlPanel = initControlPanel();

        dayResumePanel = new DayResumePanel();
        dayResumePanel.initDayResumePanel(ExpenseControlFrame.controlFrame.getMonthData(), dayData);

        Box mainBox = Box.createHorizontalBox();
        Box box = Box.createVerticalBox();
        box.add(movementListPanel);
        box.add(Util.getBoxFiller());
        box.add(movementPanel);
        box.add(Util.getBoxFiller());
        box.add(controlPanel);
        box.add(Util.getBoxFiller());
        box.setAlignmentY(BOTTOM_ALIGNMENT);
        mainBox.add(box);
        mainBox.add(dayResumePanel);

        JPanel mainPanel = new JPanel();
        mainPanel.add(mainBox);
        setContentPane(mainPanel);

        saved = false;

        addWindowListener(new MovementWindowAdapter(dayPanel));
        Util.centerFrame(this);
    }

    private JScrollPane initMovementListPanel() {
        JPanel movementPanel = new JPanel();
        movementPanel.setOpaque(true);

        tableModel = new MovementTableDataModel(false, dayData.getMovementList());

        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 180));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);

        CurrencyCellRenderer mtcr = new CurrencyCellRenderer();
        mtcr.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumn("Valor").setCellRenderer(mtcr);

        JScrollPane scrollPanel = new JScrollPane(table);

        GridLayout grid = new GridLayout(0, 1);
        setLayout(grid);
        return scrollPanel;
    }

    private JPanel initMovementPanel() {
        JPanel movementPanel = new JPanel();
        movementPanel.setLayout(new SpringLayout());

        adjustCheck = new JCheckBox();
        adjustCheck.setSelected(false);

        accountCombo = new JComboBox();
        for (Account account : Account.values()) {
            accountCombo.addItem(account.getLabel());
        }

        NumberFormat valueDisplayFormat = NumberFormat.getCurrencyInstance();
        valueDisplayFormat.setMinimumFractionDigits(0);
        valueDisplayFormat.setMaximumFractionDigits(2);

        valueField = new JFormattedTextField(new DefaultFormatterFactory(
                new NumberFormatter(valueDisplayFormat),
                new NumberFormatter(valueDisplayFormat),
                new NumberFormatter(NumberFormat.getNumberInstance())));

        conceptCombo = new JComboBox();
        for (Concept concept : Concept.values()) {
            conceptCombo.addItem(concept.getLabel());
        }

        observationField = new JTextField(25);
        observationField.setDocument(new FieldValidator(100, FieldValidator.VALIDATE_TEXT));

        movementPanel.add(new JLabel("Ajuste?", JLabel.TRAILING));
        movementPanel.add(adjustCheck);
        movementPanel.add(new JLabel("Cuenta", JLabel.TRAILING));
        movementPanel.add(accountCombo);
        movementPanel.add(new JLabel("Valor", JLabel.TRAILING));
        movementPanel.add(valueField);
        movementPanel.add(new JLabel("Concepto", JLabel.TRAILING));
        movementPanel.add(conceptCombo);
        movementPanel.add(new JLabel("Observacion", JLabel.TRAILING));
        movementPanel.add(observationField);

        Util.makeCompactGrid(movementPanel, 5, 2, 6, 6, 6, 6);
        movementPanel.setAlignmentX(CENTER_ALIGNMENT);
        movementPanel.setMaximumSize(movementPanel.getPreferredSize());

        return movementPanel;
    }

    private JPanel initControlPanel() {
        JPanel panel = new JPanel();

        addMovement = new JButton("Añadir");
        removeMovement = new JButton("Eliminar");
        saveButton = new JButton("Guardar");
        addMovement.addActionListener(this);
        removeMovement.addActionListener(this);
        saveButton.addActionListener(this);

        GridLayout gridLayout = new GridLayout(1, 2);
        gridLayout.setHgap(Constants.GAP);
        gridLayout.setVgap(Constants.GAP);

        panel.setLayout(gridLayout);
        panel.add(addMovement);
        panel.add(removeMovement);
        panel.add(saveButton);
        panel.setAlignmentX(CENTER_ALIGNMENT);

        return panel;
    }

    public boolean isValidForm() {
        return !valueField.getText().isEmpty();
    }

  @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(addMovement)) {
            actionAddMovement();
        } else if (event.getSource().equals(removeMovement)) {
            actionRemoveMovement();
        } else if (event.getSource().equals(saveButton)) {
            actionButtonSave();
        }
    }

    private void actionAddMovement() {
        if (isValidForm()) {
            MovementVO movementVO = new MovementVO();
            movementVO.setDate(dayData.getDate().getTime());
            movementVO.setAccount(Account.values()[accountCombo.getSelectedIndex()]);
            movementVO.setConcept((String) conceptCombo.getSelectedItem());
            movementVO.setObservation(observationField.getText());

            double value = ((Number) valueField.getValue()).doubleValue();
            movementVO.setValue(adjustCheck.isSelected()
                    ? value - dayResumePanel.getDayResumeData().getAccountTotal(movementVO.getAccount()) : value);

            dayData.getAddedMovementList().add(movementVO);
            tableModel.addMovement(movementVO);

            adjustCheck.setSelected(false);
            accountCombo.setSelectedIndex(0);
            valueField.setValue(null);
            valueField.validate();
            conceptCombo.setSelectedIndex(0);
            observationField.setText(null);

            dayResumePanel.initDayResumePanel(ExpenseControlFrame.controlFrame.getMonthData(), dayData);
            dayResumePanel.validate();
        } else {
            JOptionPane.showMessageDialog(this, "Falta el valor", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actionRemoveMovement() {
        if (table.getSelectedRows().length != 0) {
            for (int c : table.getSelectedRows()) {
                int index = table.convertRowIndexToModel(c);
                dayData.getRemovedMovementList().add(dayData.getMovementList().get(index));
                tableModel.removeMovement(index);
            }
            dayResumePanel.initDayResumePanel(ExpenseControlFrame.controlFrame.getMonthData(), dayData);
            dayResumePanel.validate();
        }
    }

    private void actionButtonSave() {
        Connection conn = DBConnection.getConnection();
        MovementDao movementDao = new MovementDao();
        try {
            boolean modelModified = false;

            for (MovementVO movementVO : dayData.getRemovedMovementList()) {
                if (movementVO.getId() != null) {
                    movementDao.delete(conn, movementVO);
                    modelModified = true;
                }
            }

            dayData.getAddedMovementList().removeAll(dayData.getRemovedMovementList());
            for (MovementVO movementVO : dayData.getAddedMovementList()) {
                if (movementVO.getId() == null) {
                    movementDao.create(conn, movementVO);
                    modelModified = true;
                }
            }

            if (modelModified) {
                ExpenseControlFrame.controlFrame.resetCalendarPanel();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        DBConnection.closeConnection();
        saved = true;
        dayPanel.closeMovementFrame();
    }

    public void validateMovements() {
        if (!saved) {
            dayData.getMovementList().addAll(dayData.getRemovedMovementList());
            dayData.getMovementList().removeAll(dayData.getAddedMovementList());
        }
        dayData.getAddedMovementList().clear();
        dayData.getRemovedMovementList().clear();
    }

    class MovementWindowAdapter extends WindowAdapter {

        private DayPanel adapterDayPanel;

        public MovementWindowAdapter(DayPanel dp) {
            adapterDayPanel = dp;
        }

        @Override
        public void windowClosing(WindowEvent event) {
            adapterDayPanel.closeMovementFrame();
        }
    }
}
