package com.itconsultores.colfrigos.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Control;

public class MenuActivity extends Activity implements OnClickListener {

	private Button buttonOpEntrada;
	private Button buttonOpSalida;
	private Button buttonOpEstiva;
	private Button buttonExit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_menu);

		buttonOpEntrada = (Button) findViewById(R.id.sm_button_op_entrada);
		buttonOpSalida = (Button) findViewById(R.id.sm_button_op_salida);
		buttonOpEstiva = (Button) findViewById(R.id.sm_button_op_estiva);
		buttonExit = (Button) findViewById(R.id.sm_button_menu_exit);

		buttonOpEntrada.setOnClickListener(this);
		buttonOpSalida.setOnClickListener(this);
		buttonOpEstiva.setOnClickListener(this);
		buttonExit.setOnClickListener(this);
	}

	public void onClick(View view) {
		Intent selectedIntent = null;

		if (view.equals(buttonExit)) {
			selectedIntent = new Intent(this, LoginActivity.class);
		} else if (view.equals(buttonOpEntrada)) {
			selectedIntent = new Intent(this, FormInActivity.class);
		} else if (view.equals(buttonOpSalida)) {
			Control.setSelectedOption(MenuOption.Salida);
			selectedIntent = new Intent(this, FormOutActivity.class);
		} else if (view.equals(buttonOpEstiva)) {
			selectedIntent = new Intent(this, FormEstivaActivity.class);
		}

		if (selectedIntent != null) {
			finish();
			startActivity(selectedIntent);
		}
	}
}
