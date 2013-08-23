package com.vertice.core.lite.view.test;

import com.vertice.application.movistar.control.MovistarSMS;
import com.vertice.core.lite.view.CheckItemComponent;
import com.vertice.core.lite.view.Constants;
import com.vertice.core.lite.view.CustomCanvas;
import com.vertice.core.lite.view.Footer;
import com.vertice.core.lite.view.FormCanvas;
import com.vertice.core.lite.view.Header;
import com.vertice.core.lite.view.ImageComponent;
import com.vertice.core.lite.view.Scroll;
import com.vertice.core.lite.view.TextFieldComponent;
import javax.microedition.lcdui.Graphics;

public class MgeqForm extends FormCanvas {
    Footer footer;
    Header header;
    Scroll scroll;
    CheckItemComponent cr;
    TextFieldComponent cc;

    public MgeqForm(CustomCanvas fl){
        super(fl);

        ImageComponent ic=new ImageComponent(MovistarSMS.logo,ImageComponent.ALIGN_CENTER);
        TextFieldComponent cv=new TextFieldComponent(this,"Clave Vendedor","",10,TextFieldComponent.ANY);
        TextFieldComponent nt=new TextFieldComponent(this,"Num. Telefono","",10,TextFieldComponent.ANY);
        TextFieldComponent imei=new TextFieldComponent(this,"IMEI","",10,TextFieldComponent.ANY);
        TextFieldComponent sc=new TextFieldComponent(this,"Sim Card","",10,TextFieldComponent.ANY);
        cr=new CheckItemComponent("Corte por Robo");

        header=new Header("Movistar");
        components.addElement(ic);
        components.addElement(cv);
        components.addElement(nt);
        components.addElement(imei);
        components.addElement(sc);
        components.addElement(cr);
        footer=new Footer("Atras","Enviar");
        offsetY=0;
        uselessY=header.getRequiredHeight()+TOP_BORDER;
        utilAreaH=SCREEN_H-header.getRequiredHeight()-footer.getRequiredHeight();
        scroll=new Scroll(SCREEN_H-header.getRequiredHeight());

        (new Thread(this)).start();
    }

    public void processKey() {
        super.processKey();
        if(cr.itemStateChanged()) {
            if(cr.isItemSelected()) {
                cc=new TextFieldComponent(this,"Clave Cliente","",10,TextFieldComponent.ANY);
                components.addElement(cc);
                current=components.size()-1;
            } else {
                current=components.size()-2;
                components.removeElement(cc);
                cc=null;
                offsetY=getRequiredHeight();
            }
        }
    }
}
