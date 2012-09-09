package com.itconsultores.colfrigos.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.itconsultores.colfrigos.control.Connector;
import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.Util;

public class MenuActivity extends Activity implements OnClickListener {

	private Button buttonOpEntrada;
	private Button buttonOpSalida;
	private Button buttonOpPrograma;
	private Button buttonOpSinBalanceo;
	private Button buttonExit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_menu);

		buttonOpEntrada = (Button) findViewById(R.id.sm_button_op_entrada);
		buttonOpSalida = (Button) findViewById(R.id.sm_button_op_salida);
		buttonOpPrograma = (Button) findViewById(R.id.sm_button_op_buscar_programa);
		buttonOpSinBalanceo = (Button) findViewById(R.id.sm_button_op_movimiento_sin_balanceo);
		buttonExit = (Button) findViewById(R.id.sm_button_menu_exit);

		buttonOpEntrada.setOnClickListener(this);
		buttonOpSalida.setOnClickListener(this);
		buttonOpPrograma.setOnClickListener(this);
		buttonOpSinBalanceo.setOnClickListener(this);
		buttonExit.setOnClickListener(this);

		if (Control.message != null) {
			Util.showMessage(this, R.string.label_info, Control.message);
			Control.message = null;
		}
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
		} else if (view.equals(buttonOpPrograma)) {
			String revalidate = Connector.doRevalidate();

			if ("".equals(revalidate)) {
				MenuOption menuOption = Control.getNextMovementMenu();

				if (menuOption == null) {
					Util.showMessage(this, R.string.label_info,
							"No hay nuevas programaciones");
				} else {
					Control.setSelectedOption(menuOption);
					selectedIntent = new Intent(this, WarehouseActivity.class);
				}
			} else {
				Util.showMessage(this, R.string.label_info, revalidate);
			}
		} else if (view.equals(buttonOpSinBalanceo)) {
			selectedIntent = new Intent(this, MenuFreeMovementActivity.class);
		}

		if (selectedIntent != null) {
			finish();
			startActivity(selectedIntent);
		}
	}
}