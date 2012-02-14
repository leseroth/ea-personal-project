package co.earcos.budget.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.Spring;
import javax.swing.SpringLayout;

public class Util {

    public static final int GAP = 4;

    public enum Account {

        CASH("ef", "Efectivo"), SAVING("ah", "Ahorro"),
        UNIACCION("un", "Uniaccion"), FIDUCUENTA("fi", "Fiducuenta"), INDEACCION("in", "Indeaccion"),
        AMEX("cp", "AMEX Pesos"), AMEXUSD("cd", "AMEX Dolares"), CMR("cm", "CMR");
        private String id;
        private String label;

        Account(String ids, String labels) {
            id = ids;
            label = labels;
        }

        public String getId() {
            return id;
        }

        public String getLabel() {
            return label;
        }

        public boolean isCreditCard() {
            return this == AMEX || this == AMEXUSD || this == CMR;
        }
    }

    public enum Concept {

        FOOD("Comida"), TRANSPORT("Transporte"), OTHERS("Otros"), INTERESTS("Intereses"),
        LOAN_INTEREST("Intereses Prestamo"), LOAN("Prestamo"), MOVEMENT("Movimiento"), SALARY("Sueldo"),
        SERVICES("Servicios"), RENT("Arriendo"), PAYMENT("Pago"), CELLPHONE("Celular"),
        CLOTHES("Ropa"), MOVIES("Cine"), UNIVERSITY("Universidad"), TRAVEL("Viaje"),
        IVA_DEVOLUTION("Devolucion de iva"), GROUPON("Groupon"), XD_APPS("XD Apps");
        private String label;

        Concept(String labels) {
            label = labels;
        }

        public String getLabel() {
            return label;
        }
    }

    public static void centerFrame(JFrame frame) {
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getPreferredSize();
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2 + 15);
    }

    public static Box.Filler getBoxFiller() {
        Dimension filler = new Dimension(GAP, GAP);
        Box.Filler boxFiller = new Box.Filler(filler, filler, filler);
        return boxFiller;
    }

    public static String getCurrencyValue(double value) {
        return NumberFormat.getCurrencyInstance().format(value);
    }

    public static String getFormattedDate(Date date) {
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
    }

    public static Account getAccountById(String id) {
        Account account = null;
        for (Account acc : Account.values()) {
            if (acc.getId().equals(id)) {
                account = acc;
                break;
            }
        }
        return account;
    }

    public static java.sql.Date getSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static Date getDate(java.sql.Date date) {
        return new Date(date.getTime());
    }

    public static void makeCompactGrid(Container parent, int rows, int cols, int initialX, int initialY, int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout) parent.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("The first argument to makeCompactGrid must use SpringLayout." + exc.getMessage());
            return;
        }

        Spring x = Spring.constant(initialX);
        for (int c = 0; c < cols; c++) {
            Spring width = Spring.constant(0);
            for (int r = 0; r < rows; r++) {
                width = Spring.max(width, getConstraintsForCell(r, c, parent, cols).getWidth());
            }
            for (int r = 0; r < rows; r++) {
                SpringLayout.Constraints constraints = getConstraintsForCell(r, c, parent, cols);
                constraints.setX(x);
                constraints.setWidth(width);
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));
        }

        Spring y = Spring.constant(initialY);
        for (int r = 0; r < rows; r++) {
            Spring height = Spring.constant(0);
            for (int c = 0; c < cols; c++) {
                height = Spring.max(height, getConstraintsForCell(r, c, parent, cols).getHeight());
            }
            for (int c = 0; c < cols; c++) {
                SpringLayout.Constraints constraints = getConstraintsForCell(r, c, parent, cols);
                constraints.setY(y);
                constraints.setHeight(height);
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));
        }

        SpringLayout.Constraints pCons = layout.getConstraints(parent);
        pCons.setConstraint(SpringLayout.SOUTH, y);
        pCons.setConstraint(SpringLayout.EAST, x);
    }

    private static SpringLayout.Constraints getConstraintsForCell(int row, int col, Container parent, int cols) {
        SpringLayout layout = (SpringLayout) parent.getLayout();
        Component c = parent.getComponent(row * cols + col);
        return layout.getConstraints(c);
    }
}
