package com.itconsultores.colfrigos.android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Formulario abstracto que sirve de clase padre a todos los formularios de la
 * aplicacion
 * 
 * @author Erik
 * 
 */
public abstract class AbstractForm extends Activity implements OnClickListener {
	protected Resources res;
	protected int viewId;

	/**
	 * Constructor
	 * 
	 * @param viewId
	 *            Id de la vista
	 */
	public AbstractForm(int viewId) {
		this.viewId = viewId;
	}

	/**
	 * Metodo abstracto que permite validar si la informacion del formulario
	 * este completa
	 * 
	 * @return <code>true</code> en caso de que la informacion este completa
	 */
	protected abstract boolean isInfoComplete();

	/**
	 * Método abstracto para inicializar los objetos del formulario
	 */
	protected abstract void initForm();

	/**
	 * Maneja la navegacion
	 * 
	 * @param activity
	 *            Siguiente pantalla a la que se quiere navegar
	 */
	@SuppressWarnings("rawtypes")
	protected void goTo(Class activity) {
		Intent selectedIntent = new Intent(this, activity);
		finish();
		startActivity(selectedIntent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(viewId);
		res = getResources();
		initForm();
	}

	/**
	 * Permite crear un boton
	 * 
	 * @param buttonId
	 *            Identificador de boton
	 * @return Boton creado
	 */
	protected Button initButton(int buttonId) {
		Button button = (Button) findViewById(buttonId);
		button.setOnClickListener(this);
		return button;
	}
}