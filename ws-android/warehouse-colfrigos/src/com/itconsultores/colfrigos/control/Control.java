package com.itconsultores.colfrigos.control;

import static com.itconsultores.colfrigos.control.Constants.KEY_CAR;
import static com.itconsultores.colfrigos.control.Constants.KEY_CAR_NAME;
import static com.itconsultores.colfrigos.control.Constants.KEY_COORDINATE;
import static com.itconsultores.colfrigos.control.Constants.KEY_ID;
import static com.itconsultores.colfrigos.control.Constants.KEY_IN;
import static com.itconsultores.colfrigos.control.Constants.KEY_LABEL;
import static com.itconsultores.colfrigos.control.Constants.KEY_MOVEMENT;
import static com.itconsultores.colfrigos.control.Constants.KEY_MOVEMENTS;
import static com.itconsultores.colfrigos.control.Constants.KEY_OUT;
import static com.itconsultores.colfrigos.control.Constants.KEY_POSITION;
import static com.itconsultores.colfrigos.control.Constants.KEY_POSITIONS;
import static com.itconsultores.colfrigos.control.Constants.KEY_ROLLING;
import static com.itconsultores.colfrigos.control.Constants.KEY_SIDE;
import static com.itconsultores.colfrigos.control.Constants.KEY_STATUS;
import static com.itconsultores.colfrigos.control.Constants.KEY_TYPE;
import static com.itconsultores.colfrigos.control.Constants.KEY_WEIGHT;
import static com.itconsultores.colfrigos.control.Constants.LOG_DEBUG;

import java.util.TreeSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.util.Log;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Constants.MovementType;
import com.itconsultores.colfrigos.dto.Car;
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

	@SuppressWarnings("unused")
	public static TreeSet<Car> getCarSet(Document doc) {
		TreeSet<Car> carSet = new TreeSet<Car>();

		Node positions = doc.getElementsByTagName(KEY_POSITIONS).item(0);
		NodeList NodeListCar = ((Element) positions)
				.getElementsByTagName(KEY_CAR);

		carLoop: for (int i = 0; i < NodeListCar.getLength(); i++) {
			Node carNode = NodeListCar.item(i);

			String number = ((Element) carNode).getAttribute(KEY_CAR_NAME);
			TreeSet<Position> sideA = new TreeSet<Position>();
			TreeSet<Position> sideB = new TreeSet<Position>();

			NodeList NodeListPosition = ((Element) carNode)
					.getElementsByTagName(KEY_POSITION);
			positionLoop: for (int j = 0; j < NodeListPosition.getLength(); j++) {
				Node positionNode = NodeListPosition.item(j);

				String coordinate = XMLParser.getValue((Element) positionNode,
						KEY_COORDINATE);
				String side = XMLParser.getValue((Element) positionNode,
						KEY_SIDE);
				String status = XMLParser.getValue((Element) positionNode,
						KEY_STATUS);

				Log.i(LOG_DEBUG, "Loaded Position " + number + " " + side + " "
						+ coordinate + " " + status);

				Position position = new Position(coordinate, status);
				boolean newPosition;
				if ("A".equals(side)) {
					newPosition = sideA.add(position);
				} else if ("B".equals(side)) {
					newPosition = sideB.add(position);
				} else {
					Log.i(LOG_DEBUG, "El lado solo puede ser A o B : " + side);
					throw new IllegalArgumentException();
				}

				if (!newPosition) {
					Log.i(LOG_DEBUG, "Posición duplicada");
					throw new IllegalArgumentException();
				}
			}

			Car car = new Car(number, sideA, sideB);
			carSet.add(car);
		}

		return carSet;
	}

	@SuppressWarnings("unused")
	public static TreeSet<Movement> getMovementsSet(Document doc) {
		TreeSet<Movement> movementSet = new TreeSet<Movement>();

		Node movements = doc.getElementsByTagName(KEY_MOVEMENTS).item(0);
		NodeList NodeListMovement = ((Element) movements)
				.getElementsByTagName(KEY_MOVEMENT);

		movementLoop: for (int i = 0; i < NodeListMovement.getLength(); i++) {
			Node movementNode = NodeListMovement.item(i);

			String rawMovementType = XMLParser.getValue((Element) movementNode,
					KEY_TYPE);
			MovementType movementType = MovementType.getType(rawMovementType);

			TreeSet<MovementDetail> movementDetails = new TreeSet<MovementDetail>();

			boolean newMovement = true;
			switch (movementType) {
			case IN_OUT:
				Node inMovement = ((Element) movementNode)
						.getElementsByTagName(KEY_IN).item(0);
				MovementDetail imdt = initMovementDetail(inMovement,
						MovementType.IN);
				newMovement = newMovement && movementDetails.add(imdt);

				Node outMovement = ((Element) movementNode)
						.getElementsByTagName(KEY_OUT).item(0);
				MovementDetail omdt = initMovementDetail(outMovement,
						MovementType.OUT);
				newMovement = newMovement && movementDetails.add(omdt);
				break;
			case IN:
			case OUT:
				MovementDetail movementDetail = initMovementDetail(
						movementNode, movementType);
				newMovement = newMovement
						&& movementDetails.add(movementDetail);
				break;
			}

			if (!newMovement) {
				Log.i(LOG_DEBUG, "Movimiento duplicada");
				throw new IllegalArgumentException();
			}

			Movement movement = new Movement(movementType, movementDetails);
			movementSet.add(movement);
		}

		return movementSet;
	}

	private static MovementDetail initMovementDetail(Node node,
			MovementType mvType) {

		String id = XMLParser.getValue((Element) node, KEY_ID);
		String car = XMLParser.getValue((Element) node, KEY_CAR);
		String coordinate = XMLParser.getValue((Element) node, KEY_COORDINATE);
		String label = XMLParser.getValue((Element) node, KEY_LABEL);
		String rolling = XMLParser.getValue((Element) node, KEY_ROLLING);
		String weight = XMLParser.getValue((Element) node, KEY_WEIGHT);

		Log.i(LOG_DEBUG, "Loaded Movement " + id + " " + car + " " + coordinate
				+ " " + label + " " + rolling + " " + weight + " " + mvType);
		MovementDetail md = new MovementDetail(id, car, coordinate, label,
				weight, rolling, mvType);

		return md;
	}
}
