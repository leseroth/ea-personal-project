package com.iteconsultores.colfrigos.android;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WarehouseActivity extends Activity implements OnClickListener {

	private Button buttonConfirm;
	private int maxRow = 0;
	private int maxColumn = 0;
	private int row = 0;
	private int column = 0;
	private LinearLayout warehouseGrid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_warehouse);

		buttonConfirm = (Button) findViewById(R.id.button_warehouse_confirm);
		buttonConfirm.setOnClickListener(this);

		warehouseGrid = (LinearLayout) findViewById(R.id.warehouse_grid);
		maxRow = warehouseGrid.getChildCount();
		LinearLayout firstRow = (LinearLayout) warehouseGrid.getChildAt(0);
		maxColumn = firstRow.getChildCount();
	}

	public void onClick(View view) {
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

	}
}
