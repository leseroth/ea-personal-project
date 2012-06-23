package com.itconsultores.colfrigos.control;

import com.itconsultores.colfrigos.control.Constants.MenuOption;

public class Control {
	private static MenuOption selectedOption;
	public static int calculatedWeight = -1;

	public static MenuOption getSelectedOption() {
		return selectedOption;
	}

	public static void setSelectedOption(MenuOption selected) {
		selectedOption = selected;
	}

}
