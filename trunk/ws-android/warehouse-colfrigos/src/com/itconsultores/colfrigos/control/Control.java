package com.iteconsultores.colfrigos.control;

import com.iteconsultores.colfrigos.control.Constants.MenuOption;

public class Control {
	private static MenuOption selectedOption;

	public static MenuOption getSelectedOption() {
		return selectedOption;
	}

	public static void setSelectedOption(MenuOption selected) {
		selectedOption = selected;
	}

}
