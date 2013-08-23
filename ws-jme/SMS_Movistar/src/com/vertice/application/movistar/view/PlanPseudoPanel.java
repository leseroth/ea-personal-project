package com.vertice.application.movistar.view;

import com.vertice.application.movistar.control.Control;
import com.vertice.application.movistar.util.Constants;
import com.vertice.application.movistar.util.Formater;
import com.vertice.core.lite.view.CheckGroupComponent;
import com.vertice.core.lite.view.CheckItemComponent;
import com.vertice.core.lite.view.LabelComponent;

public class PlanPseudoPanel {

    private static final String[][] PLAN_MAS = {
        {"Ninguno","Plan Mas 15","Plan Mas 25","Plan Mas 35"},{null, "MAS-15B","MAS-25B","MAS-35B"}};
    private CheckGroupComponent group;
    private CheckItemComponent selected;

    public PlanPseudoPanel(){
        CheckItemComponent[] items = new CheckItemComponent[PLAN_MAS[0].length];

        for(int i=0;i<items.length;i++) {
            items[i] = new CheckItemComponent(PLAN_MAS[0][i]);
        }

        group = new CheckGroupComponent(new LabelComponent("Plan"), items);
        selected = items[0];
        selected.setItemSelected(true);
    }

    public boolean itemStateChanged(){
       boolean stateChanged = false;
       for(int i=0; i<group.getItems().length; i++){
            CheckItemComponent itemComponent = group.getItems()[i];
            if(itemComponent.itemStateChanged()){
                selected = itemComponent;
                stateChanged = true;
                break;
            }
       }
       return stateChanged;
    }

    public void processPlan(){
        for(int i=0; i<group.getItems().length; i++){
            group.getItems()[i].setItemSelected(false);
        }
        selected.setItemSelected(true);
    }

    public CheckGroupComponent getGroup() {
        return group;
    }

    public void sendTextMessage(){
        if(!group.getItems()[0].isItemSelected()){
            Control.control.smsmanager.sendTextmessage(Constants.NUMERO_CORTO_PLAN, getPromMessage());
        } 
    }

    private String getPromMessage(){
        String res = "";

        for(int i = 0; i<group.getItems().length; i++){
            if(group.getItems()[i].isItemSelected()) {
                res = Formater.getFixedStringFillWithSpacesAtTheEnd("A",5)+" "+PLAN_MAS[1][i];
            }
        }

        return res;
    }
}
