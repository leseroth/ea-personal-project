/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vertice.core.lite.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Erik
 */
public class ImageComponent extends Component {
    public static int ALIGN_LEFT=Graphics.TOP|Graphics.LEFT,ALIGN_RIGHT=Graphics.TOP|Graphics.LEFT,ALIGN_CENTER=Graphics.TOP|Graphics.HCENTER;

    Image image;
    int align;
    int offX;

    public ImageComponent(Image image, int align) {
        this.image = image;
        this.align = align;

        if(align==ALIGN_LEFT)        { offX=CustomCanvas.LEFT_BORDER; }
        else if(align==ALIGN_RIGHT)  { offX=CustomCanvas.RIGHT_BORDER; }
        else if(align==ALIGN_CENTER) { offX=CustomCanvas.UTIL_W/2; }

        navegable=false;
        requiredHeight=image.getHeight()+CustomCanvas.SEPARATOR;
    }

    public int paintComponent(int offsetX, int offsetY, Graphics g) {
        g.drawImage(image,offX,offsetY,align);
        return requiredHeight;
    }

    public void setFocusedAction() {}
}
