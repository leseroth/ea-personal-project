package com.vertice.core.lite.view;

import javax.microedition.lcdui.Graphics;

public abstract class Component {
    public int fontColor;
    public int borderColor;
    public int backGroundColor;
    public boolean selected=false;
    public int requiredHeight;
    public boolean navegable=true;
    public boolean groupItem=false;

    public abstract int paintComponent(int offsetX,int offsetY,Graphics g);

    public abstract void setFocusedAction();

    public boolean isSelected() {
        return selected;
    }

    public boolean isGroupItem() {
        return groupItem;
    }

    public void setGroupItem(boolean groupItem) {
        this.groupItem = groupItem;
    }

    public boolean isNavegable() { return navegable; }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getRequiredHeight() {
        return requiredHeight;
    }
}
