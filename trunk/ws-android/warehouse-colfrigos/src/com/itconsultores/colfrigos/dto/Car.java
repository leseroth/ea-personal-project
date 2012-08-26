package com.itconsultores.colfrigos.dto;

import java.io.Serializable;
import java.util.List;

public class Car implements Serializable, Comparable<Car> {

	private static final long serialVersionUID = 1226259901086750247L;

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

	@Override
	public boolean equals(Object other) {
		return other == null ? false
				: other instanceof Car ? number == ((Car) other).number : false;
	}

	@Override
	public int compareTo(Car other) {
		return other == null ? -1 : number - other.number;
	}
}
