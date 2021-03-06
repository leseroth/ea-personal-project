package com.itconsultores.colfrigos.control;

import com.itconsultores.colfrigos.android.R;

public class Constants {

	/**
	 * Direcci�n de debug
	 */
	public static final String DEBUG_URL = "http://ea-personal-project.googlecode.com/svn/trunk/ws-android/movimiento.html?format=xml";

	// Direcciones y mensajes a llamar

	private static final String SERVER_URL = "http://192.168.43.17:8081/temperaturas/";
	public static final String LOGIN_URL = SERVER_URL
			+ "mobileLogin.seam?login=<login>&password=<password>";
	public static final String MOVEMENT_URL = SERVER_URL
			+ "movement.seam?login=<login>&pwd=<pwd>&type=<type>&weight=<weight>&"
			+ "clientId=<clientId>&tag=<tag>&balanceo=<balanceo>&car=<car>";
	public static final String CONFIRM_URL = SERVER_URL
			+ "confirmation.seam?login=<login>&pwd=<pwd>&movementId=<movementId>";
	public static final String BALANCE_CAR_URL = SERVER_URL
			+ "balance.seam?login=<login>&pwd=<pwd>&car=<car>";

	// Etiquetas del xml de respuesta

	protected static final String KEY_POSITIONS = "posiciones";
	protected static final String KEY_CAR = "carro";
	protected static final String KEY_CAR_NAME = "name";
	protected static final String KEY_POSITION = "p";
	protected static final String KEY_COORDINATE = "c";
	protected static final String KEY_STATUS = "o";

	protected static final String KEY_MOVEMENTS = "movimientos";
	protected static final String KEY_MOVEMENT = "movimiento";
	protected static final String KEY_ID = "id";
	protected static final String KEY_TYPE = "ti";
	protected static final String KEY_MOVEMENT_CAR = "ca";
	protected static final String KEY_MOVEMENT_COORDINATE = "co";
	protected static final String KEY_LABEL = "et";
	protected static final String KEY_WEIGHT = "pe";

	protected static final String KEY_IN = "entra";
	protected static final String KEY_OUT = "sale";

	protected static final String KEY_CLIENTS = "clientes";
	protected static final String KEY_CLIENT = "cli";
	protected static final String KEY_CLIENT_ID = "id";
	protected static final String KEY_CLIENT_NAME = "na";

	protected static final String KEY_ERROR = "error";

	public static final String LOG_DEBUG = "debug info";

	/**
	 * Opciones de menu
	 * 
	 * @author Erik
	 * 
	 */
	public enum MenuOption {
		Entrada(R.string.label_op_entrada), //
		Salida(R.string.label_op_salida), //
		EntradaSalida(R.string.label_op_entrada_salida), //
		Estiva(R.string.label_op_estiva); //

		private int textId;

		private MenuOption(int text) {
			textId = text;
		}

		public int getTextId() {
			return textId;
		}

	}

	/**
	 * Tipo de movimiento
	 * 
	 * @author Erik
	 * 
	 */
	public enum MovementType {
		IN_OUT("SE", MenuOption.EntradaSalida, StatusColor.INEXISTENT), //
		IN("E", MenuOption.Entrada, StatusColor.IN), //
		OUT("S", MenuOption.Salida, StatusColor.OUT);

		private String movementType;
		private MenuOption menuOption;
		private StatusColor statusColor;

		private MovementType(String c, MenuOption me, StatusColor sc) {
			movementType = c;
			menuOption = me;
			statusColor = sc;
		}

		public String getMovementType() {
			return movementType;
		}

		public MenuOption getMenuOption() {
			return menuOption;
		}

		public StatusColor getStatusColor() {
			return statusColor;
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

	/**
	 * Colores usados por la pantalla de bodega
	 * 
	 * @author Erik
	 * 
	 */
	public enum StatusColor {
		INEXISTENT(R.color.color_style_warehouse_cell), //
		FREE(R.color.color_style_warehouse_cell_free), //
		FILLED(R.color.color_style_warehouse_cell_filled), //
		IN(R.color.color_style_warehouse_cell_in), //
		OUT(R.color.color_style_warehouse_cell_out);

		private int color;

		private StatusColor(int colorResource) {
			color = colorResource;
		}

		public int getColor() {
			return color;
		}
	}
}