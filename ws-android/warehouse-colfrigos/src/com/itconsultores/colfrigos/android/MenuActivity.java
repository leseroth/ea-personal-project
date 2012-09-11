package com.itconsultores.colfrigos.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.itconsultores.colfrigos.control.Connector;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.Util;

/**
 * Menu principal
 * 
 * @author Erik
 * 
 */
public class MenuActivity extends Activity implements OnClickListener {

	/**
	 * Boton de entrada
	 */
	private Button buttonOpEntrada;
	/**
	 * Boton de salida
	 */
	private Button buttonOpSalida;
	/**
	 * Boton de buscar programacion
	 */
	private Button buttonOpPrograma;
	/**
	 * Boton de realizar movimientos sin balanceo
	 */
	private Button buttonOpSinBalanceo;
	/**
	 * Boton de salida de la aplicacion
	 */
	private Button buttonExit;

	/**
	 * {@inheritDoc}
	 */
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

		Control.freeMovementMenu = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClick(View view) {
		Intent selectedIntent = null;

		if (view.equals(buttonExit)) {
			selectedIntent = new Intent(this, LoginActivity.class);
		} else if (view.equals(buttonOpEntrada)) {
			selectedIntent = new Intent(this, FormInActivity.class);
		} else if (view.equals(buttonOpSalida)) {
			selectedIntent = new Intent(this, FormOutActivity.class);
		} else if (view.equals(buttonOpPrograma)) {
			Class<? extends Activity> nextActivity = Connector.doRevalidate();
			if (nextActivity == this.getClass()) {
				Util.showMessage(this, R.string.label_info, Control.message);
				Control.message = null;
			} else {
				selectedIntent = new Intent(this, nextActivity);
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