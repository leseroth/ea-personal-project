/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.earcos.util.generic;

import java.awt.image.BufferedImage;
import java.io.IOException;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author earcos
 */
public class ImageUtilTest {

    private static final String IMAGE_DOWN = "src/test/resources/images/down.png";
    private static final String IMAGE_UP = "src/test/resources/images/up.png";
    private static final String TARGET_FOLDER = "target/";
    private static final int TARGET_WIDTH = 120;
    private static final int TARGET_HEIGHT = 120;

    @Test
    public void testImageUpscale() {
        try {
            BufferedImage originalImage = ImageUtil.getBufferedImageFromFile(IMAGE_UP);
            BufferedImage scaledImage = ImageUtil.getScaledInstance(originalImage, TARGET_WIDTH, TARGET_HEIGHT);
            ImageUtil.createImageFile(scaledImage, "png", TARGET_FOLDER + "upscaled.png");
        } catch (IOException ex) {
            assertTrue("No fue posible cargar las imagenes", false);
        }
    }

    @Test
    public void testImageDownscale() {
        try {
            BufferedImage originalImage = ImageUtil.getBufferedImageFromFile(IMAGE_DOWN);
            BufferedImage scaledImage = ImageUtil.getScaledInstance(originalImage, TARGET_WIDTH, TARGET_HEIGHT);
            ImageUtil.createImageFile(scaledImage, "png", TARGET_FOLDER + "downscaled.png");
        } catch (IOException ex) {
            assertTrue("No fue posible cargar las imagenes", false);
        }
    }
}
