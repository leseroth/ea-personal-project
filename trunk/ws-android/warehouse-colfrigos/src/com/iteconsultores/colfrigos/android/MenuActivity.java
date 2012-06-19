package com.iteconsultores.colfrigos.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity implements OnClickListener {

	private Button buttonOpEntrada;
	private Button buttonOpSalida;
	private Button buttonOpEntradaSalida;
	private Button buttonOpEstiva;
	private Button buttonExit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_menu);

		buttonOpEntrada = (Button) findViewById(R.id.sm_button_op_entrada);
		buttonOpSalida = (Button) findViewById(R.id.sm_button_op_salida);
		buttonOpEntradaSalida = (Button) findViewById(R.id.sm_button_op_entrada_salida);
		buttonOpEstiva = (Button) findViewById(R.id.sm_button_op_estiva);
		buttonExit = (Button) findViewById(R.id.sm_button_menu_exit);

		buttonOpEntrada.setOnClickListener(this);
		buttonOpSalida.setOnClickListener(this);
		buttonOpEntradaSalida.setOnClickListener(this);
		buttonOpEstiva.setOnClickListener(this);
		buttonExit.setOnClickListener(this);
	}

	public void onClick(View view) {

		if (view.equals(buttonExit)) {
			finish();
		} else {
			Intent intentMenu = new Intent(this, WarehouseActivity.class);
			startActivity(intentMenu);
		}
	}

}
