package com.vertice.application.movistar.view;

import com.vertice.application.movistar.control.Control;
import com.vertice.application.movistar.control.MovistarSMS;
import com.vertice.application.movistar.util.Constants;
import com.vertice.core.lite.view.CustomCanvas;
import com.vertice.core.lite.view.Footer;
import com.vertice.core.lite.view.FormCanvas;
import com.vertice.core.lite.view.Header;
import com.vertice.core.lite.view.ImageComponent;
import com.vertice.core.lite.view.TextFieldComponent;

public class CSCPanel extends FormCanvas {

    private TextFieldComponent numeroTelefonicoTF, iccidTF, claveVendedorTF,numeroDNI;
    private PlanPseudoPanel planPanel;
    private static int minNumTel=9,maxNumTel=12;
    private static int minNumSC=13,maxNumSC=13;
    private static int minNumCV=6,maxNumCV=6;
    private static int minNumDNI=8,maxNumDNI=8;

    public CSCPanel(CustomCanvas fl,String title){
        super(fl);

        numeroTelefonicoTF = new TextFieldComponent(this,"Numero Telefono", "", maxNumTel, TextFieldComponent.PHONENUMBER);
        iccidTF = new TextFieldComponent(this,"SIM CARD", "", maxNumSC, TextFieldComponent.NUMERIC);
        claveVendedorTF = new TextFieldComponent(this,"Clave vendedor", "", maxNumCV, TextFieldComponent.PASSWORD | TextFieldComponent.NUMERIC);
        numeroDNI = new TextFieldComponent(this,"DNI", "", maxNumDNI, TextFieldComponent.NUMERIC);
        planPanel = new PlanPseudoPanel();

        header=new Header(title);
        append(new ImageComponent(MovistarSMS.logo,ImageComponent.ALIGN_CENTER));
        append(numeroTelefonicoTF);
        append(iccidTF);
        append(claveVendedorTF);
        append(numeroDNI);
        appendCheckGroupComponent(planPanel.getGroup());
        footer=new Footer("Enviar","Atras");

        initalize();
    }

    public void processKey() {
        super.processKey();
        if(planPanel.itemStateChanged())
        { planPanel.processPlan(); }
    }

    public void commandAction(int command) {
        switch (command) {
            case LEFT_SOFTKEY:
                if (validData()) {
                    Control.control.smsmanager.sendTextmessage(Constants.NUMERO_CORTO_CSC, getCSCMessage());
                    planPanel.sendTextMessage();
                }
                break;
            case RIGHT_SOFTKEY:
                run = false;
                fromList.startCanvas();
                Control.control.getDisplay().setCurrent(fromList);
                break;
        }
    }

    private String getCSCMessage() {
        StringBuffer res = new StringBuffer();
        res.append(Constants.LLAVE_ACCESO);
        res.append(Constants.SEPARADOR_CSC); res.append(Constants.TRAMA_CSC);
        res.append(Constants.SEPARADOR_CSC); res.append(numeroTelefonicoTF.getString());
        res.append(Constants.SEPARADOR_CSC); res.append(Constants.ICCID_PREFIX); res.append(iccidTF.getString());
        res.append(Constants.SEPARADOR_CSC); res.append(Constants.CSC_ASTERISCO_MARCADO);
        res.append(Constants.SEPARADOR_CSC); res.append(claveVendedorTF.getString());
        res.append(Constants.SEPARADOR_CSC); res.append(numeroDNI.getString());
        return res.toString();
    }

    private boolean validData() {
        boolean res = true;
        int errors = 0;

        if (numeroTelefonicoTF.getString().length() < minNumTel) {
            errors++;
            Control.control.showAlert("Error", "El numero telefonico debe tener al menos "+minNumTel+" digitos");
        } else if (iccidTF.getString().length() < minNumSC) {
            errors++;
            Control.control.showAlert("Error", "La serie de Sim Card debe tener "+minNumSC+" digitos");
        } else if (claveVendedorTF.getString().length() != minNumCV) {
            errors++;
            Control.control.showAlert("Error", "La clave del vendedor debe tener 6 digitos");
        } else if (numeroDNI.getString().length() != minNumDNI) {
            errors++;
            Control.control.showAlert("Error", "El numero de DNI debe tener 8 digitos");
        }

        if (errors > 0) {
            res = false;
        }
        return res;
    }
}
