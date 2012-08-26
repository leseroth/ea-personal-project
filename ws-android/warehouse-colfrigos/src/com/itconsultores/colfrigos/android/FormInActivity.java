package com.itconsultores.colfrigos.android;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
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
		if (Control.calculatedWeight == -1) {
			weightTextView.setText("");
		} else {
			weightTextView.setText("" + Control.calculatedWeight);
			Control.calculatedWeight = -1;
		}

		// Clientes
		clientSpinner = (Spinner) findViewById(R.id.f_in_spinner_client);
		clientArray = new String[] { "Selecione Cliente", "Cliente 1",
				"Cliente A", "Cliente Z" };

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
			Control.setSelectedOption(MenuOption.Entrada);
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
