package com.itconsultores.colfrigos.dto;

import com.itconsultores.colfrigos.control.Constants.StatusColor;

/**
 * Representa la posición en una bodega
 * 
 * @author Erik
 * 
 */
public class Position extends AbstractCell {

	/**
	 * Constructor
	 * 
	 * @param coordinate
	 *            Coordenada en formato RSC = RowSideColumn
	 * @param status
	 *            El status puede ser S o N
	 * @throws NumberFormatException
	 * @throws IllegalArgumentException
	 */
	public Position(String coordinate, String status)
			throws NumberFormatException, IllegalArgumentException {
		super(coordinate);

		if (status == null) {
			throw new IllegalArgumentException();
		}

		/**
		 * El color solo puede ser ocupado o libre
		 */
		if ("S".equals(status) || "N".equals(status)) {
			boolean full = "S".equals(status);
			color = full ? StatusColor.FILLED.getColor() : StatusColor.FREE
					.getColor();
		} else {
			throw new IllegalArgumentException();
		}
	}
}