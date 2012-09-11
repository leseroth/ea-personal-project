package com.itconsultores.colfrigos.control;

import static com.itconsultores.colfrigos.control.Constants.KEY_CAR;
import static com.itconsultores.colfrigos.control.Constants.KEY_CAR_NAME;
import static com.itconsultores.colfrigos.control.Constants.KEY_CLIENT;
import static com.itconsultores.colfrigos.control.Constants.KEY_CLIENTS;
import static com.itconsultores.colfrigos.control.Constants.KEY_CLIENT_ID;
import static com.itconsultores.colfrigos.control.Constants.KEY_CLIENT_NAME;
import static com.itconsultores.colfrigos.control.Constants.KEY_COORDINATE;
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

import com.itconsultores.colfrigos.android.FormInActivity;
import com.itconsultores.colfrigos.android.MenuActivity;
import com.itconsultores.colfrigos.android.MenuFreeMovementActivity;
import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Constants.MovementType;
import com.itconsultores.colfrigos.dto.Car;
import com.itconsultores.colfrigos.dto.Client;
import com.itconsultores.colfrigos.dto.Movement;
import com.itconsultores.colfrigos.dto.MovementDetail;
import com.itconsultores.colfrigos.dto.Position;

/**
 * Clase central que debe ser usada solo de manera estatica por toda la
 * aplicacion, permite controlar los movimientos realizados, pendientes, carros,
 * clientes, etc.
 * 
 * @author Erik
 * 
 */
public class Control {

	/**
	 * Permite identificar la opcion de menu seleccionada, ver
	 * {@link MenuOption}
	 */
	private static MenuOption selectedOption;
	/**
	 * Peso calculado, se usa cuado desde la pantalla de Calculo de estiva se
	 * pasa a la de entrada
	 */
	private static int calculatedWeight = -1;
	/**
	 * Listado de carros, se actualiza cada vez que se recibe una programación,
	 * debe haber carros para todos los movimientos identificados. Ver
	 * {@link Car}
	 */
	private static List<Car> carList;
	/**
	 * Listado de movimientos, se llena cuando se recibe una programación, estos
	 * deben llegar en el orden que deben ejecutarse, a medida que se va a
	 * confirmando cada movimiento se retira la primera posicion de la lista.
	 * Ver {@link Movement}
	 */
	private static List<Movement> movementList;
	/**
	 * Listado de clientes, debe venir como respuesta al login, se lee una única
	 * vez, para recargar el listado de clientes se debe salir de la aplicación.
	 * Ver {@link Client}
	 */
	private static List<Client> clientList;

	// Valores asociados a movimientos libres
	/**
	 * Identifica el carro que fue seleccionado para movimientos sin balanceo
	 */
	public static int carSelected = 0;
	/**
	 * Indica que se han iniciado movimientos sobre el carro seleccionado, esta
	 * bandera cambia a true cuando se confirma al menos un movimiento contra el
	 * servidor.
	 */
	public static boolean freeMovementStarted = false;
	/**
	 * Indica si se esta accediendo a Entrada o Salida por medio del menu de
	 * movimientos sin balanceo
	 */
	public static boolean freeMovementMenu = false;

	/**
	 * Mensaje de error que se muestra desde {@link MenuActivity} o
	 * {@link MenuFreeMovementActivity} como resultado a una entrada o una
	 * salida, una vez se muestra en pantalla debe borrarse.
	 */
	public static String message;

	/**
	 * Recibe un {@link Document} e inicializaliza el listado de carros
	 * 
	 * @param doc
	 *            Documento
	 */
	@SuppressWarnings("unused")
	protected static void setCarList(Document doc) {
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
				if ("A".equals(position.getCarSide())) {
					sideA.add(position);
				} else if ("B".equals(position.getCarSide())) {
					sideB.add(position);
				} else {
					Log.i(LOG_DEBUG, "El lado solo puede ser A o B : "
							+ position.getCarSide());
					throw new IllegalArgumentException();
				}
			}

			Car car = new Car(carNumber, sideA, sideB);
			carList.add(car);
		}

		return;
	}

	/**
	 * Recibe un {@link Document} e inicializa la lista de movimientos
	 * 
	 * @param doc
	 *            Documento
	 */
	@SuppressWarnings("unused")
	protected static void setMovementsList(Document doc) {
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

	/**
	 * Permite inicializar el detalle de un movimento
	 * 
	 * @param id
	 *            Id del movimiento
	 * @param node
	 *            Nodo que contiene el movmiento
	 * @param mvType
	 *            Tipo de movimiento
	 * @return Detalle de un movimiento, {@link MovementDetail}
	 */
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

	/**
	 * Recibe un {@link Document} e incializa la lista de clientes
	 * 
	 * @param doc
	 *            Documento
	 */
	@SuppressWarnings("unused")
	protected static void setClientList(Document doc) {
		// Cargar la lista de clientes solo una vez
		if (clientList != null && !clientList.isEmpty()) {
			Log.i(LOG_DEBUG, "La lista de clientes ya estaba cargada");
			return;
		}
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

	/**
	 * Retorna un arreglo con el nombre de los clientes para ser visualizado en
	 * el Spinner de {@link FormInActivity}
	 * 
	 * @return
	 */
	public static String[] getClientArray() {
		String[] clientArray = new String[clientList.size() + 1];
		clientArray[0] = "Seleccione el cliente";
		int index = 0;
		for (Client client : clientList) {
			clientArray[++index] = client.getName();
		}
		return clientArray;
	}

	/**
	 * Retorna el siguiente movimiento que debe ejecutarse
	 * 
	 * @return
	 */
	public static MenuOption getNextMovementMenu() {
		MenuOption nextMenu = null;

		if (!movementList.isEmpty()) {
			nextMenu = movementList.get(0).getMovementType().getMenuOption();
			Log.i(LOG_DEBUG, "Next Menu " + nextMenu);
		}

		return nextMenu;
	}

	/**
	 * Resetea el peso calculado
	 */
	public static void resetCalculatedWeight() {
		calculatedWeight = -1;
	}

	/**
	 * Calcula el peso en la pantalla de estiva
	 * 
	 * @param basket
	 * @param box
	 */
	public static void setCalculatedWeight(int basket, int box) {
		calculatedWeight = basket * box;
	}

	public static MenuOption getSelectedOption() {
		return selectedOption;
	}

	public static void setSelectedOption(MenuOption selected) {
		selectedOption = selected;
	}

	public static int getCalculatedWeight() {
		return calculatedWeight;
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