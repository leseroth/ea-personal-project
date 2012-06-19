package co.earcos.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author earcos
 */
public class FileUtilTest {

  private static final String IMAGE_FOLDER = "src/test/resources/images/";
  private static final String[] TEST_IMAGE_ARRAY = {"f-d-o-c", "f-j-p-g", "f-p-n-g", "f-t-x-t"};
  private static final String[] TEST_IMAGE_MIME_ARRAY = {"application/msword", "image/jpeg", "image/png", "text/plain"};

  @Test
  public void testGetMimeType() {
    for (int i = 0; i < TEST_IMAGE_ARRAY.length; i++) {
      String mimeType = FileUtil.getMimeType(IMAGE_FOLDER + TEST_IMAGE_ARRAY[i]);
      assertEquals(mimeType, TEST_IMAGE_MIME_ARRAY[i]);
    }
  }

  @Test
  public void testGetMimeTypeFromArray() {
    for (int i = 0; i < TEST_IMAGE_ARRAY.length; i++) {
      try {
        byte[] image = FileUtil.loadFileAsByteArray(IMAGE_FOLDER + TEST_IMAGE_ARRAY[i]);
        String mimeType = FileUtil.getMimeType(image);
        assertEquals(mimeType, TEST_IMAGE_MIME_ARRAY[i]);
      } catch (FileNotFoundException ex) {
        assertTrue(ex.getMessage(), false);
      } catch (IOException ex) {
        assertTrue(ex.getMessage(), false);
      }
    }
  }
}
