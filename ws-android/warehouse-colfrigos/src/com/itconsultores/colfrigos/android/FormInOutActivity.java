package com.iteconsultores.colfrigos.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.iteconsultores.colfrigos.control.Constants.MenuOption;
import com.iteconsultores.colfrigos.control.Control;

public class FormInOutActivity extends Activity implements OnClickListener {

	private Button buttonOk;
	private Button buttonBack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_entrada_salida);

		buttonOk = (Button) findViewById(R.id.f_inout_button_confirm);
		buttonBack = (Button) findViewById(R.id.f_inout_button_back);
		buttonOk.setOnClickListener(this);
		buttonBack.setOnClickListener(this);
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

}
