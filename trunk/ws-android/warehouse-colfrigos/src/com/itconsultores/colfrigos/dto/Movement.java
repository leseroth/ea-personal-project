package com.itconsultores.colfrigos.dto;

import java.util.List;

import com.itconsultores.colfrigos.control.Constants.MovementType;

public class Movement {

	private int id;
	private MovementType movementType;
	private List<MovementDetail> movementDetails;

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