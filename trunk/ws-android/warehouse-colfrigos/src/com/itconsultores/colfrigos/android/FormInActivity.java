package com.itconsultores.colfrigos.android;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.itconsultores.colfrigos.control.Connector;
import com.itconsultores.colfrigos.control.Constants.MovementType;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.Util;
import com.itconsultores.colfrigos.dto.Client;

public class FormInActivity extends AbstractForm {

	private Button buttonOk;
	private Button buttonBack;

	private TextView weightTextView;
	private String weight;
	private Spinner clientSpinner;
	private int clientId;
	private TextView positionTextView;
	private String position;

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

		// Posicion
		positionTextView = (TextView) findViewById(R.id.f_in_textbox_position);
		TextView positionLabel = (TextView) findViewById(R.id.f_in_label_position);

		// Solo mostrar posicion si es movimiento libre
		int visibility = Control.freeMovementMenu ? TextView.VISIBLE
				: TextView.INVISIBLE;
		positionLabel.setVisibility(visibility);
		positionTextView.setVisibility(visibility);

		ArrayAdapter<String> clientArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, clientArray);
		clientArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		clientSpinner.setAdapter(clientArrayAdapter);
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
				boolean goToWarehouse = Connector.doMovement(this, weight,
						clientId, position, MovementType.IN);
				if (goToWarehouse) {
					goTo(WarehouseActivity.class);
				} else if (Control.freeMovementMenu) {
					goTo(MenuFreeMovementActivity.class);
				}
			} else {
				Util.showMessage(this, R.string.label_error,
						"Hace falta informacion");
			}
		}
	}

	@Override
	protected boolean isInfoComplete() {
		weight = weightTextView.getText().toString();
		position = positionTextView.getText().toString();
		clientSpinner.getSelectedItemPosition();
		if (clientId != 0) {
			Client client = Control.getClientList().get(clientId - 1);
			clientId = client.getId();
		}
		if (!Control.freeMovementMenu) {
			position = "0";
		}

		return weight != null && weight.trim().length() != 0
				&& position != null && position.trim().length() != 0;
	}
}