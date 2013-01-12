package co.earcos.budget.view.table;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import co.earcos.budget.control.ResumeData;
import co.earcos.budget.util.Util;

/**
 * 
 * @author Erik
 */
public class ResumeTableDataModel extends AbstractTableModel {

	private static final long serialVersionUID = -6669488450395540575L;
	private String[] columnNames;
	private List<ResumeData> resumeDataList;

	public ResumeTableDataModel(List<ResumeData> resumeList,
			String[] columnTitles) {
		columnNames = columnTitles;
		resumeDataList = resumeList;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return resumeDataList.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {
		String value = null;
		ResumeData resumeData = resumeDataList.get(row);

		switch (columnNames.length - col - 1) {
		case 0:
			value = Util.getCurrencyValue(resumeData.getCurrentMonth());
			break;
		case 1:
			value = Util.getCurrencyValue(resumeData.getBalance());
			break;
		default:
			if (col == 0) {
				if (resumeData.getAccount() == null) {
					value = resumeData.getConcept();
				} else {
					value = resumeData.getAccount().getLabel();
				}
			} else if (col == 1) {
				if (resumeData.getObservation() == null) {
					value = resumeData.getConcept();
				} else {
					value = resumeData.getObservation();
				}
			}
			break;
		}

		return value;
	}

	public void addResumeDataList(List<ResumeData> resumeList) {
		resumeDataList.addAll(resumeList);
		fireTableDataChanged();
	}

	public void clearResumeDataList() {
		resumeDataList.clear();
		fireTableDataChanged();
	}
}
