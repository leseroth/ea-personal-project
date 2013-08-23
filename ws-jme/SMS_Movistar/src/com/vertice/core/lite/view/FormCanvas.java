package com.vertice.core.lite.view;

import javax.microedition.lcdui.Graphics;

public abstract class FormCanvas extends CustomCanvas {
    public CustomCanvas fromList;
    public Footer footer;
    public Header header;
    public Scroll scroll;

    public FormCanvas(CustomCanvas fl){
        super();
        fromList=fl;
    }

    public void processKey(){
        Component component=(Component)components.elementAt(current);

        switch (keyStates){
            case KEY_UP:
                if(current!=0) { current--; }
                else           { current=components.size()-1; }
                break;
            case KEY_DOWN:
                current=getNextNavegableItem(current);
                break;
            case KEY_LEFT:  break;
            case KEY_RIGHT: break;
            case KEY_FIRE: case KEY_NUMBER:
                boolean check=component instanceof CheckItemComponent;
                
                if(check)
                { check=keyStates==KEY_FIRE; }
                else
                { check=true; }

                if(check){
                    if(component.isNavegable())
                    { component.setFocusedAction(); }
                    else
                    { ((Component)components.elementAt(getNextNavegableItem(current))).setFocusedAction(); }
                }
                break;
            case RIGHT_SOFTKEY:
            case LEFT_SOFTKEY:            
                commandAction(keyStates);
                break;
        }
        keyStates=0;
    }

    public void commandAction(int command){
        switch(command){
            case RIGHT_SOFTKEY:
            case LEFT_SOFTKEY:
                run=false;
                fromList.startCanvas();
                Constants.constants.getDisplay().setCurrent(fromList);
                break;
        }
    }

    public void customPaint() {
        bufferGraphics.setColor(BACKGROUND_COLOR);
        bufferGraphics.fillRect(0,0,SCREEN_W,SCREEN_H);

        paintComponents(offsetY+uselessY);
        if(scroll.isRequired(getRequiredHeight(),current,components.size())) {
            scroll.paintComponent(SCREEN_W-scroll.getRequiredHeight(), header.getRequiredHeight(), bufferGraphics);
            UTIL_W=SCREEN_W-scroll.getRequiredHeight();
        } else { UTIL_W=SCREEN_W; }

        header.paintComponent(bufferGraphics);
        footer.paintComponent(bufferGraphics);
        statics(Constants.trace);
        graphics.drawImage(bufferImage,0,0,Graphics.TOP|Graphics.LEFT);

        flushGraphics();
    }

    public void run() {
        while(run){

            try {
                if(pause) { Thread.sleep(com.vertice.core.lite.view.Constants.sleepTime); }
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

        destroy();
    }

    public void initalize() {
        offsetY=0;
        uselessY=header.getRequiredHeight()+TOP_BORDER;
        utilAreaH=SCREEN_H-header.getRequiredHeight()-footer.getRequiredHeight();
        scroll=new Scroll(SCREEN_H-header.getRequiredHeight());

        (new Thread(this)).start();
    }

    public void append(Component component)
    { components.addElement(component); }

    public void appendCheckGroupComponent(CheckGroupComponent component)
    { component.insertInto(components); }
}
