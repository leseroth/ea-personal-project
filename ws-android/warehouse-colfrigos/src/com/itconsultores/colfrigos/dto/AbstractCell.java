package com.itconsultores.colfrigos.dto;

public abstract class AbstractCell {

	protected int row;
	protected int column;

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	abstract public int getColor();
}
