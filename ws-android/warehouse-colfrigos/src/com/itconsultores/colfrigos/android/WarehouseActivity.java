package com.itconsultores.colfrigos.android;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itconsultores.colfrigos.control.Constants;
import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Constants.StatusColor;
import com.itconsultores.colfrigos.control.Control;
import com.itconsultores.colfrigos.dto.Car;
import com.itconsultores.colfrigos.dto.Movement;
import com.itconsultores.colfrigos.dto.Position;

public class WarehouseActivity extends Activity implements OnClickListener {

	private Button buttonConfirm;
	private Button buttonBack;
	private int maxRow = 0;
	private int maxColumn = 0;
	private int row = 0;
	private int column = 0;
	private LinearLayout warehouseGrid;
	private transient Movement movement;
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
	}

	@Override
	protected void onStart() {
		super.onStart();

		MenuOption selected = Control.getSelectedOption();
		TextView titleText = (TextView) findViewById(R.id.label_warehouse_title);
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
		} else {
			inText.setVisibility(TextView.INVISIBLE);
			inColor.setVisibility(TextView.INVISIBLE);
		}

		// Mosatrar Salir
		if (selected.isMostrarSalida()) {
			outText.setVisibility(TextView.VISIBLE);
			outColor.setVisibility(TextView.VISIBLE);
		} else {
			outText.setVisibility(TextView.INVISIBLE);
			outColor.setVisibility(TextView.INVISIBLE);
		}

		setWarehouseStatus();
	}

	private void setWarehouseStatus() {
		movement = Control.getMovementList().get(0);
		List<Position> side = null;

		Car car = null;
		carNumber = movement.getMovementDetails().get(0).getCarNumber();
		carSide = movement.getMovementDetails().get(0).getCarSide();

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
			TextView text = (TextView) ((LinearLayout) warehouseGrid
					.getChildAt(p.getRow())).getChildAt(p.getColumn());
			if (text == null) {
				Log.e(Constants.LOG_DEBUG, "No se encuentra " + p.getRow()
						+ " " + p.getColumn());
			} else {
				text.setBackgroundColor(p.isFull() ? Color
						.parseColor(StatusColor.FILLED.getColor()) : Color
						.parseColor(StatusColor.FREE.getColor()));
			}
		}
	}

	public void onClick(View view) {

		// Acciones al confirmar
		if (view.equals(buttonConfirm)) {
			TextView text = (TextView) ((LinearLayout) warehouseGrid
					.getChildAt(row)).getChildAt(column);
			text.setBackgroundColor(Color.GRAY);

			column++;
			if (column >= maxColumn) {
				column = 0;
				row++;
				if (row >= maxRow) {
					row = 0;
				}
			}

		}

		// Regresar
		// TODO Verificar si esta bien que se regrese hasta el menu
		else if (view.equals(buttonBack)) {
			Intent menuIntent = new Intent(this, MenuActivity.class);
			startActivity(menuIntent);
		}
	}
}
