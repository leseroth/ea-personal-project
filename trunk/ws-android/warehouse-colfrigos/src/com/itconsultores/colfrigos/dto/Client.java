package com.itconsultores.colfrigos.dto;

public class Client {
	private int id;
	private String name;

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
