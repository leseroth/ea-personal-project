package com.itconsultores.colfrigos.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.itconsultores.colfrigos.control.Constants;

public class MenuFreeMovementActivity extends Activity implements
		OnClickListener, OnItemSelectedListener {

	private Spinner carSpinner;
	private Button buttonOpEntradaSinBalanceo;
	private Button buttonOpSalidaSinBalanceo;
	private Button buttonOpBalancear;
	private Button buttonVolver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_menu_sin_balanceo);

		carSpinner = (Spinner) findViewById(R.id.sfm_spinner_car);
		buttonOpEntradaSinBalanceo = (Button) findViewById(R.id.sfm_button_op_entrada);
		buttonOpSalidaSinBalanceo = (Button) findViewById(R.id.sfm_button_op_salida);
		buttonOpBalancear = (Button) findViewById(R.id.sfm_button_op_balancear_carro);
		buttonVolver = (Button) findViewById(R.id.sfm_button_menu_back);

		buttonOpEntradaSinBalanceo.setOnClickListener(this);
		buttonOpSalidaSinBalanceo.setOnClickListener(this);
		buttonOpBalancear.setOnClickListener(this);
		buttonVolver.setOnClickListener(this);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.existent_car_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		carSpinner.setAdapter(adapter);

		// Agregar el listener al final o va a pensar que el valor por defecto
		// fue seleccion
		carSpinner.setOnItemSelectedListener(this);

		setStatus(false);
	}

	private void setStatus(boolean carSelected) {
		carSpinner.setEnabled(!carSelected);
		buttonOpEntradaSinBalanceo.setEnabled(carSelected);
		buttonOpSalidaSinBalanceo.setEnabled(carSelected);
		buttonOpBalancear.setEnabled(carSelected);
		buttonVolver.setEnabled(!carSelected);
	}

	@Override
	public void onClick(View view) {
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		Log.i(Constants.LOG_DEBUG, "Carro Seleccionado " + pos);
		if (pos != 0) {
			setStatus(true);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}