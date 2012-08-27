package com.itconsultores.colfrigos.android;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Constants.MovementType;
import com.itconsultores.colfrigos.control.Control;

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
		Intent selectedIntent = null;

		if (view.equals(buttonBack)) {
			selectedIntent = new Intent(this, MenuActivity.class);
		} else if (view.equals(buttonOk)) {
			String weight = weightTextView.getText().toString();
			Long clientId = clientSpinner.getSelectedItemId();
			String in = Control.doMovement(weight, clientId, null,MovementType.IN);
			if ("".equals(in)) {
				MenuOption menuOption = Control.getNextMovementMenu();

				if (menuOption == null) {
					Intent intentMenu = new Intent(this, MenuActivity.class);
					startActivity(intentMenu);
				} else {
					Control.setSelectedOption(menuOption);
					 selectedIntent = new Intent(this,
							WarehouseActivity.class);
				}
			} else {
				Toast toast = Toast.makeText(this, in, Toast.LENGTH_LONG);
				toast.show();
			}

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
