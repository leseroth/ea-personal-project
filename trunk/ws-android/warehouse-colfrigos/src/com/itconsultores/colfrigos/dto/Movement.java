package com.itconsultores.colfrigos.dto;

import java.io.Serializable;
import java.util.TreeSet;

import com.itconsultores.colfrigos.control.Constants.MovementType;

public class Movement implements Serializable, Comparable<Movement> {

	private static final long serialVersionUID = 1672409454486753561L;

	private MovementType movementType;
	private TreeSet<MovementDetail> movementDetails;

	public Movement(MovementType movementType,
			TreeSet<MovementDetail> movementDetails)
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

	public TreeSet<MovementDetail> getMovementDetails() {
		return movementDetails;
	}

	@Override
	public int compareTo(Movement other) {
		return other == null ? -1 : //
				getMovementDetails().first().getId()
						- other.getMovementDetails().first().getId();
	}
}
