

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.*;
import com.jidesoft.utils.ProductNames;
import com.jidesoft.utils.SystemInfo;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import javax.swing.*;

public abstract class AbstractDemo
  implements Demo, ProductNames
{
  public static int a;

  public String getDescription()
  {
    return null;
  }

  public int hashCode()
  {
    return getName().hashCode();
  }

  public String toString()
  {
    return getName();
  }

  public Component getOptionsPanel() {
    return null;
  }

  public boolean isCommonOptionsPaneVisible() {
    return true;
  }

  public void dispose() {
  }

  public static JFrame showAsFrame(Demo paramDemo) {
    boolean bool = DemoData.c; JFrame localJFrame = new JFrame(paramDemo.getName() + " - JIDE  on JDK " + SystemInfo.getJavaVersion());

    localJFrame.setDefaultCloseOperation(3);

    Component localComponent = paramDemo.getDemoPanel();
    //JComponent localJComponent = a(localJFrame, paramDemo, localComponent);

    JPanel localJPanel = new JPanel(new BorderLayout());
    localJPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    localJPanel.add(localComponent, "Center");

    localJFrame.getContentPane().setLayout(new BorderLayout());
    localJFrame.getContentPane().add(localJPanel, "Center");
//    if (!bool) { if (localJComponent != null) {
//        localJFrame.getContentPane().add(new JScrollPane(localJComponent), "Before");
//      }
//
//      localJFrame.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(71, 11), "printMem");
//
//      localJFrame.getRootPane().getActionMap().put("printMem", new AbstractAction() {
//        public void actionPerformed(ActionEvent paramActionEvent) {
//          JideSwingUtilities.runGCAndPrintFreeMemory();
//        }
//      });
//      localJFrame.pack();
//    }

    return localJFrame;
  }


  public String[] getDemoSource()
  {
    return new String[] { getClass().getName() + ".java" };
  }

  public String getDemoFolder() {
    return "";
  }

  public int getAttributes() {
    return 0;
  }


}