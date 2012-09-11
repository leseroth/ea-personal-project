package com.itconsultores.colfrigos.dto;

import java.util.List;

import com.itconsultores.colfrigos.control.Constants.MovementType;

/**
 * Modela un movimiento
 * 
 * @author Erik
 * 
 */
public class Movement {

	/**
	 * Identificador del movimiento
	 */
	private int id;
	/**
	 * Tipo del movimiento
	 */
	private MovementType movementType;
	/**
	 * Detalle del movimiento, si es {@link MovementType#IN} o
	 * {@link MovementType#OUT} solo hay un detalle, si es
	 * {@link MovementType#IN_OUT} hay dos detalles, el de entrada y el de
	 * salida
	 */
	private List<MovementDetail> movementDetails;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Identificador del movimiento
	 * @param movementType
	 *            Tipo de movimiento
	 * @param movementDetails
	 *            Listado de detalles
	 * @throws NumberFormatException
	 * @throws IllegalArgumentException
	 */
	public Movement(String id, MovementType movementType,
			List<MovementDetail> movementDetails) throws NumberFormatException,
			IllegalArgumentException {
		if (id == null || movementType == null || movementDetails == null
				|| movementDetails.isEmpty()) {
			throw new IllegalArgumentException();
		}

		this.id = Integer.parseInt(id);
		this.movementType = movementType;
		this.movementDetails = movementDetails;
	}

	public int getId() {
		return id;
	}

	public MovementType getMovementType() {
		return movementType;
	}

	public List<MovementDetail> getMovementDetails() {
		return movementDetails;
	}
}