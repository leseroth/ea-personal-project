package com.itconsultores.colfrigos.android;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Constants.MovementType;
import com.itconsultores.colfrigos.control.Control;

public class FormOutActivity extends AbstractForm {

	private Button buttonOk;
	private Button buttonBack;
	private TextView positionTextView;

	public FormOutActivity() {
		super(R.layout.form_salida);
	}

	@Override
	protected void initForm() {
		buttonOk = initButton(R.id.f_out_button_confirm);
		buttonBack = initButton(R.id.f_out_button_back);

		// Formulario
		positionTextView = (TextView) findViewById(R.id.f_out_textbox_position);
	}

	@Override
	public void onClick(View view) {

		if (view.equals(buttonBack)) {
			goTo(MenuActivity.class);
		} else if (view.equals(buttonOk)) {

			String position = positionTextView.getText().toString();
			String out = Control
					.doMovement(null, 0, position, MovementType.OUT);
			if ("".equals(out)) {
				MenuOption menuOption = Control.getNextMovementMenu();

				if (menuOption == null) {
					Intent intentMenu = new Intent(this, MenuActivity.class);
					startActivity(intentMenu);
				} else {
					Control.setSelectedOption(menuOption);
					goTo(WarehouseActivity.class);
				}
			} else {
				Toast toast = Toast.makeText(this, out, Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}

	@Override
	protected boolean isInfoComplete() {
		// TODO Validar Informacion
		return true;
	}
}
