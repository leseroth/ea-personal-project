package com.itconsultores.colfrigos.dto;

import java.io.Serializable;
import java.util.TreeSet;

public class Car implements Serializable, Comparable<Car> {

	private static final long serialVersionUID = 1226259901086750247L;

	private int number;
	private TreeSet<Position> sideA;
	private TreeSet<Position> sideB;

	public Car(String number, TreeSet<Position> sideA, TreeSet<Position> sideB)
			throws NumberFormatException {
		this.number = Integer.parseInt(number);
		this.sideA = sideA;
		this.sideB = sideB;
	}

	public int getNumber() {
		return number;
	}

	public TreeSet<Position> getSideA() {
		return sideA;
	}

	public TreeSet<Position> getSideB() {
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
