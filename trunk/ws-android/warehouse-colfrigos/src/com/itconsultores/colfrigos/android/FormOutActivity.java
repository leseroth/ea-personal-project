package com.itconsultores.colfrigos.android;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itconsultores.colfrigos.control.Connector;
import com.itconsultores.colfrigos.control.Constants.MovementType;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.Util;

public class FormOutActivity extends AbstractForm {

	private Button buttonOk;
	private Button buttonBack;
	private TextView positionTextView;
	private String position;

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
			if (Control.freeMovementMenu) {
				goTo(MenuFreeMovementActivity.class);
			} else {
				goTo(MenuActivity.class);
			}
		} else if (view.equals(buttonOk)) {
			if (isInfoComplete()) {
				Class<? extends Activity> nextActivity = Connector.doMovement(
						this, null, 0, position, MovementType.OUT);
				if (nextActivity != null) {
					goTo(nextActivity);
				}
			} else {
				Util.showMessage(this, R.string.label_error,
						"Debe indicar una posición");
			}
		}
	}

	@Override
	protected boolean isInfoComplete() {
		position = positionTextView.getText().toString();
		return position != null && position.trim().length() != 0;
	}
}