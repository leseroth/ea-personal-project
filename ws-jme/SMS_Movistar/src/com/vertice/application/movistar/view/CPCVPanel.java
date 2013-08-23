package com.vertice.application.movistar.view;

import com.vertice.application.movistar.control.Control;
import com.vertice.application.movistar.control.MovistarSMS;
import com.vertice.application.movistar.util.CalendarData;
import com.vertice.application.movistar.util.Constants;
import com.vertice.application.movistar.util.Formater;
import com.vertice.core.lite.view.CustomCanvas;
import com.vertice.core.lite.view.Footer;
import com.vertice.core.lite.view.FormCanvas;
import com.vertice.core.lite.view.Header;
import com.vertice.core.lite.view.ImageComponent;
import com.vertice.core.lite.view.TextFieldComponent;

public class CPCVPanel extends FormCanvas {

    private TextFieldComponent numeroTelefonicoTF;

    public CPCVPanel(CustomCanvas fl,String title){
        super(fl);

        numeroTelefonicoTF = new TextFieldComponent(this,"Numero telefonico", "", 12, TextFieldComponent.PHONENUMBER);

        header = new Header(title);
        append(new ImageComponent(MovistarSMS.logo,ImageComponent.ALIGN_CENTER));
        append(numeroTelefonicoTF);
        footer = new Footer("Enviar", "Atras");

        initalize();
    }

    public void commandAction(int command) {
        switch (command) {
            case LEFT_SOFTKEY:
                if (validData())
                { Control.control.smsmanager.sendTextmessage(Constants.NUMERO_CORTO_CPCV, getCPCVMessage()); }
                break;
            case RIGHT_SOFTKEY:
                run = false;
                fromList.startCanvas();
                Control.control.getDisplay().setCurrent(fromList);
                break;
        }
    }

    private String getCPCVMessage() {
        String res = "";

        res = Constants.LLAVE_ACCESO + Constants.TRAMA_CPCV + CalendarData.getDate() + CalendarData.getHour() + Formater.getFixedStringFillWithSpacesAtTheEnd(numeroTelefonicoTF.getString(), 12);
        return res;
    }

    private boolean validData() {
        boolean res = true;
        int errors = 0;
        if (numeroTelefonicoTF.getString().length() < 9) {
            errors++;
            Control.control.showAlert("Error", "El numero telefonico debe tener al menos 9 digitos");
        }

        if (errors > 0) {
            res = false;
        }
        return res;
    }
}
