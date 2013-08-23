package com.vertice.core.lite.view;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class CheckItemComponent extends Component {
    boolean stateChanged=false;
    boolean itemSelected=false;
    boolean lastGroupItem=false;
    private String title;
    Font font;
    int checkSize,modifier,modifierY;

    public CheckItemComponent(String title) {
        this.title=title;

        font=Font.getFont(Font.FACE_SYSTEM,Font.STYLE_BOLD,Font.SIZE_MEDIUM);
        modifier=modifierY=2;
        checkSize=font.getHeight()-2*modifierY;
        requiredHeight=font.getHeight()+CustomCanvas.SEPARATOR;

        if(checkSize<14){
            checkSize=14; modifier=2; modifierY=0;
            requiredHeight=checkSize+CustomCanvas.SEPARATOR;
        }
    }

    public int paintComponent(int offsetX, int offsetY, Graphics g) {
        if(groupItem) { offsetX=offsetX+CustomCanvas.LEFT_BORDER; }

        if(selected) {
            g.setColor(0x31c221);
            g.fillRect(offsetX+modifier, offsetY+modifierY, checkSize,checkSize);
        }
        g.setColor(0x000084);
        g.fillRect(offsetX+2+modifier, offsetY+2+modifierY, checkSize-4, checkSize-4);
        g.setColor(0xffffff);
        g.fillRect(offsetX+3+modifier, offsetY+3+modifierY, checkSize-6, checkSize-6);
        if(itemSelected) {
            g.setColor(0x0082ff);
            g.fillRect(offsetX+4+modifier, offsetY+4+modifierY, checkSize-8,checkSize-8);
        }

        g.setColor(0x000080);
        g.setFont(font);
        g.drawString(title,offsetX+checkSize+modifier*2,offsetY+(requiredHeight-font.getHeight())/2,Graphics.LEFT|Graphics.TOP);

        if(groupItem && !lastGroupItem)
        { return requiredHeight-CustomCanvas.SEPARATOR; }
        else
        { return requiredHeight; }
    }

    public void setLastGroupItem(boolean lastGroupItem) {
        this.lastGroupItem = lastGroupItem;
    }

    public void setFocusedAction() {
        stateChanged=true;
        itemSelected=!itemSelected;
    }

    public boolean isItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(boolean itemSelected) {
        this.itemSelected = itemSelected;
    }

    public boolean itemStateChanged() {
        boolean isc=stateChanged;
        stateChanged=false;
        return isc;
    }
}
