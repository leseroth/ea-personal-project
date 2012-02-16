package co.earcos.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author earcos
 */
public class FileLister {

    public static void main(String... args) {
        FileLister fl = new FileLister();
        File file = new File("C:\\workspace\\icbs-commons-business\\src\\test\\resources\\db");
        List<File> fileList = new ArrayList<File>();

        fl.loadList(fileList, file.listFiles(), "nuke", "sql", false);
        fl.loadList(fileList, file.listFiles(), "install", "sql", true);
        fl.loadList(fileList, file.listFiles(), "populate", "sql", true);
    }

    private void loadList(List<File> fileList, File[] rawFileArray, String prefix, String sufix, boolean asc) {
        if (!asc) {
            Collections.reverse(Arrays.asList(rawFileArray));
        }
        for (File rawFile : rawFileArray) {
            if (rawFile.isDirectory()) {
                loadList(fileList, rawFile.listFiles(), prefix, sufix, asc);
            } else {
                String fileName = rawFile.getName();
                if (fileName.startsWith(prefix) && fileName.endsWith(sufix)) {
                    System.out.println("File: " + rawFile.getAbsolutePath() + " : " + rawFile.getName());
                    fileList.add(rawFile);
                }
            }
        }
    }
}
