package co.earcos.util;

import java.io.File;
import org.apache.commons.codec.binary.Base64;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class EncrypterUtilTest {

  private static final String IMAGE_FOLDER = "src/test/resources/images/";
  private static final String[] TEST_IMAGE_ARRAY = {"f-j-p-g", "f-d-o-c", "f-p-n-g", "f-t-x-t", "down.png", "up.png"};
  private static final String TARGET_FOLDER = "target/encoding/";

  @BeforeClass
  public static void beforeClass() {
    File targetDir = new File("target/encoding");
    if (!targetDir.exists()) {
      targetDir.mkdir();
    }
  }

  @Test
  public void testAES() {
    try {
      for (String fileName : TEST_IMAGE_ARRAY) {

        byte[] rawFile = FileUtil.loadFileAsByteArray(IMAGE_FOLDER + fileName);
        // long oTime = System.currentTimeMillis();
        byte[] encodedFile = EncrypterUtil.encodeFile(rawFile, IMAGE_FOLDER, fileName, TARGET_FOLDER);
        // long eTime = System.currentTimeMillis();
        byte[] decodedFile = EncrypterUtil.decodeFile(encodedFile, IMAGE_FOLDER, fileName, TARGET_FOLDER);
        // long dTime = System.currentTimeMillis();

        FileUtil.createFile(TARGET_FOLDER + "Enc_AES_" + fileName, encodedFile);
        FileUtil.createFile(TARGET_FOLDER + "Dec_AES_" + fileName, decodedFile);

        System.err.println("AES " + fileName + " O:" + rawFile.length + " E:" + encodedFile.length + " D:" + decodedFile.length);
        //System.err.println("  Time Enc:" + (eTime - oTime) + " Dec:" + (dTime - eTime));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      assertTrue("No fue posible cargar las imagenes", false);
    }
  }

  @Test
  public void testBase64() {
    try {
      Base64 base64 = new Base64();

      for (String fileName : TEST_IMAGE_ARRAY) {

        byte[] rawFile = FileUtil.loadFileAsByteArray(IMAGE_FOLDER + fileName);
        byte[] encodedFile = base64.encode(rawFile);
        byte[] decodedFile = base64.decode(encodedFile);

        FileUtil.createFile(TARGET_FOLDER + "Enc_B64_" + fileName, encodedFile);
        FileUtil.createFile(TARGET_FOLDER + "Dec_B64_" + fileName, decodedFile);

        System.err.println("B64 " + fileName + " O:" + rawFile.length + " E:" + encodedFile.length + " D:" + decodedFile.length);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      assertTrue("No fue posible cargar las imagenes", false);
    }
  }
}
