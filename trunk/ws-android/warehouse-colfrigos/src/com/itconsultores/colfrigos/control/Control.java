package com.itconsultores.colfrigos.control;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.itconsultores.colfrigos.control.Constants.MenuOption;

public class Control {

	static final String KEY_POSITIONS = "posiciones";
	static final String KEY_CAR = "carro";
	static final String KEY_CAR_NAME = "name";
	static final String KEY_POSITION = "posicion";
	static final String KEY_SIDE = "lado";
	static final String KEY_COORDINATE = "coordenada";
	static final String KEY_STATUS = "estado";

	private static MenuOption selectedOption;
	public static int calculatedWeight = -1;

	public static MenuOption getSelectedOption() {
		return selectedOption;
	}

	public static void setSelectedOption(MenuOption selected) {
		selectedOption = selected;
	}

	@SuppressWarnings("unused")
	public static List<Car> getCarList(Document doc) {
		List<Car> carList = new ArrayList<Car>();

		Node positions = doc.getElementsByTagName(KEY_POSITIONS).item(0);
		NodeList nodeListCar = ((Element) positions)
				.getElementsByTagName(KEY_CAR);

		carLoop: for (int i = 0; i < nodeListCar.getLength(); i++) {
			Node carNode = nodeListCar.item(i);

			String name = ((Element) carNode).getAttribute(KEY_CAR_NAME);
			List<Position> sideA = new ArrayList<Position>();
			List<Position> sideB = new ArrayList<Position>();

			NodeList nodeListPosition = ((Element) carNode)
					.getElementsByTagName(KEY_POSITION);
			positionLoop: for (int j = 0; j < nodeListPosition.getLength(); j++) {
				Node positionNode = nodeListPosition.item(j);

				String coordinate = ((Element) positionNode)
						.getElementsByTagName(KEY_COORDINATE).item(0)
						.getNodeValue();
				String side = ((Element) positionNode)
						.getElementsByTagName(KEY_SIDE).item(0).getNodeValue();
				String status = ((Element) positionNode)
						.getElementsByTagName(KEY_STATUS).item(0)
						.getNodeValue();

				System.err.println(name + " " + side + " " + coordinate + " "
						+ status);

				Position position = new Position(coordinate, status);
				if ("A".equals(side)) {
					sideA.add(position);
				} else if ("B".equals(side)) {
					sideB.add(position);
				}
			}

			Car car = new Car(name, sideA, sideB);
			carList.add(car);
		}

		return carList;
	}
}
