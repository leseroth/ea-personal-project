package co.earcos.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class allows to read a web page to search info in it
 *
 * @author earcos
 */
public class WebPageUtil {

    private static Log log = LogFactory.getLog(WebPageUtil.class);

    public static List<String> readPage(String webpage) {
        InputStreamReader isReader = null;
        BufferedReader bReader = null;
        List<String> webPageContent = new ArrayList<String>();

        try {
            URL url = new URL(webpage);
            isReader = new InputStreamReader(url.openStream());
            bReader = new BufferedReader(isReader);

            pageReader:
            for (;;) {
                String line = bReader.readLine();
                if (line == null) {
                    break pageReader;
                } else {
                    webPageContent.add(line);
                }
            }

        } catch (MalformedURLException ex) {
            log.error("Url not valid: " + webpage, ex);
        } catch (IOException ex) {
            log.error("IO error reading: " + webpage, ex);
        } finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
                if (isReader != null) {
                    isReader.close();
                }
            } catch (IOException ex) {
                log.error("IO error while trying to close the connection", ex);
            }
        }

        return webPageContent;
    }
}
