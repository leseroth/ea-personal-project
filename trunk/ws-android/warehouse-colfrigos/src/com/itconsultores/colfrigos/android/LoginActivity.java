package com.itconsultores.colfrigos.android;

import java.util.List;
import java.util.TreeSet;

import org.w3c.dom.Document;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.itconsultores.colfrigos.control.Constants;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.XMLParser;
import com.itconsultores.colfrigos.dto.Car;
import com.itconsultores.colfrigos.dto.Movement;

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
			List<Car> carSet = Control.getCarList(doc);
			// Cargar el lsitado de movimientos
			TreeSet<Movement> movementSet = Control.getMovementsSet(doc);

			Log.i(Constants.LOG_DEBUG, "Car Total " + carSet.size());
			Log.i(Constants.LOG_DEBUG, "Movement Total " + movementSet.size());

			Intent intentMenu = new Intent(this, MenuActivity.class);
			startActivity(intentMenu);
		} else if (view.equals(buttonExit)) {
			finish();
		}
	}
}