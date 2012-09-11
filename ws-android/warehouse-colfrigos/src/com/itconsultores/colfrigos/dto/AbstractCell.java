package com.itconsultores.colfrigos.dto;

/**
 * Una celda en una bodega debe tener s
 * 
 * @author Erik
 * 
 */
public abstract class AbstractCell {

	/**
	 * Fila
	 */
	protected int row;
	/**
	 * Columna
	 */
	protected int column;
	/**
	 * Lado del carro, puede ser A o B
	 */
	protected String carSide;
	/**
	 * Color
	 */
	protected int color;

	/**
	 * Inicializa la fila, columna y lado del carro
	 * 
	 * @param coordinate
	 *            Coordenada en formato RSC = RowSideColumn
	 * @throws NumberFormatException
	 */
	public AbstractCell(String coordinate) throws NumberFormatException {
		if (coordinate == null) {
			throw new IllegalArgumentException();
		}

		row = Integer.parseInt(coordinate.substring(0, 1)) - 1;
		carSide = coordinate.substring(1, 2);
		column = Integer.parseInt(coordinate.substring(2, coordinate.length())) - 1;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public String getCarSide() {
		return carSide;
	}

	public int getColor() {
		return color;
	}
}