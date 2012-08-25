package com.itconsultores.colfrigos.control;

import java.util.ArrayList;

public class Movement {

	private String type;
	private ArrayList<MovementDetail> movementDetails;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<MovementDetail> getMovementDetails() {
		return movementDetails;
	}

	public void setMovementDetails(ArrayList<MovementDetail> movementDetails) {
		this.movementDetails = movementDetails;
	}
}
