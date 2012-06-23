package com.itconsultores.colfrigos.android;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Control;

public class FormInOutActivity extends AbstractForm {

	private Button buttonOk;
	private Button buttonBack;

	public FormInOutActivity() {
		super(R.layout.form_entrada_salida);
	}

	@Override
	protected void initForm() {
		buttonOk = initButton(R.id.f_inout_button_confirm);
		buttonBack = initButton(R.id.f_inout_button_back);
	}

	@Override
	public void onClick(View view) {
		Intent selectedIntent = null;

		if (view.equals(buttonBack)) {
			selectedIntent = new Intent(this, MenuActivity.class);
		} else if (view.equals(buttonOk)) {
			Control.setSelectedOption(MenuOption.EntradaSalida);
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
