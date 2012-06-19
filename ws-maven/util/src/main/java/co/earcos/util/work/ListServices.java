package co.earcos.util.work;

import co.earcos.util.FileUtil;
import co.earcos.util.FormattingTools;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListServices {

  public static void main(String... args) {
    String filePath = "C:\\workspaceBranch\\icbs-bus-simulator-web\\target\\generated\\src\\main\\java\\com";
    ListServices ue = new ListServices();
    ue.execute(filePath);
  }
  int counter = 0;

  private void execute(String filePath) {
    File projectPath = new File(filePath);
    List<File> fileList = new ArrayList<File>();
    FileUtil.loadList(fileList, projectPath.listFiles(), "", ".java", true);

    for (File file : fileList) {
      test(file);
    }
  }

  private void test(File file) {
    try {
      FileInputStream fstream = new FileInputStream(file);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String strLine;

      while ((strLine = br.readLine()) != null) {
        if (strLine.indexOf("@WebService(") != -1) {
          counter++;
          strLine = strLine.substring(strLine.indexOf("name = "), strLine.indexOf(")"));
          System.out.println(FormattingTools.formatDecimal("00", counter) + " > " + strLine);
          while ((strLine = br.readLine()) != null) {

            if (strLine.indexOf("@WebMethod") != -1) {
              strLine = "      Operacion ="+strLine.substring(strLine.indexOf("action = ")+8, strLine.indexOf(")"));
              System.out.print(strLine);
            }

            if (strLine.indexOf("@WebParam") != -1) {
              strLine = " > ServiceName ="+strLine.substring(strLine.indexOf("name = ")+6, strLine.indexOf(", targetNamespace = "));
              System.out.println(strLine);
            }
          }
        }
      }
      in.close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
}
