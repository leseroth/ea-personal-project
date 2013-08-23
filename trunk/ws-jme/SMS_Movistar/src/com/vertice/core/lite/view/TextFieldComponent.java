package com.vertice.core.lite.view;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.TextField;

public class TextFieldComponent extends Component {
    public static final int NUMERIC = TextField.NUMERIC;
    public static final int EMAIL = TextField.EMAILADDR;
    public static final int ANY = TextField.ANY;
    public static final int PASSWORD = TextField.PASSWORD;
    public static final int PHONENUMBER = TextField.PHONENUMBER;

    private String text;
    private String title;
    private int lenght = 255, type = ANY;
    private javax.microedition.lcdui.TextBox textBox;
    private FormCanvas formCanvas;
    private Font font;

    public TextFieldComponent(FormCanvas formCanvas, String title, String text, int lenght, int type) {
        this.formCanvas = formCanvas;
        this.title = title;
        this.text = text;
        this.lenght = lenght;
        this.type = type;
        if (title == null) { title = ""; }

        font=Font.getFont(Font.FACE_SYSTEM,Font.STYLE_BOLD,Font.SIZE_MEDIUM);
        requiredHeight=font.getHeight()*2+5+CustomCanvas.SEPARATOR;
    }

    public boolean finalize() {
        title = text = null;
        textBox = null;
        return true;
    }

    public void setFocusedAction() {

        try {
            textBox = new javax.microedition.lcdui.TextBox(title, text, lenght, type);
        } catch (Exception e) {
            textBox = new javax.microedition.lcdui.TextBox(title, "", lenght, type);
        }
        textBox.addCommand(new Command("Ok", Command.OK, 1));
        textBox.addCommand(new Command("Cancelar", Command.CANCEL, 2));
        textBox.setCommandListener(new CommandListener() {

            public void commandAction(Command command, Displayable displayable) {
                if (command.getCommandType() == Command.OK) {
                    text = textBox.getString();
                }
                Constants.constants.getDisplay().setCurrent(formCanvas);
                textBox = null;
                System.gc();
            }
        });

        Constants.constants.getDisplay().setCurrent(textBox);
    }

    public String getString() { return text; }

    private String formatString() {
        if(type==(PASSWORD|NUMERIC)) {
            String hidden ="**********";
            return hidden.substring(0,text.length());
        }
        else
        { return text; }
    }

    public int paintComponent(int offsetX,int offsetY,Graphics g) {
        g.setColor(0x000080);
        g.setFont(font);
        g.drawString(title,CustomCanvas.LEFT_BORDER,offsetY,Graphics.LEFT|Graphics.TOP);
        g.drawString(formatString(),CustomCanvas.LEFT_BORDER+5,offsetY+font.getHeight()+4,Graphics.LEFT|Graphics.TOP);
        g.setColor(CustomCanvas.BACKGROUND_COLOR);
        g.fillRect(CustomCanvas.UTIL_W-CustomCanvas.RIGHT_BORDER-5,offsetY+font.getHeight()+4, CustomCanvas.SCREEN_W-CustomCanvas.UTIL_W+CustomCanvas.RIGHT_BORDER+5,font.getHeight());
        g.setColor(0x000080);
        g.drawRect(CustomCanvas.LEFT_BORDER+2,offsetY+font.getHeight()+2,CustomCanvas.UTIL_W-CustomCanvas.LEFT_BORDER-CustomCanvas.RIGHT_BORDER-4,font.getHeight()+2);

        if(selected) {
            g.setColor(0x30C020);
            g.drawRect(CustomCanvas.LEFT_BORDER,offsetY+font.getHeight(),CustomCanvas.UTIL_W-CustomCanvas.LEFT_BORDER-CustomCanvas.RIGHT_BORDER,font.getHeight()+6);
            g.drawRect(CustomCanvas.LEFT_BORDER+1,offsetY+font.getHeight()+1,CustomCanvas.UTIL_W-CustomCanvas.LEFT_BORDER-CustomCanvas.RIGHT_BORDER-2,font.getHeight()+4);
        }
        g.setColor(CustomCanvas.BACKGROUND_COLOR);
        g.fillRect(0, ANY, EMAIL, lenght);

        return requiredHeight;
    }
}
