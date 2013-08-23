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

public class CAEQPanel extends FormCanvas {

    private TextFieldComponent numeroTelefonicoTF, imeiTF, telefonoVendedorTF, claveVendedorTF, numeroDNITF;
    private CheckItemComponent planConsumoCG,planConsumoCG2;
    private CheckGroupComponent group;
    private PlanPseudoPanel planPanel;

    public CAEQPanel(CustomCanvas fl,String title){
        super(fl);

        numeroTelefonicoTF = new TextFieldComponent(this,"Numero Telefono", "", 12, TextFieldComponent.PHONENUMBER);
        imeiTF = new TextFieldComponent(this,"IMEI", "", 15, TextFieldComponent.NUMERIC);
        telefonoVendedorTF = new TextFieldComponent(this,"Telefono vendedor", "123456789", 9, TextFieldComponent.PHONENUMBER);
        claveVendedorTF = new TextFieldComponent(this,"Clave vendedor", "", 6, TextFieldComponent.PASSWORD | TextFieldComponent.NUMERIC);
        numeroDNITF = new TextFieldComponent(this,"Numero de DNI", "", 18, TextFieldComponent.NUMERIC);
        planConsumoCG = new CheckItemComponent("01147");
        planConsumoCG2 = new CheckItemComponent("");

        group=new CheckGroupComponent(new LabelComponent("Plan de Consumo"), new CheckItemComponent[]{planConsumoCG2,planConsumoCG});
        planPanel = new PlanPseudoPanel();

        header=new Header(title);
        append(new ImageComponent(MovistarSMS.logo,ImageComponent.ALIGN_CENTER));
        append(numeroTelefonicoTF);
        append(imeiTF);
        append(claveVendedorTF);
        append(numeroDNITF);
        appendCheckGroupComponent(group);
        appendCheckGroupComponent(planPanel.getGroup());
        footer=new Footer("Enviar","Atras");

        initalize();
    }

    public void processKey() {
        super.processKey();
        if(planConsumoCG.itemStateChanged())
        { planConsumoCG2.setItemSelected(!planConsumoCG.isItemSelected()); }
        else if(planConsumoCG2.itemStateChanged())
        { planConsumoCG.setItemSelected(!planConsumoCG2.isItemSelected()); }
        else if(planPanel.itemStateChanged())
        { planPanel.processPlan(); }
    }

    public void commandAction(int command) {
        switch (command) {
            case LEFT_SOFTKEY:
                if (validData()) {
                    Control.control.smsmanager.sendTextmessage(Constants.NUMERO_CORTO_CAEQ, getCAEQMessage());
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

    private String getCAEQMessage() {
        String res = "";
        res = Constants.LLAVE_ACCESO + Constants.TRAMA_CAEQ + CalendarData.getDate() + CalendarData.getHour() + Formater.getFixedStringFillWithSpacesAtTheEnd(numeroTelefonicoTF.getString(), 12) + imeiTF.getString() + Constants.ASTERISCO_MARCADO;
        res += "N" + Formater.getFixedStringFillWithSpacesAtTheEnd(telefonoVendedorTF.getString(), 12) + Formater.getFixedStringFillWithSpacesAtTheEnd(claveVendedorTF.getString(), 16) + Formater.getFixedStringFillWithSpacesAtTheEnd(numeroDNITF.getString(), 18);
        if(planConsumoCG.isItemSelected()) 
        { res += Formater.getFixedStringFillWithZerosAtTheEnd("01147",5); }
        else
        { res += Formater.getFixedStringFillWithZerosAtTheEnd("",5); }
        return res;
    }

    private boolean validData() {
        boolean res = true;
        int errors = 0;

        if (numeroTelefonicoTF.getString().length() < 9) {
            errors++;
            Control.control.showAlert("Error", "El numero telef�nico debe tener al menos 9 digitos");
        } else if (imeiTF.getString().length() != 15) {
            errors++;
            Control.control.showAlert("Error", "El IMEI debe tener 15 digitos");
        } else if (telefonoVendedorTF.getString().length() != 9) {
            errors++;
            Control.control.showAlert("Error", "El telefono del vendedor debe tener 9 digitos");
        } else if (claveVendedorTF.getString().length() != 6) {
            errors++;
            Control.control.showAlert("Error", "La clave del vendedor debe tener 6 digitos");
        } else if (numeroDNITF.getString().length() < 8) {
            errors++;
            Control.control.showAlert("Error", "El numero de DNI debe tener al menos 8 digitos");
        }

        if (errors > 0) {
            res = false;
        }
        return res;
    }
}
