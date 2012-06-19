
import java.io.File;

/**
 *
 * @author earcos
 */
public class FileTree {

  File file;

  public FileTree(File file) {
    this.file = file;
  }

  public File getFile() {
    return file;
  }

  @Override
  public String toString() {
    String fileName = "";
    if (file != null) {
      fileName = file.getName();
      int index = fileName.lastIndexOf(File.separator);
      fileName = index == -1 ? fileName : fileName.substring(index);
    }
    return fileName;
  }
}
