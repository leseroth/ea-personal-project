package com.vertice.core.lite.view;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class LabelComponent extends Component {
    int offX;
    Font font;
    String text;

    public LabelComponent(String text) {
        this.text=text;

        navegable=false;
        font=Font.getFont(Font.FACE_SYSTEM,Font.STYLE_BOLD,Font.SIZE_MEDIUM);
        requiredHeight=font.getHeight()+CustomCanvas.SEPARATOR;
    }

    public int paintComponent(int offsetX, int offsetY, Graphics g) {
        g.setColor(0x000080);
        g.setFont(font);
        g.drawString(text,offsetX,offsetY,Graphics.LEFT|Graphics.TOP);
        if(groupItem) 
        { return font.getHeight(); }
        else
        { return requiredHeight; }
    }

    

    public void setFocusedAction() {}
}
