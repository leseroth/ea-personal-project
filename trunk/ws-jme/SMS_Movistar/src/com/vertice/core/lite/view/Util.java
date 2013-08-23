package com.vertice.core.lite.view;

import javax.microedition.lcdui.Graphics;

public class Util {

    public static int[][] getLinealGradient(int fromColor[],int toColor[],int steps,int center){
        int[][] gradient=new int[steps][3];

        if(center!=0) { steps=center-1; }
        
        double[] factor=new double[3];
        factor[0]=(toColor[0]-fromColor[0])/steps;
        factor[1]=(toColor[1]-fromColor[1])/steps;
        factor[2]=(toColor[2]-fromColor[2])/steps;

        for(int i=0;i<gradient.length;i++) {
            if(i<center){ 
                gradient[i][0]=(new Double(fromColor[0]+factor[0]*i)).intValue();
                gradient[i][1]=(new Double(fromColor[1]+factor[1]*i)).intValue();
                gradient[i][2]=(new Double(fromColor[2]+factor[2]*i)).intValue();
            } else {
                gradient[i][0]=(new Double(fromColor[0]+factor[0]*(2*center-i))).intValue();
                gradient[i][1]=(new Double(fromColor[1]+factor[1]*(2*center-i))).intValue();
                gradient[i][2]=(new Double(fromColor[2]+factor[2]*(2*center-i))).intValue();
            }

            for(int j=0;j<3;j++){
                if(gradient[i][j]>255) { gradient[i][j]=255; }
                if(gradient[i][j]<0)   { gradient[i][j]=0; }
            }
        }
        
        return gradient;
    }

    public static void drawHorizontalGradient(int gradient[][],int fromX,int toX,int offsetY,Graphics g,boolean mirror){
        if(mirror) { offsetY=offsetY+gradient.length-1; }
        for(int i=0;i<gradient.length;i++){
            g.setColor(gradient[i][0],gradient[i][1],gradient[i][2]);
            if(mirror) { g.drawLine(fromX,offsetY-i,toX,offsetY-i); }
            else       { g.drawLine(fromX,offsetY+i,toX,offsetY+i); }
        }
    }

    public static void drawVerticalGradient(int gradient[][],int offsetX,int fromY,int toY,Graphics g,boolean mirror){
        if(mirror) { offsetX=offsetX+gradient.length-1; }
        for(int i=0;i<gradient.length;i++){
            g.setColor(gradient[i][0],gradient[i][1],gradient[i][2]);
            if(mirror) { g.drawLine(offsetX-i,fromY,offsetX-i,toY); }
            else       { g.drawLine(offsetX+i,fromY,offsetX+i,toY); }
        }
    }
}
