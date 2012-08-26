package com.itconsultores.colfrigos.android;

import java.util.List;

import org.w3c.dom.Document;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.itconsultores.colfrigos.control.Car;
import com.itconsultores.colfrigos.control.Constants;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.Movement;
import com.itconsultores.colfrigos.control.XMLParser;

public class LoginActivity extends Activity implements OnClickListener {

	private Button buttonLogin;
	private Button buttonExit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_login);

		buttonLogin = (Button) findViewById(R.id.sl_button_login);
		buttonExit = (Button) findViewById(R.id.sl_button_exit);
		buttonLogin.setOnClickListener(this);
		buttonExit.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onClick(View view) {

		if (view.equals(buttonLogin)) {
			String xml = XMLParser.getXMLFromUrl(Constants.URL); // getting XML
			Document doc = XMLParser.XMLfromString(xml);

			// Cargar el lsitado de carros
			//List<Car> carList = Control.getCarList(doc);
			// Cargar el lsitado de movimientos
			List<Movement> movementList = Control.getMovementsList(doc);
			
			System.out.println(movementList.size());

			Intent intentMenu = new Intent(this, MenuActivity.class);
			startActivity(intentMenu);
		} else if (view.equals(buttonExit)) {
			finish();
		}
	}
}