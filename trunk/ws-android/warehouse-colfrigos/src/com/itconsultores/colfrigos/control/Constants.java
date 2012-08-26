package com.itconsultores.colfrigos.control;

import com.itconsultores.colfrigos.android.R;

public class Constants {
	public static final String URL = "http://ea-personal-project.googlecode.com/svn/trunk/ws-android/movimiento.html?format=xml";

	protected static final String KEY_POSITIONS = "posiciones";
	protected static final String KEY_CAR = "carro";
	protected static final String KEY_CAR_NAME = "name";
	protected static final String KEY_POSITION = "posicion";
	protected static final String KEY_SIDE = "lado";
	protected static final String KEY_COORDINATE = "coordenada";
	protected static final String KEY_STATUS = "estado";

	protected static final String KEY_MOVEMENTS = "movimientos";
	protected static final String KEY_MOVEMENT = "movimiento";
	protected static final String KEY_ID = "id";
	protected static final String KEY_TYPE = "tipo";
	protected static final String KEY_LABEL = "etiqueta";
	protected static final String KEY_WEIGHT = "peso";
	protected static final String KEY_ROLLING = "balanceo";

	protected static final String KEY_IN = "entra";
	protected static final String KEY_OUT = "sale";

	public static final String LOG_DEBUG = "debug info";

	public enum MenuOption {
		Entrada(R.string.label_op_entrada, true, false), //
		Salida(R.string.label_op_salida, false, true), //
		EntradaSalida(R.string.label_op_entrada_salida), //
		Estiva(R.string.label_op_estiva); //

		private int textId;
		private boolean mostrarIngresar;
		private boolean mostrarSalida;

		private MenuOption(int text) {
			textId = text;
			mostrarIngresar = true;
			mostrarSalida = true;
		}

		private MenuOption(int text, boolean ing, boolean sal) {
			textId = text;
			mostrarIngresar = ing;
			mostrarSalida = sal;
		}

		public boolean isMostrarIngresar() {
			return mostrarIngresar;
		}

		public boolean isMostrarSalida() {
			return mostrarSalida;
		}

		public int getTextId() {
			return textId;
		}

	}

	public enum PositionStatus {
		NON_EXISTENT("I"), FULL("O"), EMPTY("V");

		private String code;

		private PositionStatus(String c) {
			code = c;
		}

		public String getCode() {
			return code;
		}
	}

	public enum MovementType {
		IN_OUT("salida-entrada"), IN("entrada"), OUT("salida");

		private String movementType;

		private MovementType(String c) {
			movementType = c;
		}

		public String getMovementType() {
			return movementType;
		}

		public static MovementType getType(String str) {
			MovementType type = null;
			movementTypeLoop: for (MovementType mt : MovementType.values()) {
				if (mt.getMovementType().equals(str)) {
					type = mt;
					break movementTypeLoop;
				}
			}
			return type;
		}
	}
}
