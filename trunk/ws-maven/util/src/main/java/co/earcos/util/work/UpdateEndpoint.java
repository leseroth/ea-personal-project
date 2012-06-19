package co.earcos.util.work;

import co.earcos.util.FileUtil;
import co.earcos.util.FormattingTools;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateEndpoint {

    public static void main(String... args) {
        String filePath = "C:\\workspace\\icbs-bus-simulator-web\\src\\main\\java\\com";
        UpdateEndpoint ue = new UpdateEndpoint();
        ue.execute(filePath);
    }
    int counter = 0;

    private void execute(String filePath) {
        File projectPath = new File(filePath);
        List<File> fileList = new ArrayList<File>();
        FileUtil.loadList(fileList, projectPath.listFiles(), "", "Impl.java", true);
        System.out.println("Total = " + fileList.size());
        for (File file : fileList) {
            System.out.println("Nom: " + file.getAbsolutePath());
            String newFile = updateEndpoint(file);
            updateFile(file, newFile);
        }
    }

    private void updateFile(File file, String content) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write(content);
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    private String updateEndpoint(File file) {
        StringBuilder newFile = new StringBuilder();

        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            StringBuilder sb = new StringBuilder();

            while ((strLine = br.readLine()) != null) {
                if (strLine.indexOf("@WebService(") != -1) {
                    counter++;
                    System.out.println(counter + " > " + strLine);

                    String serviceName = strLine.substring(strLine.lastIndexOf(".") + 1, strLine.indexOf("\")"));

                    sb.append(FormattingTools.formatDecimal("00", counter));
                    sb.append(" > ");
                    sb.append(strLine);
                    sb.append(" > ");
                    sb.append(serviceName);
                    //System.out.println(sb.toString());

                    strLine = strLine.replaceAll(serviceName, serviceName + "\", name = \"" + serviceName + "Svc\", portName = \"" + serviceName + "Port");
                    //System.out.println("     "+strLine);
                }

                newFile.append(strLine);
                newFile.append("\n");
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return newFile.toString();
    }
}
