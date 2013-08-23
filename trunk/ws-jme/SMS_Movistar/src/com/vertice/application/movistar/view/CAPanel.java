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

public class CAPanel extends FormCanvas {

    private TextFieldComponent numeroTelefonicoTF, claveVendedorTF;

    public CAPanel(CustomCanvas fl,String title){
        super(fl);

        numeroTelefonicoTF = new TextFieldComponent(this,"Numero Telefono", "", 12, TextFieldComponent.PHONENUMBER);
        claveVendedorTF = new TextFieldComponent(this,"Clave vendedor", "", 6, TextFieldComponent.PASSWORD | TextFieldComponent.NUMERIC);

        header=new Header(title);
        append(new ImageComponent(MovistarSMS.logo,ImageComponent.ALIGN_CENTER));
        append(numeroTelefonicoTF);
        append(claveVendedorTF);
        footer=new Footer("Enviar","Atras");

        initalize();
    }

    public void commandAction(int command) {
        switch (command) {
            case LEFT_SOFTKEY:
                if (validData())
                { Control.control.smsmanager.sendTextmessage(Constants.NUMERO_CORTO_CA, getCAMessage()); }
                break;
            case RIGHT_SOFTKEY:
                run = false;
                fromList.startCanvas();
                Control.control.getDisplay().setCurrent(fromList);
                break;
        }
    }

    private String getCAMessage() {
        String res = "";
        res = Constants.LLAVE_ACCESO + Constants.TRAMA_CA + CalendarData.getDate() + CalendarData.getHour();
        res += Formater.getFixedStringFillWithSpacesAtTheEnd(numeroTelefonicoTF.getString(), 12);
        res += Formater.getFixedStringFillWithZerosAtTheEnd(claveVendedorTF.getString(), 16);
        return res;
    }

    private boolean validData() {
        boolean res = true;
        int errors = 0;

        if (numeroTelefonicoTF.getString().length() < 9) {
            errors++;
            Control.control.showAlert("Error", "El n�mero telefonico debe tener al menos 9 digitos");
        } else if (claveVendedorTF.getString().length() != 6) {
            errors++;
            Control.control.showAlert("Error", "La clave del vendedor debe tener 6 digitos");
        }

        if (errors > 0) {
            res = false;
        }
        return res;
    }
}
