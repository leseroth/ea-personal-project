package com.itconsultores.colfrigos.android;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.Util;

public class LoginActivity extends AbstractForm {

	private String user;
	private String pass;

	private Button buttonLogin;
	private Button buttonExit;

	private TextView userTextView;
	private TextView passTextView;

	public LoginActivity() {
		super(R.layout.screen_login);
	}

	@Override
	protected void initForm() {
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
	public void onClick(View view) {

		if (view.equals(buttonLogin)) {
			if (isInfoComplete()) {
				String login = Control.doLogin(user, pass);

				if ("".equals(login)) {
					MenuOption menuOption = Control.getNextMovementMenu();

					if (menuOption == null) {
						goTo(MenuActivity.class);
					} else {
						Control.setSelectedOption(menuOption);
						goTo(WarehouseActivity.class);
					}

				} else {
					Util.showMessage(this, R.string.label_login, login);
				}

			} else {
				Util.showMessage(this, R.string.label_login,
						"Debe ingresar datos");
			}
		} else if (view.equals(buttonExit)) {
			finish();
		}
	}

	@Override
	protected boolean isInfoComplete() {
		user = userTextView.getText().toString();
		pass = passTextView.getText().toString();
		return user != null && pass != null && user.length() > 0
				&& pass.length() > 0;
	}
}