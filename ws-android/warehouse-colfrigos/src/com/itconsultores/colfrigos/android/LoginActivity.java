package com.itconsultores.colfrigos.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Control;

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
			String user = userTextView.getText().toString();
			String pass = passTextView.getText().toString();
			String login = Control.doLogin(user, pass);

			if ("".equals(login)) {
				MenuOption menuOption = Control.getNextMovementMenu();
				Intent selectedIntent = null;

				if (menuOption == null) {
					selectedIntent = new Intent(this, MenuActivity.class);
				} else {
					Control.setSelectedOption(menuOption);
					selectedIntent = new Intent(this, WarehouseActivity.class);
				}

				finish();
				startActivity(selectedIntent);
			} else {
				Toast toast = Toast.makeText(this, login, Toast.LENGTH_LONG);
				toast.show();
			}
		} else if (view.equals(buttonExit)) {
			finish();
		}
	}
}