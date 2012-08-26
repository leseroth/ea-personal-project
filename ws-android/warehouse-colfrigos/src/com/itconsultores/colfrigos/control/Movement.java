package com.itconsultores.colfrigos.control;

import java.util.ArrayList;

public class Movement {

	enum MovementType {
		IN_OUT("salida-entrada"), IN("entrada"), OUT("salida");

		private String movementType;

		private MovementType(String c) {
			movementType = c;
		}

		public String getMovementType() {
			return movementType;
		}
	}
	
	private MovementType movementType;
	private ArrayList<MovementDetail> movementDetails;

	
	public Movement(String movementType, ArrayList<MovementDetail> movementDetails)throws IllegalArgumentException {
		if (movementType == null || movementDetails == null || movementDetails.isEmpty()) {
			throw new IllegalArgumentException();
		}				
		movementTypeLoop: for (MovementType mt : MovementType.values()) {
			if (mt.getMovementType().equals(movementType)) {
				this.movementType = mt;
				break movementTypeLoop;
			}
		}

		if (this.movementType == null) {
			throw new IllegalArgumentException();
		}
		
		this.movementDetails = movementDetails;

	}

	public MovementType getMovementType() {
		return movementType;
	}

	public void setMovementType(MovementType movementType) {
		this.movementType = movementType;
	}

	public ArrayList<MovementDetail> getMovementDetails() {
		return movementDetails;
	}

	public void setMovementDetails(ArrayList<MovementDetail> movementDetails) {
		this.movementDetails = movementDetails;
	}
}
