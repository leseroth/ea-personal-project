package co.earcos.budget.view.table;

import co.earcos.budget.model.MovementVO;
import co.earcos.budget.util.Util;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Erik
 */
public class MovementTableDataModel extends AbstractTableModel {

  private String[] columnNames;
  private List<MovementVO> movementList;
  private boolean complete;

  public MovementTableDataModel(boolean complete, List<MovementVO> movementVOList) {
    this.complete = complete;

    if (this.complete) {
      columnNames = new String[]{"Fecha", "Cuenta", "Valor", "Concepto", "Observacion"};
    } else {
      columnNames = new String[]{"Cuenta", "Valor", "Concepto", "Observacion"};
    }

    movementList = movementVOList;
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public int getRowCount() {
    return movementList.size();
  }

  @Override
  public String getColumnName(int col) {
    return columnNames[col];
  }

  @Override
  public Object getValueAt(int row, int col) {
    String value = null;
    MovementVO movementData = movementList.get(row);

    if (complete) {
      col--;
      value = Util.getFormattedDate(movementData.getDate());
    }

    switch (col) {
      case 0:
        value = movementData.getAccount().getLabel();
        break;
      case 1:
        value = Util.getCurrencyValue(movementData.getAccount(), movementData.getValue());
        break;
      case 2:
        value = movementData.getConcept();
        break;
      case 3:
        value = movementData.getObservation();
        break;
    }
    return value;
  }

  public void addMovement(MovementVO movementData) {
    movementList.add(movementData);
    fireTableDataChanged();
  }

  public void removeMovement(int index) {
    movementList.remove(index);
    fireTableDataChanged();
  }

  public void clearMovementList() {
    movementList.clear();
    fireTableDataChanged();
  }
}
