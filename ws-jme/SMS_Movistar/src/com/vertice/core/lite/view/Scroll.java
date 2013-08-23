package com.vertice.core.lite.view;

import javax.microedition.lcdui.Graphics;

public class Scroll extends Component {
    private int[][] frontColors, backColors;
    private int barSize,current,usableH,totalC;

    public Scroll(int usable) {
        usableH=usable;
        frontColors = Util.getLinealGradient(new int[]{0, 0x66, 0xe5}, new int[]{0, 0x04, 0x84}, 6, 6);
        backColors = Util.getLinealGradient(new int[]{0xb9, 0xb9, 0xbd}, new int[]{0x46, 0x46, 0x97}, 6, 5);
        backColors[backColors.length - 1] = new int[]{0, 0, 128};
        requiredHeight = frontColors.length;
    }

    public int paintComponent(int offsetX, int offsetY, Graphics g) {
        current=(usableH-barSize)*current/totalC;
        Util.drawVerticalGradient(backColors, offsetX, offsetY, offsetY+usableH, g, true);
        Util.drawVerticalGradient(frontColors, offsetX, current+offsetY,current+offsetY+barSize,g, false);
        return frontColors.length;
    }

    public void setFocusedAction() {}

    public boolean isRequired(int height, int current,int totalC) {
        this.current=current;
        this.totalC=totalC;
        barSize=usableH*usableH/(2*height);
        return height > usableH;
    }
}
