package com.earcos.budget.view.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Erik
 */
public class CurrencyCellRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);

        if (obj.toString().indexOf("(") != -1) {
            cell.setForeground(Color.red);
        } else {
            cell.setForeground(Color.black);
        }

        return cell;
    }
}
