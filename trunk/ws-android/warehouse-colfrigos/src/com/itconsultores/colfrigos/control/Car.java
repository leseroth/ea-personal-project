package com.itconsultores.colfrigos.control;

import java.util.List;

public class Car {

	private String name;
	private List<Position> sideA;
	private List<Position> sideB;

	public Car(String name, List<Position> sideA, List<Position> sideB) {
		this.name = name;
		this.sideA = sideA;
		this.sideB = sideB;
	}

	public String getName() {
		return name;
	}

	public List<Position> getSideA() {
		return sideA;
	}

	public List<Position> getSideB() {
		return sideB;
	}
}
