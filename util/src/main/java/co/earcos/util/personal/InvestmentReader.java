package co.earcos.util.personal;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 *
 * @author Erik
 */
public class InvestmentReader extends JFrame implements ActionListener {

    // Menu
    private JMenu menu;
    private JMenuBar menuBar;
    private JMenuItem exitItem;
    // Consola
    private JTextArea consoleArea;
    private JButton executeButton;
    // Constantes
    private static final int GAP = 4;
    private static final char TAB = 9;
    private static final String[] ACCOUNTS = {"Fiducuenta", "Indeaccion", "Acciones Uniaccion"};
    // Robot
    private Robot robot;
    private static final int NUMERIC_KEYS[] = {
        KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4,
        KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9
    };
    
    private InvestmentReader(String title) {
        super(title);
        
        menuBar = new JMenuBar();
        menu = new JMenu();
        exitItem = new JMenuItem();
        menu.setText("Opciones");
        exitItem.setText("Salir");
        exitItem.addActionListener(this);
        menu.add(exitItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        
        consoleArea = new JTextArea();
        consoleArea.setLineWrap(true);
        consoleArea.setEditable(true);
        consoleArea.setWrapStyleWord(true);
        JScrollPane consoleScroll = new JScrollPane(consoleArea);
        consoleScroll.setPreferredSize(new Dimension(700, 400));
        
        executeButton = new JButton("Cargar Info");
        executeButton.addActionListener(this);
        executeButton.setAlignmentX(CENTER_ALIGNMENT);
        
        Box mainBox = Box.createVerticalBox();
        mainBox.add(getBoxFiller());
        mainBox.add(consoleScroll);
        mainBox.add(getBoxFiller());
        mainBox.add(executeButton);
        mainBox.setAlignmentX(CENTER_ALIGNMENT);
        JPanel mainPanel = new JPanel();
        mainPanel.add(mainBox);
        
        setContentPane(mainPanel);
    }

    /**
     * Crea y carga la interfaz grafica
     */
    public static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        InvestmentReader frame = new InvestmentReader("Erik Arcos - Analizador de Tasas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        centerFrame(frame);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(exitItem)) {
            System.exit(0);
        } else if (event.getSource().equals(executeButton)) {
            try {
                loadData(consoleArea.getText());
                System.exit(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(InvestmentReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AWTException ex) {
                Logger.getLogger(InvestmentReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private String normalizeString(String input) {
        String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").trim();
    }
    
    private void loadData(String data) throws InterruptedException, AWTException {
        data = data.replaceAll("[\\\n]", "" + TAB);
        String[] dataArray = data.split("" + TAB);
        int accIndex = 0;
        
        Thread.sleep(5000);
        robot = new Robot();
        
        searchInfo:
        for (int i = 0; i < dataArray.length; i++) {
            String dataString = normalizeString(dataArray[i]);
            
            if (dataString.equals(ACCOUNTS[accIndex])) {
                i = i + 3;
                
                dataString = normalizeString(dataArray[i]);
                dataString = dataString.replaceAll("[\\.]", "").replaceAll("[,]", ".").trim();
                for (char c : dataString.toCharArray()) {
                    if (c == '.') {
                        pressAndReleaseKey(KeyEvent.VK_PERIOD);
                    } else {
                        pressAndReleaseKey(NUMERIC_KEYS[Integer.parseInt(c + "")]);
                    }
                }
                pressAndReleaseKey(KeyEvent.VK_TAB);
                
                accIndex++;
                if (accIndex == ACCOUNTS.length) {
                    break searchInfo;
                }
            }
        }
    }
    
    private void pressAndReleaseKey(int key){
        robot.keyPress(key);
        robot.keyRelease(key);
    }

    /**
     * Centra un frame dentro de la pantalla
     * @param frame Frame a centrar
     */
    public static void centerFrame(JFrame frame) {
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getPreferredSize();
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2 - 15);
    }

    /**
     * Crea un filler para mejorar la interfaz grafica
     * @return 
     */
    public static Box.Filler getBoxFiller() {
        Dimension filler = new Dimension(GAP, GAP);
        Box.Filler boxFiller = new Box.Filler(filler, filler, filler);
        return boxFiller;
    }

    /**
     * Inicia la interfaz grafica
     * @param args
     */
    public static void main(String... args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
