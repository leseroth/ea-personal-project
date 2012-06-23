package com.itconsultores.colfrigos.android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;

public abstract class AbstractForm extends Activity implements OnClickListener {
	protected Resources res;
	protected int viewId;

	public AbstractForm(int viewId) {
		this.viewId = viewId;
	}

	protected abstract boolean isInfoComplete();

	protected abstract void initForm();

	@SuppressWarnings("rawtypes")
	protected void goTo(Class activity) {
		Intent selectedIntent = new Intent(this, activity);
		startActivity(selectedIntent);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(viewId);
		res = getResources();
		initForm();
	}

	protected Button initButton(int buttonId) {
		Button button = (Button) findViewById(buttonId);
		button.setOnClickListener(this);
		return button;
	}
}
