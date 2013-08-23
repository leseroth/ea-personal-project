package com.vertice.application.movistar.smsmanager;

import com.vertice.application.movistar.control.Control;
import java.io.IOException;

import javax.microedition.io.Connector;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;
import javax.wireless.messaging.TextMessage;

public class SMSManager implements MessageListener {

    private MessageConnection mc;

    public SMSManager() {
    }

    public void init() {
        try {
            mc = (MessageConnection) Connector.open("sms://:3333");
            mc.setMessageListener(this);
        } catch (IOException e) {
            Control.control.showAlert("Excepcion Server SMS", e.getMessage());
        }
    }

    public void notifyIncomingMessage(MessageConnection receivermc) {
        System.out.println("NotifyIncomingMessage");
        try {

            Message message;
            message = receivermc.receive();
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                Control.control.showAlert("Text Message", "Adddress=" + textMessage.getAddress() + " payloadText=" + textMessage.getPayloadText());
            } else if (message instanceof BinaryMessage) {
                BinaryMessage binaryMessage = (BinaryMessage) message;
                String texto = new String(binaryMessage.getPayloadData());
                System.out.println("Notify Incomming Message: "+texto);
                //Control.control.showAlert("Binary Message", "Adddress="+binaryMessage.getAddress()+" payloadData="+binaryMessage.getPayloadData()+" Texto="+texto);
            } else {
                Control.control.showAlert("Wrong Message", "Adddress=" + message.getAddress());
            }
        } catch (Exception e) {
            Control.control.showAlert("Excepcion", e.getMessage());
        }

    }

    /* Send text message */
    public void sendTextmessage(final String address, final String message) {
        new Thread() {

            public void run() {
                System.out.println("SendTextMessage: address=" + address + " message=" + message);
                super.run();
                try {
                    //MessageConnection sender = (MessageConnection)Connector.open("sms://"+address+":3333");
                    MessageConnection sender = (MessageConnection) Connector.open("sms://" + address);
                    TextMessage textMessage = (TextMessage) sender.newMessage(MessageConnection.TEXT_MESSAGE);
                    textMessage.setPayloadText(message);
                    sender.send(textMessage);

                    //TextMessage textMessage = (TextMessage)mc.newMessage(MessageConnection.TEXT_MESSAGE);
                    //textMessage.setPayloadText(message);

                    //textMessage.setAddress(address);
                    //mc.send(textMessage);

                    //Control.control.showAlert("Confirmacion", "Se envio mensaje: Destino=" + address + " mensaje=" + message);
                } catch (Exception e) {
                    Control.control.showAlert("Excepcion envio SMS", e.getMessage());
                }
            }
        }.start();



    }

    public void close() {
        try {
            mc.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
