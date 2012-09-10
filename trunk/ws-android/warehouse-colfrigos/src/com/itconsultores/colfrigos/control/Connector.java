package com.itconsultores.colfrigos.control;

import static com.itconsultores.colfrigos.control.Constants.KEY_ERROR;
import static com.itconsultores.colfrigos.control.Constants.LOG_DEBUG;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.util.Log;

import com.itconsultores.colfrigos.android.AbstractForm;
import com.itconsultores.colfrigos.android.MenuActivity;
import com.itconsultores.colfrigos.android.MenuFreeMovementActivity;
import com.itconsultores.colfrigos.android.R;
import com.itconsultores.colfrigos.android.WarehouseActivity;
import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Constants.MovementType;
import com.itconsultores.colfrigos.dto.Movement;

public class Connector {
	private static String user;
	private static String pass;

	private static final boolean debug = false;

	public static Class<? extends Activity> doRevalidate() {
		Control.message = null;
		String result = doLogin(user, pass);
		Class<? extends Activity> menu = Control.freeMovementMenu ? MenuFreeMovementActivity.class
				: MenuActivity.class;

		Class<? extends Activity> nextActivity = null;
		if ("".equals(result)) {
			MenuOption menuOption = Control.getNextMovementMenu();

			if (menuOption == null) {
				nextActivity = menu;
				Control.message = "No hay movimientos pendientes";
			} else {
				Control.setSelectedOption(menuOption);
				nextActivity = WarehouseActivity.class;
			}
		} else {
			Control.message = result;
			nextActivity = menu;
		}

		return nextActivity;
	}

	public static String doLogin(String username, String password) {
		String result = "Error de comunicacion con el servidor";

		try {
			user = username;
			pass = password;

			String loginUrl = Constants.LOGIN_URL;
			loginUrl = loginUrl.replaceAll("<login>", user);
			loginUrl = loginUrl.replaceAll("<password>", pass);
			Log.i(LOG_DEBUG, loginUrl);

			String xml = null;
			if (debug) {
				xml = XMLParser.getXMLFromUrl(Constants.DEBUG_URL);
			} else {
				xml = XMLParser.getXMLFromUrl(loginUrl);
			}
			Document doc = XMLParser.XMLfromString(xml);

			Log.i(LOG_DEBUG, "Realizando login");
			NodeList error = doc.getElementsByTagName(KEY_ERROR);
			result = XMLParser.getElementValue(error.item(0));

			if ("".equals(result)) {
				// Cargar el listado de carros
				Control.setCarList(doc);
				// Cargar el listado de movimientos
				Control.setMovementsList(doc);
				// Cargar el listado de clientes
				Control.setClientList(doc);

				Log.i(Constants.LOG_DEBUG, "Car Total "
						+ Control.getCarList().size());
				Log.i(Constants.LOG_DEBUG, "Movement Total "
						+ Control.getMovementList().size());
				Log.i(Constants.LOG_DEBUG, "Client Total "
						+ Control.getClientList().size());
			}
		} catch (IllegalArgumentException e) {
			result = "Error en los datos recibidos";
		} catch (Exception e) {
			result = "Error en la comunicacion";
		}

		return result;
	}

	public static boolean doCarBalancing() {
		String result = "Error de comunicacion con el servidor";

		try {
			String balanceCarUrl = Constants.BALANCE_CAR_URL;
			balanceCarUrl = balanceCarUrl.replaceAll("<login>", user);
			balanceCarUrl = balanceCarUrl.replaceAll("<pwd>", pass);
			balanceCarUrl = balanceCarUrl.replaceAll("<car>", ""
					+ Control.carSelected);
			Log.i(LOG_DEBUG, balanceCarUrl);

			String xml = null;
			if (debug) {
				xml = XMLParser.getXMLFromUrl(Constants.DEBUG_URL);
			} else {
				xml = XMLParser.getXMLFromUrl(balanceCarUrl);
			}
			Document doc = XMLParser.XMLfromString(xml);

			Log.i(LOG_DEBUG, "Realizando balanceo del carro");
			NodeList error = doc.getElementsByTagName(KEY_ERROR);
			result = XMLParser.getElementValue(error.item(0));

			if ("".equals(result)) {
				// Cargar el listado de carros
				Control.setCarList(doc);
				// Cargar el listado de movimientos
				Control.setMovementsList(doc);

				Log.i(Constants.LOG_DEBUG, "Car Total "
						+ Control.getCarList().size());
				Log.i(Constants.LOG_DEBUG, "Movement Total "
						+ Control.getMovementList().size());
			}
		} catch (IllegalArgumentException e) {
			result = "Error en los datos recibidos";
		} catch (Exception e) {
			result = "Error en la comunicacion";
		}

		boolean goToWarehouse = false;
		if ("".equals(result)) {
			MenuOption menuOption = Control.getNextMovementMenu();

			if (menuOption == null) {
				Control.message = "No es necesario realizar movimientos adicionales para balancear el carro";
				Control.carSelected = 0;
				Control.freeMovementStarted = false;
			} else {
				Control.setSelectedOption(menuOption);
				goToWarehouse = true;
			}
		} else {
			Control.message = result;
		}

		return goToWarehouse;
	}

	public static Class<? extends Activity> doMovement(AbstractForm caller,
			String weight, int clientId, String tag, MovementType movementType) {
		String result = "Error de comunicacion";

		try {
			String movementUrl = Constants.MOVEMENT_URL;
			movementUrl = movementUrl.replaceAll("<login>", user);
			movementUrl = movementUrl.replaceAll("<pwd>", pass);
			movementUrl = movementUrl.replaceAll("<type>",
					movementType.getMovementType());
			movementUrl = movementUrl.replaceAll("<balanceo>",
					Control.freeMovementMenu ? "N" : "S");
			movementUrl = movementUrl.replaceAll("<car>", Control.carSelected
					+ "");

			switch (movementType) {
			case IN:
				movementUrl = movementUrl.replaceAll("<weight>", weight);
				movementUrl = movementUrl.replaceAll("<clientId>", ""
						+ clientId);
				movementUrl = movementUrl.replaceAll("<tag>", tag);
				break;
			case OUT:
				movementUrl = movementUrl.replaceAll("<weight>", "0");
				movementUrl = movementUrl.replaceAll("<clientId>", "0");
				movementUrl = movementUrl.replaceAll("<tag>", tag);
				break;
			}
			Log.i(LOG_DEBUG, movementUrl);

			String xml = null;
			if (debug) {
				xml = XMLParser.getXMLFromUrl(Constants.DEBUG_URL);
			} else {
				xml = XMLParser.getXMLFromUrl(movementUrl);
			}
			Document doc = XMLParser.XMLfromString(xml);

			Log.i(LOG_DEBUG, "Realizando Movimiento");
			NodeList error = doc.getElementsByTagName(KEY_ERROR);
			result = XMLParser.getElementValue(error.item(0));
			Log.i(LOG_DEBUG, "Resultado movimiento " + result);

			if ("".equals(result)) {
				// Cargar el listado de carros
				Control.setCarList(doc);
				// Cargar el listado de movimientos
				Control.setMovementsList(doc);
				// Cargar el listado de clientes
				Control.setClientList(doc);

				Log.i(Constants.LOG_DEBUG, "Car Total "
						+ Control.getCarList().size());
				Log.i(Constants.LOG_DEBUG, "Movement Total "
						+ Control.getMovementList().size());
				Log.i(Constants.LOG_DEBUG, "Client Total "
						+ Control.getClientList().size());
			}
		} catch (IllegalArgumentException e) {
			result = "Error en los datos recibidos";
		} catch (Exception e) {
			result = "Error en la comunicacion";
		}

		Class<? extends Activity> nextActivity = null;
		if ("".equals(result)) {
			MenuOption menuOption = null;

			if (Control.freeMovementMenu) {
				Control.freeMovementStarted = Control.freeMovementStarted
						|| Control.freeMovementMenu;

				Control.message = "Movimiento confirmado";
				nextActivity = MenuFreeMovementActivity.class;
			} else {
				menuOption = Control.getNextMovementMenu();

				if (menuOption == null) {
					Control.message = "No se encontraron movimientos";
					nextActivity = MenuActivity.class;
				} else {
					Control.setSelectedOption(menuOption);
					nextActivity = WarehouseActivity.class;
				}
			}
		} else {
			Util.showMessage(caller, R.string.label_error, result);
		}

		return nextActivity;
	}

	public static MenuOption confirmMovement(Movement movement)
			throws Exception {
		Control.getMovementList().remove(movement);

		String result = null;
		Control.message = null;
		try {
			String confirmUrl = Constants.CONFIRM_URL;
			confirmUrl = confirmUrl.replaceAll("<login>", user);
			confirmUrl = confirmUrl.replaceAll("<pwd>", pass);
			confirmUrl = confirmUrl.replaceAll("<movementId>",
					"" + movement.getId());
			Log.i(LOG_DEBUG, confirmUrl);

			String xml = null;
			if (debug) {
				xml = XMLParser.getXMLFromUrl(Constants.DEBUG_URL);
			} else {
				xml = XMLParser.getXMLFromUrl(confirmUrl);
			}

			Document doc = XMLParser.XMLfromString(xml);
			NodeList error = doc.getElementsByTagName(KEY_ERROR);
			result = XMLParser.getElementValue(error.item(0));
		} catch (IllegalArgumentException e) {
			result = "Error en los datos recibidos";
		} catch (Exception e) {
			result = "Error en la comunicacion";
		}

		if (!"".equals(result)) {
			Control.message = result;
			throw new Exception(result);
		}

		return Control.getNextMovementMenu();
	}
}