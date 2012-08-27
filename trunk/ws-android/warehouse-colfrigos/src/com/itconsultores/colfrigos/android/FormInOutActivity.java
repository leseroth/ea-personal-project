package com.itconsultores.colfrigos.android;

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
		if (view.equals(buttonBack)) {
			goTo(MenuActivity.class);
		} else if (view.equals(buttonOk)) {
			Control.setSelectedOption(MenuOption.EntradaSalida);
			goTo(WarehouseActivity.class);
		}
	}

	@Override
	protected boolean isInfoComplete() {
		// TODO Validar Informacion
		return true;
	}
}
