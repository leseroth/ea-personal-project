package com.vertice.application.movistar.control;

import com.vertice.application.movistar.smsmanager.SMSManager;
import com.vertice.core.lite.view.Constants;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;

public class Control {
    private MovistarSMS midlet;
    private Display display;
    public static Control control;
    public SMSManager smsmanager;

    public Control() {
        control = this;
        display = Constants.constants.getDisplay();
        midlet = (MovistarSMS)Constants.constants.getMidlet();
        smsmanager = new SMSManager();
        smsmanager.init();
    }

    public void showAlert(String title, String text) {
        Alert alert = new Alert(title, text, null, AlertType.WARNING);
        alert.setTimeout(Alert.FOREVER);
        display.setCurrent(alert);
    }

    public void exit() {
        midlet.destroyApp(true);
        midlet.notifyDestroyed();
    }

    public Display getDisplay()
    { return display; }
}
