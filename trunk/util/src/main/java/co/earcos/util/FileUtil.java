package co.earcos.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

public class FileUtil {

  /**
   * Returns the mime type of the specified file
   *
   * @param fileName
   * @return
   */
  static public String getMimeType(String fileName) {
    String mimeType = null;
    try {
      mimeType = Magic.getMagicMatch(new File(fileName), true).getMimeType();
    } catch (MagicParseException ex) {
    } catch (MagicMatchNotFoundException ex) {
    } catch (MagicException ex) {
    }
    return mimeType;
  }

  /**
   *
   * Returns the mime type of the file loaded as a byte array
   *
   * @param data
   * @return
   */
  static public String getMimeType(byte[] data) {
    String mimeType = null;
    try {
      mimeType = Magic.getMagicMatch(data).getMimeType();
    } catch (MagicParseException ex) {
    } catch (MagicMatchNotFoundException ex) {
    } catch (MagicException ex) {
    }
    return mimeType;
  }

  public static byte[] loadFileAsByteArray(String fileName) throws FileNotFoundException, IOException {
    // Cargar el archivo
    InputStream is = new FileInputStream(new File(fileName));

    // Leer el archivo
    List<Byte> byteList = new ArrayList<Byte>();
    int b;
    while ((b = is.read()) != -1) {
      byteList.add((byte) b);
    }
    is.close();

    // Crear el arreglo de bytes
    byte[] file = new byte[byteList.size()];
    for (int i = 0; i < byteList.size(); i++) {
      file[i] = byteList.get(i);
    }

    return file;
  }
}
