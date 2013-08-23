package com.vertice.core.lite.view;

import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public abstract class CustomCanvas extends GameCanvas implements Runnable {
    public Graphics graphics;
    public Graphics bufferGraphics;
    public Image bufferImage;
    public long idleTime;
    public boolean run,pause;
    public Vector components;
    public int utilAreaH;
    public int offsetY,uselessY;

    public int keyStates;
    public int current;
    public static final int KEY_UP=1,KEY_DOWN=2,KEY_LEFT=3,KEY_RIGHT=4,KEY_FIRE=5,LEFT_SOFTKEY=6,RIGHT_SOFTKEY=7,KEY_NUMBER=8;
    public static final int LEFT_BORDER=5,RIGHT_BORDER=5,TOP_BORDER=5,BOTTOM_BORDER=5,SEPARATOR=8;
    public static int BACKGROUND_COLOR=0xFFFFFF;
    public static int SCREEN_W,SCREEN_H,UTIL_W;

    public CustomCanvas(){
        super(false);
        setFullScreenMode(true);
        graphics=getGraphics();

        SCREEN_H=getHeight();
        SCREEN_W=UTIL_W=getWidth();

        bufferImage=Image.createImage(SCREEN_W,SCREEN_H);
        bufferGraphics=bufferImage.getGraphics();
        run=true;
        components=new Vector();
        current=0;
        pause=false;
    }

    public void destroy(){
        run=false;

        graphics=bufferGraphics=null;
        bufferImage=null;

        System.gc();
    }

    public void pauseCanvas() {pause=true;}
    public void startCanvas() {pause=false;}

    protected void keyPressed(int keyCode) {
        keyStates = keyCode;

        switch (keyStates) {
            case -1: keyStates = KEY_UP; break; //UP_PRESSED: 50
            case -2: keyStates = KEY_DOWN;  break; //DOWN_PRESSED: 56
            case -3: keyStates = KEY_LEFT;  break; //LEFT_PRESSED: 52
            case -4: keyStates = KEY_RIGHT; break; //RIGHT_PRESSED: 54
            case -5: keyStates = KEY_FIRE;  break; //FIRE_PRESSED: 53
            case -6: keyStates = LEFT_SOFTKEY;   break; //softKey izq
            case -7: keyStates = RIGHT_SOFTKEY;  break; //softKey der
            case 48: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57:
                keyStates = KEY_NUMBER;
                break;
            default: keyStates = 0; break;
        }
    }

    public abstract void processKey();

    public abstract void customPaint();

    public void startCicle()
    { idleTime=System.currentTimeMillis(); }


    public void statics(boolean trace){
        idleTime=(Constants.sleepTime-(System.currentTimeMillis()-idleTime));

        if(trace){
        bufferGraphics.setColor(0);
        bufferGraphics.drawString("tm: "+Runtime.getRuntime().totalMemory(),0,20,Graphics.TOP|Graphics.LEFT);
        bufferGraphics.drawString("mu: "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()),0,35,Graphics.TOP|Graphics.LEFT);
        bufferGraphics.drawString("id: "+idleTime+"/"+Constants.sleepTime,0,50,Graphics.TOP|Graphics.LEFT);
        }

        if(idleTime<0) { idleTime=0; }
    }

    public void paintComponents(int offsetY){
        Component component;
        for(int i=0;i<components.size();i++){
            component=(Component)components.elementAt(i);
            component.setSelected(current==i);
        }

        component=(Component)components.elementAt(current);        
        if(!component.isNavegable()) {
            component.setSelected(false);
            ((Component)components.elementAt(getNextNavegableItem(current))).setSelected(true);
        }
        
        for(int i=0;i<components.size();i++){
            component=(Component)components.elementAt(i);
            offsetY=offsetY+component.paintComponent(CustomCanvas.LEFT_BORDER, offsetY, bufferGraphics);
        }
    }

   public int getNextNavegableItem(int _index) {
        int index=components.size()-1;

        if(_index==components.size()-1){
            index=0;
        } else {
            Component component;
            for(int i=_index+1;i<components.size();i++) {
                component=(Component)components.elementAt(i);
                if(component.isNavegable()) { index=i; break; }
            }
        }

        return index;
    }

    public void calculateOffsetY(){
        int minY=0,maxY=0,aux=0;
        for(int i=0;i<components.size();i++) {
            if(i==current) {
                minY=offsetY+aux;
                maxY=offsetY+aux+((Component)components.elementAt(i)).getRequiredHeight();
                break;
            }
            else
            { aux=aux+((Component)components.elementAt(i)).getRequiredHeight(); }
        }

        if(maxY>utilAreaH) { offsetY=utilAreaH-(maxY-offsetY); }
        if(minY<0)         { offsetY=offsetY-minY; }

    }

    public int getRequiredHeight(){
        int requiredH=0;
        for(int i=0;i<components.size();i++)
        { requiredH=requiredH+((Component)components.elementAt(i)).getRequiredHeight(); }
        return requiredH;
    }
}
