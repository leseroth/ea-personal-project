package com.itconsultores.colfrigos.dto;

/**
 * Información de un cliente
 * 
 * @author Erik
 * 
 */
public class Client {
	/**
	 * Identificador del cliente
	 */
	private int id;
	/**
	 * Nombre del cliente
	 */
	private String name;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            Id del cliente
	 * @param name
	 *            Nombre del cliente
	 * @throws IllegalArgumentException
	 * @throws NumberFormatException
	 */
	public Client(String id, String name) throws IllegalArgumentException,
			NumberFormatException {
		if (id == null || name == null) {
			throw new IllegalArgumentException();
		}

		this.id = Integer.parseInt(id);
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}