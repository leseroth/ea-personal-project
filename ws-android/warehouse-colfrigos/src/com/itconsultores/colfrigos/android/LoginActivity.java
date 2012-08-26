package com.itconsultores.colfrigos.android;

import org.w3c.dom.Document;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itconsultores.colfrigos.control.Constants;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.XMLParser;

public class LoginActivity extends Activity implements OnClickListener {

	private Button buttonLogin;
	private Button buttonExit;

	private TextView userTextView;
	private TextView passTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_login);

		// Botones
		buttonLogin = (Button) findViewById(R.id.sl_button_login);
		buttonExit = (Button) findViewById(R.id.sl_button_exit);

		buttonLogin.setOnClickListener(this);
		buttonExit.setOnClickListener(this);

		// Formulario
		userTextView = (TextView) findViewById(R.id.sl_textbox_username);
		passTextView = (TextView) findViewById(R.id.sl_textbox_password);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onClick(View view) {

		if (view.equals(buttonLogin)) {
			String xml = XMLParser.getXMLFromUrl(Constants.URL);
			Document doc = XMLParser.XMLfromString(xml);

			String user = userTextView.getText().toString();
			String pass = passTextView.getText().toString();
			String login = Control.doLogin(doc, user, pass);

			if ("".equals(login)) {
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

				Intent intentMenu = new Intent(this, MenuActivity.class);
				startActivity(intentMenu);
			} else {
				Toast toast = Toast.makeText(this, login, Toast.LENGTH_LONG);
				toast.show();
			}
		} else if (view.equals(buttonExit)) {
			finish();
		}
	}
}