package com.vertice.core.lite.view;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class Constants {
    public static final long sleepTime=150;

    public static Constants constants;
    public static boolean trace;

    private MIDlet midlet;
    private Display display;

    public Constants() { constants=this; }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public static void setTrace(boolean trace) {
        Constants.trace = trace;
    }

    public MIDlet getMidlet() {
        return midlet;
    }

    public void setMidlet(MIDlet midlet) {
        this.midlet = midlet;
    }

    
}
