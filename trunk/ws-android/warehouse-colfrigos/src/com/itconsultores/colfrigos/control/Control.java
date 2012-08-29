package com.itconsultores.colfrigos.control;

import static com.itconsultores.colfrigos.control.Constants.KEY_CAR;
import static com.itconsultores.colfrigos.control.Constants.KEY_CAR_NAME;
import static com.itconsultores.colfrigos.control.Constants.KEY_CLIENT;
import static com.itconsultores.colfrigos.control.Constants.KEY_CLIENTS;
import static com.itconsultores.colfrigos.control.Constants.KEY_CLIENT_ID;
import static com.itconsultores.colfrigos.control.Constants.KEY_CLIENT_NAME;
import static com.itconsultores.colfrigos.control.Constants.KEY_COORDINATE;
import static com.itconsultores.colfrigos.control.Constants.KEY_ERROR;
import static com.itconsultores.colfrigos.control.Constants.KEY_ID;
import static com.itconsultores.colfrigos.control.Constants.KEY_IN;
import static com.itconsultores.colfrigos.control.Constants.KEY_LABEL;
import static com.itconsultores.colfrigos.control.Constants.KEY_MOVEMENT;
import static com.itconsultores.colfrigos.control.Constants.KEY_MOVEMENTS;
import static com.itconsultores.colfrigos.control.Constants.KEY_MOVEMENT_CAR;
import static com.itconsultores.colfrigos.control.Constants.KEY_MOVEMENT_COORDINATE;
import static com.itconsultores.colfrigos.control.Constants.KEY_OUT;
import static com.itconsultores.colfrigos.control.Constants.KEY_POSITION;
import static com.itconsultores.colfrigos.control.Constants.KEY_POSITIONS;
import static com.itconsultores.colfrigos.control.Constants.KEY_STATUS;
import static com.itconsultores.colfrigos.control.Constants.KEY_TYPE;
import static com.itconsultores.colfrigos.control.Constants.KEY_WEIGHT;
import static com.itconsultores.colfrigos.control.Constants.LOG_DEBUG;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Constants.MovementType;
import com.itconsultores.colfrigos.dto.Car;
import com.itconsultores.colfrigos.dto.Client;
import com.itconsultores.colfrigos.dto.Movement;
import com.itconsultores.colfrigos.dto.MovementDetail;
import com.itconsultores.colfrigos.dto.Position;

public class Control {

	private static MenuOption selectedOption;
	private static int calculatedWeight = -1;
	private static String user;
	private static String pass;
	private static List<Car> carList;
	private static List<Movement> movementList;
	private static List<Client> clientList;
	public static String message;
	private static boolean debug = false;

	public static MenuOption getSelectedOption() {
		return selectedOption;
	}

	public static void setSelectedOption(MenuOption selected) {
		selectedOption = selected;
	}

	public static void resetCalculatedWeight() {
		calculatedWeight = -1;
	}

	public static void setCalculatedWeight(int basket, int box) {
		calculatedWeight = basket * box;
	}

	public static String doLogin(String username, String password) {
		String result = "Error de comunicacion con el servidor";

		try {
			String url = Constants.LOGIN_URL + "login=" + username
					+ "&password=" + password;
			Log.i(LOG_DEBUG, url);

			String xml = null;
			if (debug) {
				xml = XMLParser.getXMLFromUrl(Constants.DEBUG_URL);
			} else {
				xml = XMLParser.getXMLFromUrl(url);
			}

			Document doc = XMLParser.XMLfromString(xml);

			user = username;
			pass = password;

			Log.i(LOG_DEBUG, "Realizando login");
			NodeList error = doc.getElementsByTagName(KEY_ERROR);
			result = XMLParser.getElementValue(error.item(0));

			if ("".equals(result)) {
				// Cargar el listado de carros
				setCarList(doc);
				// Cargar el listado de movimientos
				setMovementsList(doc);
				// Cargar el listado de clientes
				setClientList(doc);

				Log.i(Constants.LOG_DEBUG, "Car Total " + carList.size());
				Log.i(Constants.LOG_DEBUG,
						"Movement Total " + movementList.size());
				Log.i(Constants.LOG_DEBUG, "Client Total " + clientList.size());
			}
		} catch (IllegalArgumentException e) {
			result = "Error en los datos recibidos";
		} catch (Exception e) {
			result = "Error en la comunicacion";
		}

		return result;
	}

	public static String doMovement(String weight, int clientId, String tag,
			MovementType movementType) {
		String result = "Error de comunicacion";

		try {
			String url = Constants.MOVEMENT_URL + "login=" + user + "&pwd="
					+ pass + "&type=" + movementType.getMovementType();

			switch (movementType) {
			case IN:
				url += "&weight=" + weight + "&clientId=" + clientId + "&tag=0";
				break;

			case OUT:
				url += "&tag=" + tag + "&weight=0&clientId=0";
				break;
			}
			Log.i(LOG_DEBUG, url);

			String xml = null;
			if (debug) {
				xml = XMLParser.getXMLFromUrl(Constants.DEBUG_URL);
			} else {
				xml = XMLParser.getXMLFromUrl(url);
			}

			Document doc = XMLParser.XMLfromString(xml);

			Log.i(LOG_DEBUG, "Realizando login");
			NodeList error = doc.getElementsByTagName(KEY_ERROR);
			result = XMLParser.getElementValue(error.item(0));

			if ("".equals(result)) {
				// Cargar el listado de carros
				setCarList(doc);
				// Cargar el listado de movimientos
				setMovementsList(doc);
				// Cargar el listado de clientes
				setClientList(doc);

				Log.i(Constants.LOG_DEBUG, "Car Total " + carList.size());
				Log.i(Constants.LOG_DEBUG,
						"Movement Total " + movementList.size());
				Log.i(Constants.LOG_DEBUG, "Client Total " + clientList.size());
			}
		} catch (IllegalArgumentException e) {
			result = "Error en los datos recibidos";
		} catch (Exception e) {
			result = "Error en la comunicacion";
		}

		return result;
	}

	public static MenuOption getNextMovementMenu() {
		MenuOption nextMenu = null;

		if (!movementList.isEmpty()) {
			nextMenu = movementList.get(0).getMovementType().getMenuOption();
			Log.i(LOG_DEBUG, "Next Menu " + nextMenu);
		}

		return nextMenu;
	}

	public static MenuOption confirmMovement(Movement movement)
			throws Exception {
		movementList.remove(movement);

		String result = null;
		message = null;
		try {
			String url = Constants.CONFIRM_URL + "login=" + user + "&pwd="
					+ pass + "&movementId=" + movement.getId();
			Log.i(LOG_DEBUG, url);

			String xml = null;
			if (debug) {
				xml = XMLParser.getXMLFromUrl(Constants.DEBUG_URL);
			} else {
				xml = XMLParser.getXMLFromUrl(url);
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
			message = result;
			throw new Exception(result);
		}

		return getNextMovementMenu();
	}

	@SuppressWarnings("unused")
	public static void setCarList(Document doc) {
		carList = new ArrayList<Car>();

		Node positions = doc.getElementsByTagName(KEY_POSITIONS).item(0);
		if (positions == null) {
			Log.i(LOG_DEBUG, "No hay posiciones para leer");
			return;
		}

		NodeList NodeListCar = ((Element) positions)
				.getElementsByTagName(KEY_CAR);

		carLoop: for (int i = 0; i < NodeListCar.getLength(); i++) {
			Node carNode = NodeListCar.item(i);

			String carNumber = ((Element) carNode).getAttribute(KEY_CAR_NAME);
			List<Position> sideA = new ArrayList<Position>();
			List<Position> sideB = new ArrayList<Position>();

			NodeList NodeListPosition = ((Element) carNode)
					.getElementsByTagName(KEY_POSITION);
			positionLoop: for (int j = 0; j < NodeListPosition.getLength(); j++) {
				Node positionNode = NodeListPosition.item(j);

				String coordinate = XMLParser.getValue((Element) positionNode,
						KEY_COORDINATE);
				String status = XMLParser.getValue((Element) positionNode,
						KEY_STATUS);

				Log.i(LOG_DEBUG, "Loaded Position " + carNumber + " "
						+ coordinate + " " + status);

				Position position = new Position(coordinate, status);
				if ("A".equals(position.getSide())) {
					sideA.add(position);
				} else if ("B".equals(position.getSide())) {
					sideB.add(position);
				} else {
					Log.i(LOG_DEBUG, "El lado solo puede ser A o B : "
							+ position.getSide());
					throw new IllegalArgumentException();
				}
			}

			Car car = new Car(carNumber, sideA, sideB);
			carList.add(car);
		}

		return;
	}

	@SuppressWarnings("unused")
	public static void setMovementsList(Document doc) {
		movementList = new ArrayList<Movement>();

		Node movements = doc.getElementsByTagName(KEY_MOVEMENTS).item(0);
		if (movements == null) {
			Log.i(LOG_DEBUG, "No hay movimientos para leer");
			return;
		}

		NodeList NodeListMovement = ((Element) movements)
				.getElementsByTagName(KEY_MOVEMENT);

		movementLoop: for (int i = 0; i < NodeListMovement.getLength(); i++) {
			Node movementNode = NodeListMovement.item(i);

			String id = null;
			String rawMovementType = XMLParser.getValue((Element) movementNode,
					KEY_TYPE);
			MovementType movementType = MovementType.getType(rawMovementType);

			List<MovementDetail> movementDetails = new ArrayList<MovementDetail>();

			switch (movementType) {
			case IN_OUT:
				Node inMovement = ((Element) movementNode)
						.getElementsByTagName(KEY_IN).item(0);
				id = XMLParser.getValue((Element) inMovement, KEY_ID);
				MovementDetail imdt = initMovementDetail(id, inMovement,
						MovementType.IN);
				movementDetails.add(imdt);

				Node outMovement = ((Element) movementNode)
						.getElementsByTagName(KEY_OUT).item(0);
				MovementDetail omdt = initMovementDetail(id, outMovement,
						MovementType.OUT);
				movementDetails.add(omdt);
				break;
			case IN:
			case OUT:
				id = XMLParser.getValue((Element) movementNode, KEY_ID);
				MovementDetail movementDetail = initMovementDetail(id,
						movementNode, movementType);
				movementDetails.add(movementDetail);
				break;
			}

			Movement movement = new Movement(id, movementType, movementDetails);
			movementList.add(movement);
		}

		return;
	}

	private static MovementDetail initMovementDetail(String id, Node node,
			MovementType mvType) {

		String car = XMLParser.getValue((Element) node, KEY_MOVEMENT_CAR);
		String coordinate = XMLParser.getValue((Element) node,
				KEY_MOVEMENT_COORDINATE);
		String label = XMLParser.getValue((Element) node, KEY_LABEL);
		String weight = XMLParser.getValue((Element) node, KEY_WEIGHT);

		Log.i(LOG_DEBUG, "Loaded Movement " + id + " " + car + " " + coordinate
				+ " " + label + " " + weight + " " + mvType);
		MovementDetail md = new MovementDetail(car, coordinate, label, weight,
				mvType);

		return md;
	}

	@SuppressWarnings("unused")
	public static void setClientList(Document doc) {
		clientList = new ArrayList<Client>();

		Node clients = doc.getElementsByTagName(KEY_CLIENTS).item(0);
		if (clients == null) {
			Log.i(LOG_DEBUG, "No hay clientes para leer");
			return;
		}

		NodeList NodeListClient = ((Element) clients)
				.getElementsByTagName(KEY_CLIENT);

		clientLoop: for (int i = 0; i < NodeListClient.getLength(); i++) {
			Node clientNode = NodeListClient.item(i);

			String id = XMLParser.getValue((Element) clientNode, KEY_CLIENT_ID);
			String name = XMLParser.getValue((Element) clientNode,
					KEY_CLIENT_NAME);

			Log.i(LOG_DEBUG, "Loaded Client " + id + " " + name);

			Client client = new Client(id, name);
			clientList.add(client);
		}

		return;
	}

	public static String[] getClientArray() {
		String[] clientArray = new String[clientList.size() + 1];
		clientArray[0] = "Seleccione el cliente";
		int index = 0;
		for (Client client : clientList) {
			clientArray[++index] = client.getName();
		}
		return clientArray;
	}

	public static int getCalculatedWeight() {
		return calculatedWeight;
	}

	public static String getUser() {
		return user;
	}

	public static String getPass() {
		return pass;
	}

	public static List<Car> getCarList() {
		return carList;
	}

	public static List<Movement> getMovementList() {
		return movementList;
	}

	public static List<Client> getClientList() {
		return clientList;
	}
}
