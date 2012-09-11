package com.itconsultores.colfrigos.android;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itconsultores.colfrigos.control.Connector;
import com.itconsultores.colfrigos.control.Constants;
import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.control.Util;
import com.itconsultores.colfrigos.dto.AbstractCell;
import com.itconsultores.colfrigos.dto.Car;
import com.itconsultores.colfrigos.dto.Movement;
import com.itconsultores.colfrigos.dto.MovementDetail;
import com.itconsultores.colfrigos.dto.Position;

/**
 * Pantalla de la bodega
 * 
 * @author Erik
 * 
 */
public class WarehouseActivity extends Activity implements OnClickListener {

	/**
	 * Boton confirmar
	 */
	private Button buttonConfirm;
	/**
	 * Boton revalidar
	 */
	private Button buttonRevalidate;
	/**
	 * Maximo numero de filas
	 */
	private int maxRow = 0;
	/**
	 * Maximo numero de filas
	 */
	private int maxColumn = 0;
	/**
	 * Grilla de la bodega
	 */
	private LinearLayout warehouseGrid;
	/**
	 * Movimiento que esta siendo ejecutado actualmente
	 */
	private transient Movement currentMovement;
	/**
	 * Lado del carro
	 */
	private String carSide;
	/**
	 * Numero del carro
	 */
	private int carNumber;
	/**
	 * Color de entrada, se usa para poder pintar la etiqueta
	 */
	private TextView inColor;
	/**
	 * Color de salida, se usa para poder pintar la etiqueta
	 */
	private TextView outColor;
	/**
	 * Referncia al objeto {@link Resources}
	 */
	private Resources res;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_warehouse);

		buttonConfirm = (Button) findViewById(R.id.button_warehouse_confirm);
		buttonConfirm.setOnClickListener(this);

		buttonRevalidate = (Button) findViewById(R.id.button_warehouse_revalidate);
		buttonRevalidate.setOnClickListener(this);

		warehouseGrid = (LinearLayout) findViewById(R.id.warehouse_grid);
		maxRow = warehouseGrid.getChildCount();
		LinearLayout firstRow = (LinearLayout) warehouseGrid.getChildAt(0);
		maxColumn = firstRow.getChildCount();

		inColor = (TextView) findViewById(R.id.warehouseInColor);
		outColor = (TextView) findViewById(R.id.warehouseOutColor);

		Log.i(Constants.LOG_DEBUG, "Max rows : " + maxRow);
		Log.i(Constants.LOG_DEBUG, "Max column : " + maxColumn);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onStart() {
		super.onStart();

		MenuOption selected = Control.getSelectedOption();
		TextView titleText = (TextView) findViewById(R.id.label_warehouse_title);
		TextView subTitleText = (TextView) findViewById(R.id.label_warehouse_subtitle);

		// Ajustar el titulo
		titleText.setText(selected.getTextId());

		// Pintar la bodega
		setWarehouseStatus();

		// Ajustar subtitulo
		res = getResources();
		subTitleText.setText(res.getString(R.string.label_car) + " "
				+ carNumber + " - " + res.getString(R.string.label_side) + " "
				+ carSide + " - Peso: "
				+ currentMovement.getMovementDetails().get(0).getWeight()
				+ " Kg");

		Util.showMessage(this, R.string.label_info, "Debe realizar "
				+ Control.getMovementList().size() + " Movimientos");
	}

	/**
	 * Pinta las etiquetas en pantalla, como lo son el numero del carro, el
	 * lado, el peso, etc.
	 */
	private void setWarehouseStatus() {
		currentMovement = Control.getMovementList().get(0);
		List<Position> side = null;

		Car car = null;
		carNumber = currentMovement.getMovementDetails().get(0).getCarNumber();
		carSide = currentMovement.getMovementDetails().get(0).getCarSide();

		carLoop: for (Car c : Control.getCarList()) {
			if (c.getNumber() == carNumber) {
				car = c;
				break carLoop;
			}
		}

		if ("A".equals(carSide)) {
			side = car.getSideA();
		} else if ("B".equals(carSide)) {
			side = car.getSideB();
		}

		for (Position p : side) {
			paintCell(p);
		}

		for (MovementDetail md : currentMovement.getMovementDetails()) {
			paintCell(md);

			switch (md.getMovementType()) {
			case IN:
				inColor.setText(md.getLabel());
				break;
			case OUT:
				outColor.setText(md.getLabel());
				break;
			}
		}
	}

	/**
	 * Pinta una celda con el color determinado, por defecto todas las celdas
	 * estan vacias, por tanto todas las posiciones que no se reciban se
	 * consideran vacias
	 * 
	 * @param ac
	 */
	private void paintCell(AbstractCell ac) {
		TextView text = (TextView) ((LinearLayout) warehouseGrid
				.getChildAt(maxRow - 1 - ac.getRow())).getChildAt(ac
				.getColumn());
		if (text == null) {
			Log.e(Constants.LOG_DEBUG,
					"No se encuentra " + (maxRow - 1 - ac.getRow()) + " "
							+ ac.getColumn());
		} else {
			text.setBackgroundResource(ac.getColor());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onClick(View view) {
		Intent selectedIntent = null;

		// Acciones al confirmar
		if (view.equals(buttonConfirm)) {
			try {
				MenuOption nextMenu = Connector
						.confirmMovement(currentMovement);

				if (nextMenu == null) {
					Control.message = "Programación finalizada";
					Control.carSelected = 0;
					Control.freeMovementStarted = false;
					if (Control.freeMovementMenu) {
						selectedIntent = new Intent(this,
								MenuFreeMovementActivity.class);
					} else {
						selectedIntent = new Intent(this, MenuActivity.class);
					}
				} else {
					Control.setSelectedOption(nextMenu);
					selectedIntent = new Intent(this, WarehouseActivity.class);
				}
			} catch (Exception e) {
				selectedIntent = new Intent(this, MenuActivity.class);
			}
		}

		else if (view.equals(buttonRevalidate)) {
			Class<? extends Activity> nextActivity = Connector.doRevalidate();
			selectedIntent = new Intent(this, nextActivity);
		}

		if (selectedIntent != null) {
			finish();
			startActivity(selectedIntent);
		}
	}
}