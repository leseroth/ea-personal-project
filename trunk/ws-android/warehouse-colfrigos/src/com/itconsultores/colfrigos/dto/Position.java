package com.itconsultores.colfrigos.dto;

import java.io.Serializable;

import com.itconsultores.colfrigos.control.Constants.PositionStatus;

public class Position implements Serializable, Comparable<Position> {

	private static final long serialVersionUID = -8370711078180239596L;

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

	private int getCoordinate() {
		return row * 100 + column;
	}

	@Override
	public boolean equals(Object other) {
		return other == null ? false : //
				other instanceof Position ? //
				getCoordinate() == ((Position) other).getCoordinate()
						: false;
	}

	@Override
	public int compareTo(Position other) {
		return other == null ? -1 : getCoordinate() - other.getCoordinate();
	}
}
