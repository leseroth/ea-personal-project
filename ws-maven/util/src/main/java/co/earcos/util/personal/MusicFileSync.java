package co.earcos.util.personal;

import java.io.*;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author earcos
 */
public class MusicFileSync {

    private static final String FROM_DIR = "/Volumes/EA External HD/Musica/Inglés/";
    private static final String TO_DIR = "/Volumes/Datos/Musica/Inglés/";
    private TreeMap<String, File> fromMap;
    private TreeMap<String, File> toMap;

    public static void main(String... args) {
        MusicFileSync fileSync = new MusicFileSync();
        fileSync.execute();
    }

    private MusicFileSync() {
        fromMap = new TreeMap<String, File>();
        toMap = new TreeMap<String, File>();
    }

    private void execute() {
        File fromDirectory = new File(FROM_DIR);
        listFiles(fromMap, FROM_DIR, fromDirectory);

        File toDirectory = new File(TO_DIR);
        listFiles(toMap, TO_DIR, toDirectory);

        System.out.println("Compara from - to");
        genericCompareMap(FROM_DIR, fromMap, TO_DIR, toMap);
        System.out.println("Fin from-to");
    }

    private void listFiles(TreeMap<String, File> map, String relativeTo, File file) {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                listFiles(map, relativeTo, subFile);
            }
        } else {
            String filePath = file.getAbsolutePath();
            filePath = filePath.substring(relativeTo.length(), filePath.length());
            map.put(filePath, file);
        }
    }

    private void genericCompareMap(String fromDir, TreeMap<String, File> from, String toDir, TreeMap<String, File> to) {
        fromSearch:
        for (String fromKey : from.keySet()) {
            File fromFile = from.get(fromKey);
            if (to.containsKey(fromKey)) {
                File toFile = to.get(fromKey);
                if (fromFile.length() != toFile.length()) {
                    System.out.println("-- Este archivo es diferente: " + fromKey);
                }
            } else {
                createDirectory(fromKey, toDir);
                copyFile(fromKey, fromFile, toDir);
            }
        }
    }

    private void createDirectory(String fromKey, String toDir) {
        int indexOfFolder = fromKey.lastIndexOf(File.separator);
        if (indexOfFolder != -1) {
            String subDir = fromKey.substring(0, indexOfFolder);
            File directory = new File(toDir + subDir);
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("-- Se crea el directorio: " + directory.getAbsolutePath());
            }
        }
    }

    private void copyFile(String fromKey, File fromFile, String toDir) {
        File toFile = new File(toDir + fromKey);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(fromFile);
            fos = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("-- Se copio el archivo: " + fromKey);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MusicFileSync.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MusicFileSync.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    System.out.println("IOEXception " + e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.out.println("IOEXception " + e);
                }
            }
        }
    }
}
