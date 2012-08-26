package com.itconsultores.colfrigos.dto;

import com.itconsultores.colfrigos.control.Constants.MovementType;

public class MovementDetail {

	private int row;
	private int column;
	private int carNumber;
	private String carSide;
	private String label;
	private double weight;
	private MovementType movementType;

	public MovementDetail(String car, String coordinate, String label,
			String weight, MovementType movementType)
			throws IllegalArgumentException, NumberFormatException {

		if (car == null || coordinate == null || label == null
				|| weight == null || movementType == null) {
			throw new IllegalArgumentException();
		}

		row = Integer.parseInt(coordinate.substring(0, 1));
		carSide = coordinate.substring(1, 2);
		column = Integer.parseInt(coordinate.substring(2, coordinate.length()));
		carNumber = Integer.parseInt(car);
		this.label = label;
		this.weight = Double.parseDouble(weight);
		this.movementType = movementType;

		// Solo puede ser carro A o B
		if (!"A".equals(carSide) && !"B".equals(carSide)) {
			throw new IllegalArgumentException();
		}
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int getCarNumber() {
		return carNumber;
	}

	public String getCarSide() {
		return carSide;
	}

	public String getLabel() {
		return label;
	}

	public double getWeight() {
		return weight;
	}

	public MovementType getMovementType() {
		return movementType;
	}
}