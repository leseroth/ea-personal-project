package com.itconsultores.colfrigos.dto;

import com.itconsultores.colfrigos.control.Constants.MovementType;

/**
 * Indica el detalle de un movimiento, esta clase existe dado que eventualmente
 * puede ser IN_OUT, lo cual se separa en un detalle de entrada y uno de salida
 * 
 * @author Erik
 * 
 */
public class MovementDetail extends AbstractCell {

	/**
	 * Indica el numero del carro
	 */
	private int carNumber;
	/**
	 * Identifica la etiqueta de la posicion a mover
	 */
	private String label;
	/**
	 * Identifica el peso en esa posicion
	 */
	private double weight;
	/**
	 * Indica el tipo de movimiento, puede ser {@link MovementType#IN} o
	 * {@link MovementType#OUT}, si el movimiento era
	 * {@link MovementType#IN_OUT}, se separa en dos detalles, ver la clase
	 * {@link Movement}
	 */
	private MovementType movementType;

	/**
	 * Constructor
	 * 
	 * @param car
	 *            Número del carro
	 * @param coordinate
	 *            Coordenada en formato RSC = RowSideColumn
	 * @param label
	 *            Etiqueta de la posicion
	 * @param weight
	 *            Peso en kilos del movimiento
	 * @param movementType
	 *            Tipo de movimiento, puede ser {@link MovementType#IN} o
	 *            {@link MovementType#OUT},
	 * @throws IllegalArgumentException
	 * @throws NumberFormatException
	 */
	public MovementDetail(String car, String coordinate, String label,
			String weight, MovementType movementType)
			throws IllegalArgumentException, NumberFormatException {
		super(coordinate);

		if (car == null || label == null || weight == null
				|| movementType == null) {
			throw new IllegalArgumentException();
		}

		carNumber = Integer.parseInt(car);
		this.label = label;
		this.weight = Double.parseDouble(weight);
		this.movementType = movementType;

		// Solo puede ser carro A o B
		if (!"A".equals(carSide) && !"B".equals(carSide)) {
			throw new IllegalArgumentException();
		}
	}

	public int getCarNumber() {
		return carNumber;
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

	/**
	 * El color depende del tipo de movimiento, estos se guardan en los archivos
	 * de propiedades, ver {@link MovementType}{@link #getColor()}
	 */
	@Override
	public int getColor() {
		return movementType.getStatusColor().getColor();
	}
}