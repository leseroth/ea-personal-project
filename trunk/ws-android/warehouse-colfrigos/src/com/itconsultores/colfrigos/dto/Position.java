package com.itconsultores.colfrigos.dto;

public class Position {

	private int row;
	private int column;
	private boolean full;
	private transient String side;

	public Position(String coordinate, String status)
			throws NumberFormatException, IllegalArgumentException {
		if (coordinate == null || status == null) {
			throw new IllegalArgumentException();
		}

		row = Integer.parseInt(coordinate.substring(0, 1));
		side = coordinate.substring(1, 2);
		column = Integer.parseInt(coordinate.substring(2, coordinate.length()));

		if ("S".equals(status) || "N".equals(status)) {
			full = "S".equals(status);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean isFull() {
		return full;
	}

	public String getSide() {
		return side;
	}
}