package com.vertice.core.lite.view;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class Footer extends Component{
    private String leftOption,rightOption;
    private int[][] colors;
    private Font font;
    
    public Footer(String left,String right){
        font=Font.getFont(Font.FACE_SYSTEM,Font.STYLE_BOLD,Font.SIZE_MEDIUM);
        colors=Util.getLinealGradient(new int[]{0x00,0x13,0x93},new int[]{0x00,0x80,0xff},font.getHeight()+2,font.getHeight()-2);
        colors[colors.length-1]=new int[]{0,0,0x80};
        leftOption=left; rightOption=right;
        requiredHeight=colors.length;
    }

    public int paintComponent(Graphics g) {
        Util.drawHorizontalGradient(colors,0,CustomCanvas.SCREEN_W,CustomCanvas.SCREEN_H-colors.length+1, g,true);
        g.setColor(0xFFFFFF);
        g.setFont(font);
        g.drawString(leftOption,CustomCanvas.LEFT_BORDER,CustomCanvas.SCREEN_H-CustomCanvas.BOTTOM_BORDER,Graphics.LEFT|Graphics.BASELINE);
        g.drawString(rightOption,CustomCanvas.SCREEN_W-CustomCanvas.RIGHT_BORDER,CustomCanvas.SCREEN_H-CustomCanvas.BOTTOM_BORDER,Graphics.RIGHT|Graphics.BASELINE);
        return colors.length;
    }

    public void setFocusedAction() {}

    public int paintComponent(int offsetX, int offsetY, Graphics g) { return 0; }
}
