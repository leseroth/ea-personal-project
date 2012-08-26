package com.itconsultores.colfrigos.dto;

import java.util.ArrayList;

import com.itconsultores.colfrigos.control.Constants.MovementType;

public class Movement {

	private MovementType movementType;
	private ArrayList<MovementDetail> movementDetails;

	public Movement(MovementType movementType,
			ArrayList<MovementDetail> movementDetails)
			throws IllegalArgumentException {
		if (movementType == null || movementDetails == null
				|| movementDetails.isEmpty()) {
			throw new IllegalArgumentException();
		}

		this.movementType = movementType;
		this.movementDetails = movementDetails;
	}

	public MovementType getMovementType() {
		return movementType;
	}

	public ArrayList<MovementDetail> getMovementDetails() {
		return movementDetails;
	}
}
