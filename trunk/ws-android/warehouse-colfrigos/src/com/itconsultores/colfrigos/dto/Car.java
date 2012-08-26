package com.itconsultores.colfrigos.dto;

import java.util.List;

public class Car {

	private int number;
	private List<Position> sideA;
	private List<Position> sideB;

	public Car(String number, List<Position> sideA, List<Position> sideB)
			throws NumberFormatException {
		this.number = Integer.parseInt(number);
		this.sideA = sideA;
		this.sideB = sideB;
	}

	public int getNumber() {
		return number;
	}

	public List<Position> getSideA() {
		return sideA;
	}

	public List<Position> getSideB() {
		return sideB;
	}
}
