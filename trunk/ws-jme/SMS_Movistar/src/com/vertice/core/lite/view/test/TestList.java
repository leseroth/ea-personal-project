package com.vertice.core.lite.view.test;

import com.vertice.application.movistar.control.MovistarSMS;
import com.vertice.core.lite.view.*;
import javax.microedition.lcdui.Graphics;

public class TestList extends CustomCanvas {

    Footer footer;
    Header header;
    Scroll scroll;

    ItemListComponent il1,il2,il3;

    CaeqForm caeqForm;
    MgeqForm mgeqForm;
    ConsultaForm consultaForm;

    public TestList(){
        super();

        ImageComponent ic=new ImageComponent(MovistarSMS.logo,ImageComponent.ALIGN_CENTER);
        il1=new ItemListComponent(this,"CAEQ", null);
        il2=new ItemListComponent(this,"MGEQ - Robo", null);
        il3=new ItemListComponent(this,"Consulta", null);

        header=new Header("Movistar");
        components.addElement(ic);
        components.addElement(il1);
        components.addElement(il2);
        components.addElement(il3);
        footer=new Footer("","Ok");

        System.out.println(components.size()+" "+components.elementAt(0));

        offsetY=0;
        uselessY=header.getRequiredHeight()+TOP_BORDER;
        utilAreaH=SCREEN_H-header.getRequiredHeight()-footer.getRequiredHeight();
        scroll=new Scroll(SCREEN_H-header.getRequiredHeight()-footer.getRequiredHeight()/4);

        (new Thread(this)).start();
    }

    public void run() {
        while(run){
            try {
                if(pause) { Thread.sleep(Constants.sleepTime); }
                else {
                    startCicle();

                    processKey();
                    calculateOffsetY();
                    customPaint();

                    System.gc();
                    Thread.sleep(idleTime);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void processKey() {
        switch (keyStates){
            case KEY_UP:
                if(current!=0) { current--; }
                break;
            case KEY_DOWN:
                if(current==0) { current=2; }
                else {
                    current++;
                    if(current==components.size()) { current--; }
                }
                break;
            case KEY_LEFT:  break;
            case KEY_RIGHT: break;
            case RIGHT_SOFTKEY: case KEY_FIRE:
                clear();
                switch(current){
                    case 0: case 1:
                        caeqForm=new CaeqForm(this);
                        ((ItemListComponent)components.elementAt(1)).setToCanvas(caeqForm);
                        ((ItemListComponent)components.elementAt(1)).setFocusedAction();
                        break;
                    case 2:
                        mgeqForm=new MgeqForm(this);
                        ((ItemListComponent)components.elementAt(2)).setToCanvas(mgeqForm);
                        ((ItemListComponent)components.elementAt(2)).setFocusedAction();
                        break;
                    case 3:
                        consultaForm=new ConsultaForm(this);
                        ((ItemListComponent)components.elementAt(3)).setToCanvas(consultaForm);
                        ((ItemListComponent)components.elementAt(3)).setFocusedAction();
                        break;
                }
                break;
            case LEFT_SOFTKEY:   break;
        }
        keyStates=0;
    }

    public void clear(){
        if(caeqForm!=null) { caeqForm.destroy(); caeqForm=null; }
        if(mgeqForm!=null) { mgeqForm.destroy(); mgeqForm=null; }
        if(consultaForm!=null) { consultaForm.destroy(); consultaForm=null; }
    }

    public void customPaint(){
        bufferGraphics.setColor(BACKGROUND_COLOR);
        bufferGraphics.fillRect(0,0,SCREEN_W,SCREEN_H);

        if(scroll.isRequired(getRequiredHeight(),current,components.size())) {
            scroll.paintComponent(SCREEN_W-scroll.getRequiredHeight(), header.getRequiredHeight(), bufferGraphics);
            UTIL_W=SCREEN_W-scroll.getRequiredHeight();
        } else { UTIL_W=SCREEN_W; }

        paintComponents(offsetY+uselessY);
        header.paintComponent(bufferGraphics);
        footer.paintComponent(bufferGraphics);
        statics(Constants.trace);
        graphics.drawImage(bufferImage,0,0,Graphics.TOP|Graphics.LEFT);

        flushGraphics();
    }
}
