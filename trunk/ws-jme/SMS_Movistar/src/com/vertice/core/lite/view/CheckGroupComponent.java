package com.vertice.core.lite.view;

import java.util.Vector;

public class CheckGroupComponent {
    LabelComponent label;
    CheckItemComponent[] items;

    public CheckGroupComponent(LabelComponent label, CheckItemComponent[] items) {
        this.label = label;
        this.items = items;

        label.setGroupItem(true);
        for(int i=0;i<items.length;i++)
        { items[i].setGroupItem(true); }

        items[0].setItemSelected(true);
        items[items.length-1].setLastGroupItem(true);
    }

    public void insertInto(Vector vector){
        vector.addElement(label);
        for(int i=0;i<items.length;i++)
        { vector.addElement(items[i]); }
    }

    public CheckItemComponent[] getItems() {
        return items;
    }
}
