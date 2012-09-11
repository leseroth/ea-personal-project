package com.itconsultores.colfrigos.dto;

import java.util.List;

/**
 * Carro dentro de la bodega
 * 
 * @author Erik
 * 
 */
public class Car {

	/**
	 * Numero del carro
	 */
	private int number;
	/**
	 * Posiciones del lado A
	 */
	private List<Position> sideA;
	/**
	 * Posiciones del lado B
	 */
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