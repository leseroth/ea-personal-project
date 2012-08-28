package com.itconsultores.colfrigos.control;

import android.app.Activity;
import android.app.AlertDialog;

import com.itconsultores.colfrigos.android.R;

public class Util {

	public static void showMessage(Activity activity, int title, String message) {
		AlertDialog.Builder infoAlert = new AlertDialog.Builder(activity);
		infoAlert.setIcon(android.R.drawable.ic_dialog_alert);
		infoAlert.setTitle(title);
		infoAlert.setMessage(message);
		infoAlert.setPositiveButton(R.string.label_yes, null);
		infoAlert.show();
	}
}
