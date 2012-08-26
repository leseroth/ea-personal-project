package com.itconsultores.colfrigos.dto;

import com.itconsultores.colfrigos.control.Constants.MovementType;

public class MovementDetail {

	private int id;
	private int row;
	private int column;
	private int carNumber;
	private String carSide;
	private String label;
	private double weight;
	private String rolling;
	private MovementType movementType;

	public MovementDetail(String id, String car, String coordinate,
			String label, String weight, String rolling,
			MovementType movementType) throws IllegalArgumentException,
			NumberFormatException {

		if (id == null || car == null || coordinate == null || label == null
				|| weight == null || rolling == null || movementType == null) {
			throw new IllegalArgumentException();
		}

		this.id = Integer.parseInt(id);
		row = Integer.parseInt(coordinate.substring(0, 1));
		column = Integer.parseInt(coordinate.substring(1, coordinate.length()));
		this.carNumber = Integer.parseInt(car.substring(0, car.length() - 1));
		this.carSide = car.substring(car.length() - 1, car.length());
		this.label = label;
		this.weight = Double.parseDouble(weight);
		this.rolling = rolling;
		this.movementType = movementType;

		// Solo puede ser carro A o B
		if (!"A".equals(carSide) && !"B".equals(carSide)) {
			throw new IllegalArgumentException();
		}
	}

	public int getId() {
		return id;
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

	public String getRolling() {
		return rolling;
	}

	public MovementType getMovementType() {
		return movementType;
	}
}