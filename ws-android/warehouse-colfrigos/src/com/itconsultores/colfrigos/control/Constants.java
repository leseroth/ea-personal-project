package com.itconsultores.colfrigos.control;

import com.itconsultores.colfrigos.android.R;

public class Constants {
	public static final String URL = "http://ea-personal-project.googlecode.com/svn/trunk/ws-android/movimiento.html?format=xml";

	public enum MenuOption {
		Entrada(R.string.label_op_entrada, true, false), //
		Salida(R.string.label_op_salida, false, true), //
		EntradaSalida(R.string.label_op_entrada_salida), //
		Estiva(R.string.label_op_estiva); //

		private int textId;
		private boolean mostrarIngresar;
		private boolean mostrarSalida;

		private MenuOption(int text) {
			textId = text;
			mostrarIngresar = true;
			mostrarSalida = true;
		}

		private MenuOption(int text, boolean ing, boolean sal) {
			textId = text;
			mostrarIngresar = ing;
			mostrarSalida = sal;
		}

		public boolean isMostrarIngresar() {
			return mostrarIngresar;
		}

		public boolean isMostrarSalida() {
			return mostrarSalida;
		}

		public int getTextId() {
			return textId;
		}

	}
}
