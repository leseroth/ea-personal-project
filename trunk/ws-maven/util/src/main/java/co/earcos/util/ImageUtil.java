package co.earcos.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public abstract class ImageUtil {

    public static BufferedImage getScaledInstance(BufferedImage originalImage, int targetWidth,
            int targetHeight) {

        BufferedImage scaledInstance;
        boolean sameSize = originalImage.getHeight() == targetHeight && originalImage.getWidth() == targetWidth;

        if (sameSize) {
            scaledInstance = originalImage;
        } else {
            boolean upscaling = originalImage.getHeight() < targetHeight || originalImage.getWidth() < targetWidth;
            Object hint = upscaling ? RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR : RenderingHints.VALUE_INTERPOLATION_BILINEAR;
            scaledInstance = getScaledInstance(originalImage, targetWidth, targetHeight, hint, !upscaling);
        }

        return scaledInstance;
    }

    public static BufferedImage getBufferedImageFromFile(String fileName) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(fileName));
        return bufferedImage;
    }

    public static BufferedImage getBufferedImageFromByteArray(byte[] imageInByte) throws IOException {
        InputStream in = new ByteArrayInputStream(imageInByte);
        BufferedImage bufferedImage = ImageIO.read(in);
        return bufferedImage;
    }

    public static byte[] getByteArrayFromBufferedImage(BufferedImage bufferedImage, String fileFormat) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, fileFormat, baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }

    public static void createImageFile(BufferedImage bufferedImage, String format, String fileName) throws IOException {
        ImageIO.write(bufferedImage, format, new File(fileName));
    }

    /**
     * Convenience method that returns a scaled instance of the provided {@code BufferedImage}.
     *
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance, in pixels
     * @param targetHeight the desired height of the scaled instance, in pixels
     * @param hint one of the rendering hints that corresponds to
     *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @param higherQuality if true, this method will use a multi-step scaling
     * technique that provides higher quality than the usual one-step technique
     * (only useful in down scaling cases, where
     *    {@code targetWidth} or {@code targetHeight} is smaller than the original
     * dimensions, and generally only when the {@code BILINEAR} hint is
     * specified)
     * @return a scaled version of the original {@code BufferedImage}
     */
    public static BufferedImage getScaledInstance(BufferedImage img,
            int targetWidth,
            int targetHeight,
            Object hint,
            boolean higherQuality) {
        int type = (img.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage) img;
        int w, h;

        if (higherQuality && (img.getWidth() > targetWidth || img.getHeight() > targetHeight)) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }
}
