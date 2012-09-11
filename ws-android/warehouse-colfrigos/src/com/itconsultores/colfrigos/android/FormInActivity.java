package com.itconsultores.colfrigos.android;

import android.app.Activity;
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

/**
 * Pantalla de entrada
 * 
 * @author Erik
 * 
 */
public class FormInActivity extends AbstractForm {

	/**
	 * Boton ok
	 */
	private Button buttonOk;
	/**
	 * Boton volver
	 */
	private Button buttonBack;

	/**
	 * Campo de texto del peso
	 */
	private TextView weightTextView;
	/**
	 * Peso
	 */
	private String weight;
	/**
	 * Listado de clientes
	 */
	private Spinner clientSpinner;
	/**
	 * Id del cliente seleccionado
	 */
	private int clientId;
	/**
	 * Campo de texto de la posicion
	 */
	private TextView positionTextView;
	/**
	 * Posicion
	 */
	private String position;

	/**
	 * Arreglo de nombres de clientes para ser mostrados en pantalla
	 */
	private String[] clientArray;

	/**
	 * Constructor
	 */
	public FormInActivity() {
		super(R.layout.form_entrada);
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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
				Class<? extends Activity> nextActivity = Connector.doMovement(
						this, weight, clientId, position, MovementType.IN);
				if (nextActivity != null) {
					goTo(nextActivity);
				}
			} else {
				Util.showMessage(this, R.string.label_error,
						"Hace falta informacion");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isInfoComplete() {
		weight = weightTextView.getText().toString();
		position = positionTextView.getText().toString();
		clientId = clientSpinner.getSelectedItemPosition();
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