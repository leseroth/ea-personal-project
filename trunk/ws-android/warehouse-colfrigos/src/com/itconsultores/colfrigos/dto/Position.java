package com.itconsultores.colfrigos.dto;

import com.itconsultores.colfrigos.control.Constants.StatusColor;

public class Position extends AbstractCell {

	private boolean full;
	private transient String side;

	public Position(String coordinate, String status)
			throws NumberFormatException, IllegalArgumentException {
		if (coordinate == null || status == null) {
			throw new IllegalArgumentException();
		}

		row = Integer.parseInt(coordinate.substring(0, 1)) - 1;
		side = coordinate.substring(1, 2);
		column = Integer.parseInt(coordinate.substring(2, coordinate.length())) - 1;

		if ("S".equals(status) || "N".equals(status)) {
			full = "S".equals(status);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public boolean isFull() {
		return full;
	}

	public String getSide() {
		return side;
	}

	@Override
	public int getColor() {
		return full ? StatusColor.FILLED.getColor() : StatusColor.FREE
				.getColor();
	}
}