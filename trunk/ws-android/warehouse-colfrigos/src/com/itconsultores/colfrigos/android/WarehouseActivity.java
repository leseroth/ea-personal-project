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
import android.widget.Toast;

import com.itconsultores.colfrigos.control.Constants;
import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Constants.StatusColor;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.dto.AbstractCell;
import com.itconsultores.colfrigos.dto.Car;
import com.itconsultores.colfrigos.dto.Movement;
import com.itconsultores.colfrigos.dto.MovementDetail;
import com.itconsultores.colfrigos.dto.Position;

public class WarehouseActivity extends Activity implements OnClickListener {

	private Button buttonConfirm;
	private Button buttonBack;
	private int maxRow = 0;
	private int maxColumn = 0;
	private LinearLayout warehouseGrid;
	private transient Movement currentMovement;
	private String carSide;
	private int carNumber;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_warehouse);

		buttonConfirm = (Button) findViewById(R.id.button_warehouse_confirm);
		buttonConfirm.setOnClickListener(this);

		buttonBack = (Button) findViewById(R.id.button_warehouse_back);
		buttonBack.setOnClickListener(this);

		warehouseGrid = (LinearLayout) findViewById(R.id.warehouse_grid);
		maxRow = warehouseGrid.getChildCount();
		LinearLayout firstRow = (LinearLayout) warehouseGrid.getChildAt(0);
		maxColumn = firstRow.getChildCount();

		Log.i(Constants.LOG_DEBUG, "Max rows : " + maxRow);
		Log.i(Constants.LOG_DEBUG, "Max column : " + maxColumn);
	}

	@Override
	protected void onStart() {
		super.onStart();

		MenuOption selected = Control.getSelectedOption();
		TextView titleText = (TextView) findViewById(R.id.label_warehouse_title);
		TextView subTitleText = (TextView) findViewById(R.id.label_warehouse_subtitle);
		TextView inText = (TextView) findViewById(R.id.warehouseInText);
		TextView inColor = (TextView) findViewById(R.id.warehouseInColor);
		TextView outText = (TextView) findViewById(R.id.warehouseOutText);
		TextView outColor = (TextView) findViewById(R.id.warehouseOutColor);

		// Ajustar el titulo
		titleText.setText(selected.getTextId());

		// Mostrar Ingresar
		if (selected.isMostrarIngresar()) {
			inText.setVisibility(TextView.VISIBLE);
			inColor.setVisibility(TextView.VISIBLE);
			inColor.setBackgroundColor(StatusColor.IN.getColor());
		} else {
			inText.setVisibility(TextView.INVISIBLE);
			inColor.setVisibility(TextView.INVISIBLE);
		}

		// Mostrar Salir
		if (selected.isMostrarSalida()) {
			outText.setVisibility(TextView.VISIBLE);
			outColor.setVisibility(TextView.VISIBLE);
			outColor.setBackgroundColor(StatusColor.OUT.getColor());
		} else {
			outText.setVisibility(TextView.INVISIBLE);
			outColor.setVisibility(TextView.INVISIBLE);
		}

		setWarehouseStatus();

		// Ajustar subtitulo
		Resources res = getResources();
		subTitleText.setText(res.getString(R.string.label_car) + " "
				+ carNumber + " - " + res.getString(R.string.label_side) + " "
				+ carSide);
	}

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
		}
	}

	private void paintCell(AbstractCell ac) {
		TextView text = (TextView) ((LinearLayout) warehouseGrid
				.getChildAt(maxRow - 1 - ac.getRow())).getChildAt(ac
				.getColumn());
		if (text == null) {
			Log.e(Constants.LOG_DEBUG,
					"No se encuentra " + (maxRow - 1 - ac.getRow()) + " "
							+ ac.getColumn());
		} else {
			text.setBackgroundColor(ac.getColor());
		}
	}

	public void onClick(View view) {
		Intent selectedIntent = null;

		// Acciones al confirmar
		if (view.equals(buttonConfirm)) {
			MenuOption nextMenu = Control.confirmMovement(currentMovement);

			if (nextMenu == null) {
				selectedIntent = new Intent(this, MenuActivity.class);
			} else {
				Toast toast = Toast.makeText(this, "Todavia quedan "
						+ Control.getMovementList().size() + " Movimientos",
						Toast.LENGTH_LONG);
				toast.show();

				Control.setSelectedOption(nextMenu);
				selectedIntent = new Intent(this, WarehouseActivity.class);
			}
		}

		else if (view.equals(buttonBack)) {
			if (Control.getMovementList().isEmpty()) {
				selectedIntent = new Intent(this, MenuActivity.class);
			} else {
				Toast toast = Toast.makeText(this,
						"Debe finalizar el movimiento actual",
						Toast.LENGTH_LONG);
				toast.show();
			}
		}

		if (selectedIntent != null) {
			finish();
			startActivity(selectedIntent);
		}
	}
}
