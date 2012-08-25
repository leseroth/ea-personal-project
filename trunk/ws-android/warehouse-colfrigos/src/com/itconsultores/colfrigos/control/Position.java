package com.itconsultores.colfrigos.control;

public class Position {

	enum PositionStatus {
		NON_EXISTENT("I"), FULL("O"), EMPTY("V");

		private String code;

		private PositionStatus(String c) {
			code = c;
		}

		public String getCode() {
			return code;
		}
	}

	private int row;
	private int column;
	private PositionStatus positionStatus;

	public Position(String coordinate, String status)
			throws NumberFormatException, IllegalArgumentException {
		if (coordinate == null || status == null) {
			throw new IllegalArgumentException();
		}

		row = Integer.parseInt(coordinate.substring(0, 1));
		column = Integer.parseInt(coordinate.substring(1, coordinate.length()));

		statusLoop: for (PositionStatus ps : PositionStatus.values()) {
			if (ps.getCode().equals(status)) {
				positionStatus = ps;
				break statusLoop;
			}
		}

		if (positionStatus == null) {
			throw new IllegalArgumentException();
		}
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public PositionStatus getPositionStatus() {
		return positionStatus;
	}
}
