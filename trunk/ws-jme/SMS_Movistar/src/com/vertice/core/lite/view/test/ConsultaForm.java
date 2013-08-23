package com.vertice.core.lite.view.test;

import com.vertice.application.movistar.control.MovistarSMS;
import com.vertice.core.lite.view.Constants;
import com.vertice.core.lite.view.CustomCanvas;
import com.vertice.core.lite.view.Footer;
import com.vertice.core.lite.view.FormCanvas;
import com.vertice.core.lite.view.Header;
import com.vertice.core.lite.view.ImageComponent;
import com.vertice.core.lite.view.Scroll;
import com.vertice.core.lite.view.TextFieldComponent;
import javax.microedition.lcdui.Graphics;

public class ConsultaForm extends FormCanvas {
    Footer footer;
    Header header;
    Scroll scroll;

    public ConsultaForm(CustomCanvas fl){
        super(fl);

        ImageComponent ic=new ImageComponent(MovistarSMS.logo,ImageComponent.ALIGN_CENTER);
        TextFieldComponent cv=new TextFieldComponent(this,"Clave Vendedor","",10,TextFieldComponent.ANY);
        TextFieldComponent nt=new TextFieldComponent(this,"Num. Telefono","",10,TextFieldComponent.ANY);

        header=new Header("Movistar");
        components.addElement(ic);
        components.addElement(cv);
        components.addElement(nt);
        footer=new Footer("Atras","Enviar");
        offsetY=0;
        uselessY=header.getRequiredHeight()+TOP_BORDER;
        utilAreaH=SCREEN_H-header.getRequiredHeight()-footer.getRequiredHeight();
        scroll=new Scroll(SCREEN_H-header.getRequiredHeight());

        (new Thread(this)).start();
    }
}
