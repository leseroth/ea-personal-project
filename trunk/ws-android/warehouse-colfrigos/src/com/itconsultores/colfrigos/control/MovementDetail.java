package com.itconsultores.colfrigos.control;


public class MovementDetail {

	enum MovementDetailType {
		IN("entrada"), OUT("salida");

		private String movementDetailType;

		private MovementDetailType(String c) {
			movementDetailType = c;
		}

		public String getMovementDetailType() {
			return movementDetailType;
		}
	}
	private String id;
	private String car;
	private String coordinate;
	private String label;
	private String weight;
	private String rolling;
	private MovementDetailType movementDetailType;

	public MovementDetail(String id, String car, String coordinate,
			String label, String weight, String rolling,
			String movementDetailType)throws IllegalArgumentException {
		if (id == null || car == null || coordinate==null || label == null 
				|| weight==null || rolling == null || movementDetailType==null) {
			throw new IllegalArgumentException();
		}				
		movementDetailTypeLoop: for (MovementDetailType md : MovementDetailType.values()) {
			if (md.getMovementDetailType().equals(movementDetailType)) {
				this.movementDetailType = md;
				break movementDetailTypeLoop;
			}
		}
		if (this.movementDetailType == null) {
			throw new IllegalArgumentException();
		}
		
		this.id = id;
		this.car = car;
		this.coordinate = coordinate;
		this.label = label;
		this.weight = weight;
		this.rolling = rolling;
	}

	public MovementDetailType getMovementDetailType() {
		return movementDetailType;
	}

	public void setMovementDetailType(MovementDetailType movementDetailType) {
		this.movementDetailType = movementDetailType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getRolling() {
		return rolling;
	}

	public void setRolling(String rolling) {
		this.rolling = rolling;
	}

}
