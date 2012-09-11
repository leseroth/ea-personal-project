package com.itconsultores.colfrigos.control;

import android.app.Activity;
import android.app.AlertDialog;

import com.itconsultores.colfrigos.android.R;

/**
 * Clase de utilidades
 * 
 * @author Erik
 * 
 */
public class Util {

	/**
	 * Permite mostrar un mensaje de informacion en pantalla
	 * 
	 * @param activity
	 *            Actividad que va a mostrar el mensaje
	 * @param title
	 *            Titulo del mensaje
	 * @param message
	 *            Mensaje a mostrar
	 */
	public static void showMessage(Activity activity, int title, String message) {
		AlertDialog.Builder infoAlert = new AlertDialog.Builder(activity);
		infoAlert.setIcon(android.R.drawable.ic_dialog_alert);
		infoAlert.setTitle(title);
		infoAlert.setMessage(message);
		infoAlert.setPositiveButton(R.string.label_ok, null);
		infoAlert.show();
	}
}