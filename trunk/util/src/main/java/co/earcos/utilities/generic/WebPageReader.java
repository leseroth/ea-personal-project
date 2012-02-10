package co.earcos.util.generic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class allows to read a web page to search info in it
 * @author earcos
 */
public class WebPageReader {

    private static String WEBPAGE = "http://leseroth.blogspot.com";

    public static void main(String... args) {
        readPage();
    }

    public static void readPage() {
        InputStreamReader isReader = null;
        BufferedReader bReader = null;

        try {
            URL url = new URL(WEBPAGE);
            isReader = new InputStreamReader(url.openStream());
            bReader = new BufferedReader(isReader);

            pageReader:
            for (;;) {
                String line = bReader.readLine();
                if (line == null) {
                    break pageReader;
                } else {
                    System.out.println(line);
                }
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(WebPageReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebPageReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
                if (isReader != null) {
                    isReader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(WebPageReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("***** FIN *****");
    }
}
