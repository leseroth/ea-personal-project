package com.vertice.core.lite.view;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class Header extends Component{
    private String header;
    private int[][] colors;
    Font font;

    public Header(String headerText){ 
        font=Font.getFont(Font.FACE_SYSTEM,Font.STYLE_BOLD,Font.SIZE_MEDIUM);
        colors=Util.getLinealGradient(new int[]{0,51,178},new int[]{0,128,255},font.getHeight()+2,font.getHeight()-6);
        colors[colors.length-1]=new int[]{0,0,128};
        header=headerText;
        requiredHeight=colors.length;
   }

    public int paintComponent(Graphics g) {
        Util.drawHorizontalGradient(colors,0,CustomCanvas.SCREEN_W,0,g,false);
        g.setColor(0xFFFFFF);
        g.setFont(font);
        g.drawString(header,CustomCanvas.SCREEN_W/2,2,Graphics.HCENTER|Graphics.TOP);
        return colors.length;
    }

    public void setFocusedAction() {}

    public int paintComponent(int offsetX, int offsetY, Graphics g) { return 0; }
}

