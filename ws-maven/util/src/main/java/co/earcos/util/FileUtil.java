package co.earcos.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {

  private static Log log = LogFactory.getLog(FileUtil.class);

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
      log.error(ex);
    } catch (MagicMatchNotFoundException ex) {
      log.error(ex);
    } catch (MagicException ex) {
      log.error(ex);
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
      log.error(ex);
    } catch (MagicMatchNotFoundException ex) {
      log.error(ex);
    } catch (MagicException ex) {
      log.error(ex);
    }
    return mimeType;
  }

  /**
   * Loads a file by its name and returns a byte array
   *
   * @param fileName
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
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

  /**
   * Loads the list of files into the specified list
   *
   * @param fileList List that is going to contain all the files
   * @param rawFileArray
   * @param prefix
   * @param sufix
   * @param asc
   */
  public static void loadList(List<File> fileList, File[] rawFileArray, String prefix, String sufix, boolean asc) {
    if (!asc) {
      Collections.reverse(Arrays.asList(rawFileArray));
    }
    for (File rawFile : rawFileArray) {
      if (rawFile.isDirectory()) {
        loadList(fileList, rawFile.listFiles(), prefix, sufix, asc);
      } else {
        String fileName = rawFile.getName();
        if (fileName.startsWith(prefix) && fileName.endsWith(sufix)) {
          log.debug("File: " + rawFile.getAbsolutePath() + " : " + rawFile.getName());
          fileList.add(rawFile);
        }
      }
    }
  }

  public static void copyFile(String fromDir, String fromFileName, String toDir, String toFileName) {
    File toFile = new File(toDir + toFileName);

    FileInputStream fis = null;
    FileOutputStream fos = null;
    try {
      fis = new FileInputStream(fromDir + fromFileName);
      fos = new FileOutputStream(toFile);
      byte[] buffer = new byte[4096];
      int bytesRead;

      while ((bytesRead = fis.read(buffer)) != -1) {
        fos.write(buffer, 0, bytesRead);
      }

      log.info("File copied: " + toDir + toFileName);
    } catch (FileNotFoundException ex) {
      log.error("The file was not found", ex);
    } catch (IOException ex) {
      log.error("Error reading the file", ex);
    } finally {
      if (fis != null) {
        try {
          fis.close();
        } catch (IOException e) {
          log.error("Error while closing the input connection", e);
        }
      }
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e) {
          log.error("Error while closing the output connection", e);
        }
      }
    }
  }

  public static void createFile(String fileName, byte[] rawFile) throws FileNotFoundException, IOException {
    FileOutputStream fos = new FileOutputStream(fileName);
    fos.write(rawFile);
    fos.close();
  }
}
