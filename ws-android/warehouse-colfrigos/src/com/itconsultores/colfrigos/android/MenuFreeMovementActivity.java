package com.itconsultores.colfrigos.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.itconsultores.colfrigos.control.Connector;
import com.itconsultores.colfrigos.control.Constants;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.Util;

public class MenuFreeMovementActivity extends Activity implements
		OnClickListener, OnItemSelectedListener {

	private Spinner carSpinner;
	private Button buttonOpEntradaSinBalanceo;
	private Button buttonOpSalidaSinBalanceo;
	private Button buttonOpBalancear;
	private Button buttonBack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_menu_sin_balanceo);

		carSpinner = (Spinner) findViewById(R.id.sfm_spinner_car);
		buttonOpEntradaSinBalanceo = (Button) findViewById(R.id.sfm_button_op_entrada);
		buttonOpSalidaSinBalanceo = (Button) findViewById(R.id.sfm_button_op_salida);
		buttonOpBalancear = (Button) findViewById(R.id.sfm_button_op_balancear_carro);
		buttonBack = (Button) findViewById(R.id.sfm_button_menu_back);

		buttonOpEntradaSinBalanceo.setOnClickListener(this);
		buttonOpSalidaSinBalanceo.setOnClickListener(this);
		buttonOpBalancear.setOnClickListener(this);
		buttonBack.setOnClickListener(this);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.existent_car_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		carSpinner.setAdapter(adapter);
		carSpinner.setSelection(Control.carSelected);

		// Agregar el listener al final o va a pensar que el valor por defecto
		// fue seleccion
		carSpinner.setOnItemSelectedListener(this);
		setMenuStatus();

		if (Control.message != null) {
			Util.showMessage(this, R.string.label_info, Control.message);
			Control.message = null;
		}

		Control.freeMovementMenu = true;
	}

	private void setMenuStatus() {
		carSpinner.setEnabled(Control.carSelected == 0
				|| !Control.freeMovementStarted);
		buttonOpEntradaSinBalanceo.setEnabled(Control.carSelected != 0);
		buttonOpSalidaSinBalanceo.setEnabled(Control.carSelected != 0);
		buttonOpBalancear.setEnabled(Control.carSelected != 0);
		buttonBack.setEnabled(Control.carSelected == 0
				|| !Control.freeMovementStarted);
	}

	@Override
	public void onClick(View view) {
		Intent selectedIntent = null;

		if (view.equals(buttonBack)) {
			selectedIntent = new Intent(this, MenuActivity.class);
		} else if (view.equals(buttonOpEntradaSinBalanceo)) {
			selectedIntent = new Intent(this, FormInActivity.class);
		} else if (view.equals(buttonOpSalidaSinBalanceo)) {
			selectedIntent = new Intent(this, FormOutActivity.class);
		} else if (view.equals(buttonOpBalancear)) {
			Class<? extends Activity> nextActivity = Connector.doRevalidate();
			if (nextActivity == this.getClass()) {
				Util.showMessage(this, R.string.label_info, Control.message);
				Control.message = null;
			} else {
				selectedIntent = new Intent(this, nextActivity);
			}
		}

		if (selectedIntent != null) {
			finish();
			startActivity(selectedIntent);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		Log.i(Constants.LOG_DEBUG, "Carro Seleccionado " + pos);
		Control.carSelected = pos;
		setMenuStatus();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}