/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vertice.application.movistar.control;

import com.vertice.application.movistar.view.ListPanel;
import com.vertice.core.lite.view.Constants;
import java.io.IOException;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.*;

/**
 * @author Erik
 */
public class MovistarSMS extends MIDlet {
    ListPanel lp;
    public static Image logo;

    public void startApp() {
        try { logo = Image.createImage("/movistar.png"); }
        catch (IOException ex) { ex.printStackTrace(); }

        Constants constants=new Constants();
        constants.setMidlet(this);
        constants.setDisplay(Display.getDisplay(this));
        Constants.trace=false;
        new Control();

        lp=new ListPanel();
        Constants.constants.getDisplay().setCurrent(lp);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}
