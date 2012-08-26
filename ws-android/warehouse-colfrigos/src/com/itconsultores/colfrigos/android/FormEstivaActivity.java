package com.itconsultores.colfrigos.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itconsultores.colfrigos.control.Constants.MenuOption;
import com.itconsultores.colfrigos.control.Control;

public class FormEstivaActivity extends AbstractForm {

	private Button buttonOk;
	private Button buttonBack;

	private TextView basketTextView;
	private TextView boxTextView;

	public FormEstivaActivity() {
		super(R.layout.form_estiva);
	}

	@Override
	protected void initForm() {
		// Botones
		buttonOk = initButton(R.id.f_est_button_confirm);
		buttonBack = initButton(R.id.f_est_button_back);

		// Formulario
		basketTextView = (TextView) findViewById(R.id.f_est_textbox_basket);
		boxTextView = (TextView) findViewById(R.id.f_est_textbox_box);
	}

	@Override
	public void onClick(View view) {
		if (view.equals(buttonBack)) {
			goTo(MenuActivity.class);
		} else if (view.equals(buttonOk)) {
			if (isInfoComplete()) {
				Control.setCalculatedWeight(
						Integer.parseInt(basketTextView.getText().toString()),
						Integer.parseInt(boxTextView.getText().toString()));

				AlertDialog.Builder confirmWeight = new AlertDialog.Builder(
						this);
				confirmWeight.setIcon(android.R.drawable.ic_dialog_alert);
				confirmWeight.setTitle(R.string.label_screen_application);
				confirmWeight.setMessage("Total: "
						+ Control.getCalculatedWeight() + ", "
						+ res.getString(R.string.label_ask_entrada));
				confirmWeight.setPositiveButton(R.string.label_yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Control.setSelectedOption(MenuOption.Entrada);
								goTo(FormInActivity.class);
							}
						});
				confirmWeight.setNegativeButton(R.string.label_no, null);
				confirmWeight.show();
			}
		}
	}

	@Override
	protected boolean isInfoComplete() {
		return basketTextView.getText().toString().length() != 0
				&& boxTextView.getText().toString().length() != 0;
	}
}
