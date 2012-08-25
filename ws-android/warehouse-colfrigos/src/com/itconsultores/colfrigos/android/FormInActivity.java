package com.itconsultores.colfrigos.android;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.itconsultores.colfrigos.control.Car;
import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.Movement;
import com.itconsultores.colfrigos.control.MovementDetail;
import com.itconsultores.colfrigos.control.Position;
import com.itconsultores.colfrigos.control.XMLParser;

public class FormInActivity extends AbstractForm {

	private Button buttonOk;
	private Button buttonBack;

	private TextView weightTextView;
	private Spinner clientSpinner;

	private String[] clientArray;

 //   static final String URL = "http://ea-personal-project.googlecode.com/svn/trunk/ws-android/movimiento.html?format=xml";
    static final String URL ="http://api.androidhive.info/pizza/?format=xml";
    // XML node keys
    static final String KEY_POSITIONS = "posiciones";
    static final String KEY_CAR = "carro ";
    static final String KEY_NAME= "estado";

    static final String KEY_POSITION = "posicion";
    static final String KEY_SIDE = "lado";
    static final String KEY_COORDINATE = "coordenada ";
    static final String KEY_STATUS= "estado";

    static final String KEY_MOVEMENTS = "movimientos";
    static final String KEY_MOVEMENT = "movimiento";
    static final String KEY_ID = "id";
    static final String KEY_TYPE = "tipo";
    static final String KEY_LABEL = "etiqueta";
    static final String KEY_WEIGHT = "peso";   
    static final String KEY_ROLLING = "balanceo";
    
    static final String KEY_IN = "entra";
    static final String KEY_OUT= "sale";
	
	public FormInActivity() {
		super(R.layout.form_entrada);
	}

	@Override
	protected void initForm() {
		// Botones
		buttonOk = initButton(R.id.f_in_button_confirm);
		buttonBack = initButton(R.id.f_in_button_back);

		// Peso
		weightTextView = (TextView) findViewById(R.id.f_in_textbox_weight);
		if (Control.calculatedWeight == -1) {
			weightTextView.setText("");
		} else {
			weightTextView.setText("" + Control.calculatedWeight);
			Control.calculatedWeight = -1;
		}

		// Clientes
		clientSpinner = (Spinner) findViewById(R.id.f_in_spinner_client);
		clientArray = new String[] { "Selecione Cliente", "Cliente 1",
				"Cliente A", "Cliente Z" };

		ArrayAdapter<String> clientArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, clientArray);
		clientArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		clientSpinner.setAdapter(clientArrayAdapter);
		
		
		  XMLParser parser = new XMLParser();
	        String xml = parser.getXmlFromUrl(URL); // getting XML
	        if (xml==null){
	          	Toast.makeText(this, "Problema al cargar el archivo", Toast.LENGTH_SHORT);
	        }
	        Document doc = parser.getDomElement(xml); // getting DOM element
	        if (doc==null){
	          	Toast.makeText(this, "Problema en el archivo", Toast.LENGTH_SHORT);
	        }
	        NodeList nodePositions = doc.getElementsByTagName(KEY_POSITIONS);
	        Car myCar=new Car();
	        //Only One car
	        Element elementPosition=(Element) nodePositions.item(0);
	        
	       if (elementPosition.hasChildNodes()){
	        NodeList otherNodes=elementPosition.getChildNodes();
	        
	       String content1= otherNodes.item(0).getTextContent();
	       String content2= otherNodes.item(1).getTextContent();
	       String content3= otherNodes.item(2).getTextContent();
	       String content4= otherNodes.item(3).getTextContent();
	       String content5= otherNodes.item(4).getTextContent();
	       
	       if(otherNodes.item(3).hasChildNodes()){
	    	   
	    	   NodeList carNodes=otherNodes.item(3).getChildNodes();
		       String content6= carNodes.item(0).getTextContent();
		       String content7= carNodes.item(1).getTextContent();


	    	   
	    	   
	       }

	        
	        NodeList nodeCars= elementPosition.getElementsByTagName(KEY_CAR);
	        Element elementCar=(Element) nodeCars.item(1);
	        String scar= elementCar.toString();
	       }
//	        myCar.setName(elementCar.getAttribute(KEY_NAME));
//	        
//	        ArrayList<Position> positions=new ArrayList<Position>();
//	        NodeList positionNodes=  elementCar.getChildNodes();
//	        for (int i = 0; i < positionNodes.getLength(); i++) {
//	        				Position position= new Position();
//	        	            Element e = (Element)positionNodes.item(i);
//	        	            position.setSide(parser.getValue(e, KEY_SIDE)) ;
//	        	            position.setCoordinate(parser.getValue(e, KEY_COORDINATE)) ;
//	        	            position.setStatus(parser.getValue(e, KEY_STATUS)) ;
//	        	            
//	        	            positions.add(position);
//	        }
//	        
//	        ArrayList<Movement> movements=new ArrayList<Movement>();
//	        NodeList nodeMovements = doc.getElementsByTagName(KEY_MOVEMENTS);
//	        for (int i = 0; i < nodeMovements.getLength(); i++) {
//	        	Movement movement= new Movement();
//	        	Element e = (Element)positionNodes.item(i);
//	        	movement.setType(parser.getValue(e, KEY_TYPE));
//	        	ArrayList<MovementDetail> movementDetails=new ArrayList<MovementDetail>();
//	        	if(movement.getType()=="salida-entrada"){
//	        		
//	        		//TODO 
//	        	}else{
//	        		MovementDetail movementDetail= new MovementDetail();
//	        		movementDetail.setId(parser.getValue(e, KEY_ID));
//	        		movementDetail.setCar(parser.getValue(e, KEY_CAR));
//	        		movementDetail.setCoordinate(parser.getValue(e, KEY_COORDINATE));
//	        		movementDetail.setLabel(parser.getValue(e, KEY_LABEL));
//	        		movementDetail.setRolling(parser.getValue(e, KEY_ROLLING));
//	        		movementDetail.setSubtype(null);//TODO 
//	        		movementDetail.setWeight(parser.getValue(e, KEY_WEIGHT));
//
//	        		movementDetails.add(movementDetail);
//	        	}
//	        	movement.setMovementDetails(movementDetails);
//	        	movements.add(movement);
//	        	
//	        }
//
//	        
//	        Toast.makeText(this, "Car:" +myCar.getName()+" Positions:"+myCar.getPositions().size()+" Movements:"+movements.size(), Toast.LENGTH_SHORT);

		
	}

	@Override
	public void onClick(View view) {
		Intent selectedIntent = null;

		if (view.equals(buttonBack)) {
			selectedIntent = new Intent(this, MenuActivity.class);
		} else if (view.equals(buttonOk)) {
			Control.setSelectedOption(MenuOption.Entrada);
			selectedIntent = new Intent(this, WarehouseActivity.class);
		}

		if (selectedIntent != null) {
			startActivity(selectedIntent);
		}
	}

	@Override
	protected boolean isInfoComplete() {
		// TODO Validar Informacion
		return true;
	}
}
