package com.vertice.application.movistar.view;

import com.vertice.application.movistar.control.Control;
import com.vertice.application.movistar.control.MovistarSMS;
import com.vertice.core.lite.view.*;
import javax.microedition.lcdui.Graphics;

public class ListPanel extends CustomCanvas {

    Footer footer;
    Header header;
    Scroll scroll;

    ItemListComponent il1,il2,il3,il4,il5,il6;

    CASIPanel casip;
    CAPanel cap;
    CPCVPanel cpcvp;
    CSCPanel cscp;
    PromPanel promp;

    String op1,op2,op3,op4,op5;

    public ListPanel(){
        super();

        ImageComponent ic=new ImageComponent(MovistarSMS.logo,ImageComponent.ALIGN_CENTER);
        op1="MGEQ";
        op2="CASI";
        op3="Consulta";
        op4="Consulta CAPL";
        op5="Promociones";
        
        il1=new ItemListComponent(this,op1, null);
        il2=new ItemListComponent(this,op2, null);
        il3=new ItemListComponent(this,op3, null);
        il4=new ItemListComponent(this,op4, null);
        il5=new ItemListComponent(this,op5, null);
        il6=new ItemListComponent(this,"Salir", null);

        header=new Header("Movistar");
        components.addElement(ic);
        components.addElement(il1);
        components.addElement(il2);
        components.addElement(il3);
        components.addElement(il4);
        components.addElement(il5);
        components.addElement(il6);
        footer=new Footer("","Ok");

        offsetY=0;
        uselessY=header.getRequiredHeight()+TOP_BORDER;
        utilAreaH=SCREEN_H-header.getRequiredHeight()-footer.getRequiredHeight();
        scroll=new Scroll(SCREEN_H-header.getRequiredHeight()-footer.getRequiredHeight()/4);

        (new Thread(this)).start();
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
                        casip=new CASIPanel(this,op1);
                        ((ItemListComponent)components.elementAt(1)).setToCanvas(casip);
                        ((ItemListComponent)components.elementAt(1)).setFocusedAction();
                        break;
                    case 2:
                        cscp=new CSCPanel(this,op2);
                        ((ItemListComponent)components.elementAt(current)).setToCanvas(cscp);
                        ((ItemListComponent)components.elementAt(current)).setFocusedAction();
                        break;
                    case 3:
                        cap=new CAPanel(this,op3);
                        ((ItemListComponent)components.elementAt(current)).setToCanvas(cap);
                        ((ItemListComponent)components.elementAt(current)).setFocusedAction();
                        break;
                    case 4:
                        cpcvp=new CPCVPanel(this,op4);
                        ((ItemListComponent)components.elementAt(current)).setToCanvas(cpcvp);
                        ((ItemListComponent)components.elementAt(current)).setFocusedAction();
                        break;
                    case 5:
                        promp=new PromPanel(this, op5);
                        ((ItemListComponent)components.elementAt(current)).setToCanvas(promp);
                        ((ItemListComponent)components.elementAt(current)).setFocusedAction();
                        break;
                    case 6:
                        Control.control.exit();
                        break;
                }
                break;
            case LEFT_SOFTKEY:   break;
        }
        keyStates=0;
    }

    public void clear(){
        if(casip!=null) { casip.destroy(); casip=null; }
        if(cap!=null)   { cap.destroy(); cap=null; }
        if(cpcvp!=null) { cpcvp.destroy(); cpcvp=null; }
        if(cpcvp!=null) { cpcvp.destroy(); cpcvp=null; }
        if(cscp!=null)  { cscp.destroy(); cscp=null; }
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