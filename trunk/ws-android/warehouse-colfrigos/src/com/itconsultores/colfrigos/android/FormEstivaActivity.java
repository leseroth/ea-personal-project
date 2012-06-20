package com.itconsultores.colfrigos.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Control;

public class FormEstivaActivity extends Activity implements OnClickListener {

	private Button buttonOk;
	private Button buttonBack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_estiva);

		buttonOk = (Button) findViewById(R.id.f_est_button_confirm);
		buttonBack = (Button) findViewById(R.id.f_est_button_back);
		buttonOk.setOnClickListener(this);
		buttonBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		Intent selectedIntent = null;

		if (view.equals(buttonBack)) {
			selectedIntent = new Intent(this, MenuActivity.class);
		} else if (view.equals(buttonOk)) {
			Control.setSelectedOption(MenuOption.Entrada);
			selectedIntent = new Intent(this, FormInActivity.class);
		}

		if (selectedIntent != null) {
			startActivity(selectedIntent);
		}
	}

}
