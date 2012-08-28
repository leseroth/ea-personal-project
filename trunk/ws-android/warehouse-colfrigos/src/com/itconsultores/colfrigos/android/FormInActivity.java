package com.itconsultores.colfrigos.android;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Constants.MovementType;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.Util;
import com.itconsultores.colfrigos.dto.Client;

public class FormInActivity extends AbstractForm {

	private Button buttonOk;
	private Button buttonBack;

	private TextView weightTextView;
	private Spinner clientSpinner;

	private String[] clientArray;

	public FormInActivity() {
		super(R.layout.form_entrada);
	}

	@Override
	protected void initForm() {
		// Botones
		buttonOk = initButton(R.id.f_in_button_confirm);
		buttonBack = initButton(R.id.f_in_button_back);

		// Peso
		weightTextView = (TextView) findViewById(R.id.f_in_textbox_weight);
		if (Control.getCalculatedWeight() == -1) {
			weightTextView.setText("");
		} else {
			weightTextView.setText("" + Control.getCalculatedWeight());
			Control.resetCalculatedWeight();
		}

		// Clientes
		clientSpinner = (Spinner) findViewById(R.id.f_in_spinner_client);
		clientArray = Control.getClientArray();

		ArrayAdapter<String> clientArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, clientArray);
		clientArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		clientSpinner.setAdapter(clientArrayAdapter);

	}

	@Override
	public void onClick(View view) {

		if (view.equals(buttonBack)) {
			goTo(MenuActivity.class);
		} else if (view.equals(buttonOk)) {
			String weight = weightTextView.getText().toString();

			int clientId = clientSpinner.getSelectedItemPosition();
			if (clientId != 0) {
				Client client = Control.getClientList().get(clientId - 1);
				clientId = client.getId();
			}

			String in = Control.doMovement(weight, clientId, null,
					MovementType.IN);
			if ("".equals(in)) {
				MenuOption menuOption = Control.getNextMovementMenu();

				if (menuOption == null) {
					Util.showMessage(this, R.string.label_info,
							"No se encontraron movimientos");
				} else {
					Control.setSelectedOption(menuOption);
					goTo(WarehouseActivity.class);
				}
			} else {
				Util.showMessage(this, R.string.label_error, in);
			}
		}
	}

	@Override
	protected boolean isInfoComplete() {
		// TODO Validar Informacion
		return true;
	}
}
