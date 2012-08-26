package com.itconsultores.colfrigos.control;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Movement.MovementType;
import com.itconsultores.colfrigos.control.MovementDetail.MovementDetailType;

public class Control {

	static final String KEY_POSITIONS = "posiciones";
	static final String KEY_CAR = "carro";
	static final String KEY_CAR_NAME = "name";
	static final String KEY_POSITION = "posicion";
	static final String KEY_SIDE = "lado";
	static final String KEY_COORDINATE = "coordenada";
	static final String KEY_STATUS = "estado";
	
	static final String KEY_MOVEMENTS = "movimientos";
	static final String KEY_MOVEMENT = "movimiento";
	static final String KEY_ID = "id";
	static final String KEY_TYPE = "tipo";
	static final String KEY_LABEL = "etiqueta";
	static final String KEY_WEIGHT = "peso";
	static final String KEY_ROLLING = "balanceo";

	static final String KEY_IN = "entra";
	static final String KEY_OUT = "sale";

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
	
	@SuppressWarnings("unused")
	public static List<Movement> getMovementsList(Document doc) {
		List<Movement> movementList = new ArrayList<Movement>();

		Node movements = doc.getElementsByTagName(KEY_MOVEMENTS).item(0);
		NodeList nodeListMovement = ((Element) movements)
				.getElementsByTagName(KEY_MOVEMENT);

		movementLoop: for (int i = 0; i < nodeListMovement.getLength(); i++) {
			Node movementNode = nodeListMovement.item(i);

//			String movementType = ((Element) movementNode)
//					.getElementsByTagName(KEY_TYPE).item(0)
//					.getNodeValue();
			
			String movementType=XMLParser.getValue((Element) movementNode, KEY_TYPE);
			
			 ArrayList<MovementDetail> movementDetails=new
			 ArrayList<MovementDetail>();
			 
			 
			 if(movementType.equals("salida-entrada")){//TODO se puede utilizar el Enum?
			
				 Node inMovement = ((Element) movementNode).getElementsByTagName(KEY_IN).item(0);
					MovementDetail imdt= initMovementDetail(inMovement,"entrada");
					 movementDetails.add(imdt);
				Node outMovement = ((Element) movementNode).getElementsByTagName(KEY_OUT).item(0);
					MovementDetail omdt= initMovementDetail(outMovement,"salida");
					 movementDetails.add(omdt);		 
			 }else{
				 
				MovementDetail movementDetail= initMovementDetail(movementNode,movementType);//El mov es unico y es del tipo del padre.
				 movementDetails.add(movementDetail);
			 }
			 Movement movement= new Movement(movementType,movementDetails);
			 movementList.add(movement);
			
			 }	
			
		return movementList;
	}	
	
	
	private static MovementDetail initMovementDetail(Node node,String mvType){
		
		 String id=XMLParser.getValue((Element)node,KEY_ID);
		 String car=XMLParser.getValue((Element)node,KEY_CAR);
		 String coordinate=XMLParser.getValue((Element)node,KEY_COORDINATE);
		 String label=XMLParser.getValue((Element)node,KEY_LABEL);
		 String rolling=XMLParser.getValue((Element)node,KEY_ROLLING);
		 String movementDetailType=mvType;
		 String weight=XMLParser.getValue((Element)node,KEY_WEIGHT);
		
		 return new MovementDetail(id,car,coordinate,label,weight,rolling,movementDetailType);

	}	
	
//private static MovementDetail initMovementDetail(Node node,String movementType){
//		
//		 String id=((Element) node)
//					.getElementsByTagName(KEY_ID).item(0)
//					.getNodeValue();
//		 String car=((Element) node)
//					.getElementsByTagName(KEY_CAR).item(0)
//					.getNodeValue();
//		 String coordinate=((Element) node)
//					.getElementsByTagName(KEY_COORDINATE).item(0)
//					.getNodeValue();
//		 String label=((Element) node)
//					.getElementsByTagName(KEY_LABEL).item(0)
//					.getNodeValue();
//		 String rolling=((Element) node)
//					.getElementsByTagName(KEY_ROLLING).item(0)
//					.getNodeValue();
//		 String movementDetailType=movementType;
//		 String weight=((Element) node)
//					.getElementsByTagName(KEY_WEIGHT).item(0)
//					.getNodeValue();
//		
//		 return new MovementDetail(id,car,coordinate,label,weight,rolling,movementDetailType);
//
//	}
	
	
}
