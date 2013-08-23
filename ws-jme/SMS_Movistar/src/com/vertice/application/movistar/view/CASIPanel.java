package com.vertice.application.movistar.view;

import com.vertice.application.movistar.control.Control;
import com.vertice.application.movistar.control.MovistarSMS;
import com.vertice.application.movistar.util.CalendarData;
import com.vertice.application.movistar.util.Constants;
import com.vertice.application.movistar.util.Formater;
import com.vertice.core.lite.view.CheckGroupComponent;
import com.vertice.core.lite.view.CheckItemComponent;
import com.vertice.core.lite.view.CustomCanvas;
import com.vertice.core.lite.view.Footer;
import com.vertice.core.lite.view.FormCanvas;
import com.vertice.core.lite.view.Header;
import com.vertice.core.lite.view.ImageComponent;
import com.vertice.core.lite.view.LabelComponent;
import com.vertice.core.lite.view.TextFieldComponent;

public class CASIPanel extends FormCanvas {

    private TextFieldComponent claveVendedorTF, numeroTelefonicoTF, imeiTF, iccidTF, claveClienteTF, telefonoVendedorTF, numeroDNITF;
    private CheckItemComponent planConsumoCG,planConsumoCG2,cortePorRoboCI,flagBanditCI;
    private CheckGroupComponent group;
    private PlanPseudoPanel planPanel;

    public CASIPanel(CustomCanvas fl,String title){
        super(fl);

        numeroTelefonicoTF = new TextFieldComponent(this,"Numero Telefono", "", 9, TextFieldComponent.PHONENUMBER);
        imeiTF = new TextFieldComponent(this,"IMEI", "", 15, TextFieldComponent.NUMERIC);
        iccidTF = new TextFieldComponent(this,"SIM CARD", "", 19, TextFieldComponent.NUMERIC);
        cortePorRoboCI = new CheckItemComponent("Corte por robo");
        claveClienteTF = new TextFieldComponent(this,"Clave Cliente", "", 6, TextFieldComponent.PASSWORD | TextFieldComponent.NUMERIC);
        telefonoVendedorTF = new TextFieldComponent(this,"Telefono vendedor", "123456789", 9, TextFieldComponent.PHONENUMBER);
        claveVendedorTF = new TextFieldComponent(this,"Clave vendedor", "", 6, TextFieldComponent.PASSWORD | TextFieldComponent.NUMERIC);
        numeroDNITF = new TextFieldComponent(this,"Numero de DNI", "", 18, TextFieldComponent.NUMERIC);
        flagBanditCI = new CheckItemComponent("Bandit");

        planConsumoCG = new CheckItemComponent("01147");
        planConsumoCG2 = new CheckItemComponent("");
        group=new CheckGroupComponent(new LabelComponent("Plan de Consumo"), new CheckItemComponent[]{planConsumoCG2,planConsumoCG});
        planPanel = new PlanPseudoPanel();

        header=new Header(title);
        append(new ImageComponent(MovistarSMS.logo,ImageComponent.ALIGN_CENTER));
        append(numeroTelefonicoTF);
        append(imeiTF);
        append(iccidTF);
        append(cortePorRoboCI);
        append(claveVendedorTF);
        append(numeroDNITF);
        append(flagBanditCI);
        appendCheckGroupComponent(group);
        appendCheckGroupComponent(planPanel.getGroup());
        footer=new Footer("Enviar","Atras");

        initalize();
    }

    public void commandAction(int command) {
        switch (command) {
            case LEFT_SOFTKEY:
                if (validData()) {
                    Control.control.smsmanager.sendTextmessage(Constants.NUMERO_CORTO_CASI, getMGEQMessage());
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

    public void processKey() {
        super.processKey();
        if(cortePorRoboCI.itemStateChanged()) {
            if (cortePorRoboCI.isItemSelected()) {
                int index=components.indexOf(cortePorRoboCI);
                components.insertElementAt(claveClienteTF,index+1);
            } else
            { components.removeElement(claveClienteTF); }
        }
        else if(planConsumoCG.itemStateChanged())
        { planConsumoCG2.setItemSelected(!planConsumoCG.isItemSelected()); }
        else if(planConsumoCG2.itemStateChanged())
        { planConsumoCG.setItemSelected(!planConsumoCG2.isItemSelected()); }
        else if(planPanel.itemStateChanged())
        { planPanel.processPlan(); }
    }

    private String getMGEQMessage() {
        String res = "";
        res = Constants.LLAVE_ACCESO + Constants.TRAMA_CASI + CalendarData.getDate() + CalendarData.getHour() + Formater.getFixedStringFillWithSpacesAtTheEnd(numeroTelefonicoTF.getString(), 12) + imeiTF.getString() +
                Formater.getFixedStringFillWithSpacesAtTheEnd(Constants.ICCID_PREFIX + iccidTF.getString(), 25);

        if (cortePorRoboCI.isItemSelected()) {
            res += "S";
            res += Formater.getFixedStringFillWithZerosAtTheEnd(claveClienteTF.getString(), 16);
        } else {
            res += "N";
        }

        res += Constants.ASTERISCO_MARCADO;

        res += Formater.getFixedStringFillWithSpacesAtTheEnd(telefonoVendedorTF.getString(), 12);
        res += Formater.getFixedStringFillWithSpacesAtTheEnd(claveVendedorTF.getString(), 16);

        res += Formater.getFixedStringFillWithSpacesAtTheEnd(numeroDNITF.getString(), 18);
        if(planConsumoCG.isItemSelected())
        { res += Formater.getFixedStringFillWithZerosAtTheEnd("01147",5); }
        else
        { res += Formater.getFixedStringFillWithZerosAtTheEnd("",5); }

        if(flagBanditCI.isItemSelected()) {
            res += "S";
        } else {
            res += "N";
        }

        return res;
    }

    private boolean validData() {
        boolean res = true;
        int errors = 0;

        if (numeroTelefonicoTF.getString().length() < 9) {
            errors++;
            Control.control.showAlert("Error", "El numero telefonico debe tener al menos 9 digitos");
        } else if (imeiTF.getString().length() != 15) {
            errors++;
            Control.control.showAlert("Error", "El IMEI debe tener 15 digitos");
        } else if (telefonoVendedorTF.getString().length() != 9) {
            errors++;
            Control.control.showAlert("Error", "El tel�fono del vendedor debe tener 9 digitos");
        } else if (claveVendedorTF.getString().length() != 6) {
            errors++;
            Control.control.showAlert("Error", "La clave del vendedor debe tener 6 digitos");
        } else if (numeroDNITF.getString().length() < 8) {
            errors++;
            Control.control.showAlert("Error", "El numero de DNI debe tener al menos 8 digitos");
        } else if(cortePorRoboCI.isItemSelected() && flagBanditCI.isItemSelected()) {
            errors++;
            Control.control.showAlert("Error", "Las opciones Corte por Robo y Bandit no pueden estar seleccionadas simultaneamente");
        }

        if (errors > 0) {
            res = false;
        }
        return res;
    }
}
