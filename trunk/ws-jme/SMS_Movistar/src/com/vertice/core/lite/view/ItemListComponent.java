package com.vertice.core.lite.view;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class ItemListComponent extends Component{

    private String title;
    private FormCanvas toCanvas;
    private CustomCanvas fromList;
    private Font font;

    public ItemListComponent(CustomCanvas fromList, String title, FormCanvas toCanvas) {
        this.fromList = fromList;
        this.toCanvas = toCanvas;
        this.title = title;

        font=Font.getFont(Font.FACE_SYSTEM,Font.STYLE_BOLD,Font.SIZE_MEDIUM);
        requiredHeight=font.getHeight()+CustomCanvas.SEPARATOR;
    }

    public void setToCanvas(FormCanvas tc) { toCanvas=tc; }

    public boolean finalize() {
        title = null;
        fromList=null;
        toCanvas=null;
        return true;
    }

    public void setFocusedAction() {
        fromList.pauseCanvas();
        toCanvas.startCanvas();
        Constants.constants.getDisplay().setCurrent(toCanvas);
    }

    public int paintComponent(int offsetX,int offsetY,Graphics g) {
        if(selected) { 
            g.setColor(0x0080ff);
            g.fillRect(CustomCanvas.LEFT_BORDER,offsetY,CustomCanvas.UTIL_W-CustomCanvas.LEFT_BORDER-CustomCanvas.RIGHT_BORDER,font.getHeight());
            g.setColor(0xffffff);
        } else { g.setColor(0x000080); }

        g.setFont(font);
        g.drawString(title,CustomCanvas.LEFT_BORDER,offsetY,Graphics.LEFT|Graphics.TOP);

        return requiredHeight;
    }
}
