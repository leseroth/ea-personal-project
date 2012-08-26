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
	public static int calculatedWeight = -1;

	public static MenuOption getSelectedOption() {
		return selectedOption;
	}

	public static void setSelectedOption(MenuOption selected) {
		selectedOption = selected;
	}

	public static String doLogin(Document doc, String user, String pass) {
		Log.i(LOG_DEBUG, "Realizando login");
		NodeList error = doc.getElementsByTagName(KEY_ERROR);
		return XMLParser.getElementValue(error.item(0));
	}

	@SuppressWarnings("unused")
	public static List<Car> getCarList(Document doc) {
		List<Car> carList = new ArrayList<Car>();

		Node positions = doc.getElementsByTagName(KEY_POSITIONS).item(0);
		if (positions == null) {
			Log.i(LOG_DEBUG, "No hay posiciones para leer");
			return carList;
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

		return carList;
	}

	@SuppressWarnings("unused")
	public static List<Movement> getMovementsList(Document doc) {
		List<Movement> movementList = new ArrayList<Movement>();

		Node movements = doc.getElementsByTagName(KEY_MOVEMENTS).item(0);
		if (movements == null) {
			Log.i(LOG_DEBUG, "No hay movimientos para leer");
			return movementList;
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

		return movementList;
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
	public static List<Client> getClientList(Document doc) {
		List<Client> clientList = new ArrayList<Client>();

		Node clients = doc.getElementsByTagName(KEY_CLIENTS).item(0);
		if (clients == null) {
			Log.i(LOG_DEBUG, "No hay clientes para leer");
			return clientList;
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

		return clientList;
	}
}