package com.itconsultores.colfrigos.android;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Constants.MovementType;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.Util;

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
					Util.showMessage(this, R.string.label_info,
							"No se encontraron movimientos");
				} else {
					Control.setSelectedOption(menuOption);
					goTo(WarehouseActivity.class);
				}
			} else {
				Util.showMessage(this, R.string.label_error, out);
			}
		}
	}

	@Override
	protected boolean isInfoComplete() {
		// TODO Validar Informacion
		return true;
	}
}
