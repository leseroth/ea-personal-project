import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.CurrencyConverter;
import com.jidesoft.converter.DefaultObjectConverter;
import com.jidesoft.grouper.DefaultObjectGrouper;
import com.jidesoft.grouper.GrouperContext;
import com.jidesoft.grouper.date.DateYearGrouper;
import com.jidesoft.icons.IconsFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class DemoData
{
  public static final String[] TITLES = { "Mail", "Calendar", "Contacts", "Tasks", "Notes", "Folder List", "Shortcuts", "Journal" };

  public static final int[] MNEMONICS = { 77, 67, 79, 84, 78, 70, 83, 74 };

  public static final ImageIcon[] ICONS = { IconsFactory.getImageIcon(DemoData.class, "icons/email.gif"), IconsFactory.getImageIcon(DemoData.class, "icons/calendar.gif"), IconsFactory.getImageIcon(DemoData.class, "icons/contacts.gif"), IconsFactory.getImageIcon(DemoData.class, "icons/tasks.gif"), IconsFactory.getImageIcon(DemoData.class, "icons/notes.gif"), IconsFactory.getImageIcon(DemoData.class, "icons/folder.gif"), IconsFactory.getImageIcon(DemoData.class, "icons/shortcut.gif"), IconsFactory.getImageIcon(DemoData.class, "icons/journal.gif") };

  public static final ImageIcon[] ICONS_LARGE = { IconsFactory.getImageIcon(DemoData.class, "icons/email_24x24.png"), IconsFactory.getImageIcon(DemoData.class, "icons/calendar_24x24.png"), IconsFactory.getImageIcon(DemoData.class, "icons/contacts_24x24.png"), IconsFactory.getImageIcon(DemoData.class, "icons/tasks_24x24.png"), IconsFactory.getImageIcon(DemoData.class, "icons/notes_24x24.png"), IconsFactory.getImageIcon(DemoData.class, "icons/folder_24x24.png"), IconsFactory.getImageIcon(DemoData.class, "icons/shortcut_24x24.png"), IconsFactory.getImageIcon(DemoData.class, "icons/journal_24x24.png") };

  static String[] a = { "Symbol", "Name", "Last", "Change", "Volume" };

  static Object[][] b = { { "AA", "ALCOA INC", "32.88", "+0.53 (1.64%)", "4156200" }, { "AIG", "AMER INTL GROUP", "69.53", "-0.58 (0.83%)", "4369200" }, { "AXP", "AMER EXPRESS CO", "48.90", "-0.35 (0.71%)", "4103600" }, { "BA", "BOEING CO", "49.14", "-0.18 (0.36%)", "3573700" }, { "C", "CITIGROUP", "44.21", "-0.89 (1.97%)", "28594900" }, { "CAT", "CATERPILLAR INC", "79.40", "+0.62 (0.79%)", "1458200" }, { "DD", "DU PONT CO", "42.62", "-0.14 (0.33%)", "1832700" }, { "DIS", "WALT DISNEY CO", "23.87", "-0.32 (1.32%)", "4443600" }, { "GE", "GENERAL ELEC CO", "33.37", "+0.24 (0.72%)", "31429500" }, { "GM", "GENERAL MOTORS", "43.94", "-0.20 (0.45%)", "3722100" }, { "HD", "HOME DEPOT INC", "34.33", "-0.18 (0.52%)", "5367900" }, { "HON", "HONEYWELL INTL", "35.70", "+0.23 (0.65%)", "4092100" }, { "HPQ", "HEWLETT-PACKARD", "19.65", "-0.25 (1.26%)", "11003000" }, { "IBM", "INTL BUS MACHINE", "84.02", "-0.11 (0.13%)", "6880500" }, { "INTC", "INTEL CORP", "23.15", "-0.23 (0.98%)", "95177008" }, { "JNJ", "JOHNSON&JOHNSON", "55.35", "-0.57 (1.02%)", "5428000" }, { "JPM", "JP MORGAN CHASE", "36.00", "-0.45 (1.23%)", "12135300" }, { "KO", "COCA COLA CO", "50.84", "-0.32 (0.63%)", "4143600" }, { "MCD", "MCDONALDS CORP", "27.91", "+0.12 (0.43%)", "6110800" }, { "MMM", "3M COMPANY", "88.62", "+0.43 (0.49%)", "2073800" }, { "MO", "ALTRIA GROUP", "48.20", "-0.80 (1.63%)", "6005500" }, { "MRK", "MERCK & CO", "44.71", "-0.97 (2.12%)", "5472100" }, { "MSFT", "MICROSOFT CP", "27.87", "-0.26 (0.92%)", "46717716" }, { "PFE", "PFIZER INC", "32.58", "-1.43 (4.20%)", "28783200" }, { "PG", "PROCTER & GAMBLE", "55.01", "-0.07 (0.13%)", "5538400" }, { "SBC", "SBC COMMS", "23.00", "-0.54 (2.29%)", "6423400" }, { "UTX", "UNITED TECH CP", "91.00", "+1.16 (1.29%)", "1868600" }, { "VZ", "VERIZON COMMS", "34.81", "-0.35 (1.00%)", "4182600" }, { "WMT", "WAL-MART STORES", "52.33", "-0.25 (0.48%)", "6776700" }, { "XOM", "EXXON MOBIL", "45.32", "-0.14 (0.31%)", "7838100" } };
  public static boolean c;

 public static TreeModel createSongTreeModel()
  {
    boolean bool = c; DefaultMutableTreeNode localDefaultMutableTreeNode1 = new DefaultMutableTreeNode("Songs");
    DefaultTreeModel localDefaultTreeModel = new DefaultTreeModel(localDefaultMutableTreeNode1);
    HashMap localHashMap = new HashMap();
    try
    {
      InputStream localInputStream = DemoData.class.getClassLoader().getResourceAsStream("Library.txt.gz");
      if (localInputStream == null) {
        return null;
      }
      GZIPInputStream localGZIPInputStream = new GZIPInputStream(localInputStream);
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localGZIPInputStream, "UTF-8"));
      localBufferedReader.readLine();
      do {
        String str1 = localBufferedReader.readLine();
        if ((str1 == null) || ((!bool) && ((str1.length() == 0) &&
          (!bool))))
          break;
        String[] arrayOfString = str1.split("\t");
        String str2 = "";
        String str3 = "";
        if (!bool) if (arrayOfString.length >= 1) {
            str2 = arrayOfString[0];
          }
        if (!bool) { if (arrayOfString.length >= 2) {
            str2 = str2 + " - " + arrayOfString[1];
          }if (bool); } else if (arrayOfString.length >= 3) {
          str3 = arrayOfString[2];
        }

        DefaultMutableTreeNode localDefaultMutableTreeNode2 = (DefaultMutableTreeNode)localHashMap.get(str3);
        if (!bool) if (localDefaultMutableTreeNode2 == null) {
            localDefaultMutableTreeNode2 = new DefaultMutableTreeNode(str3);
            localHashMap.put(str3, localDefaultMutableTreeNode2);
            localDefaultMutableTreeNode1.add(localDefaultMutableTreeNode2);
          }
        localDefaultMutableTreeNode2.add(new DefaultMutableTreeNode(str2));
      }
      while (!bool);
      return localDefaultTreeModel;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }

  public static TableModel createSongTableModel()
  {
    boolean bool = c;
    try { InputStream localInputStream = DemoData.class.getClassLoader().getResourceAsStream("Library.txt.gz");
      if (localInputStream == null) {
        return null; } GZIPInputStream localGZIPInputStream = new GZIPInputStream(localInputStream);
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localGZIPInputStream, "UTF-8"));
      Vector localVector1 = new Vector();
      Vector localVector2 = new Vector();

      String str1 = localBufferedReader.readLine();
      String[] arrayOfString1 = str1.split("\t");
      localVector2.addAll(Arrays.asList(arrayOfString1));
      label178: label212: label246:
      do { String str2 = localBufferedReader.readLine();
        if ((str2 == null) || ((!bool) && ((str2.length() == 0) &&
          (!bool))))
          break;
        String[] arrayOfString2 = str2.split("\t");
        Vector localVector3 = new Vector();
        if (!bool) if (arrayOfString2.length < 1) {
            localVector3.add(null); if (!bool) break label178;
          } localVector3.add(arrayOfString2[0]);
        if (!bool) if (arrayOfString2.length < 2) {
            localVector3.add(null); if (!bool) break label212;
          } localVector3.add(arrayOfString2[1]);
        if (!bool) if (arrayOfString2.length < 3) {
            localVector3.add(null); if (!bool) break label246;
          } localVector3.add(arrayOfString2[2]);
        localVector1.add(localVector3);
      }
      while (!bool);
      label280: label314: label349: return new DefaultTableModel(localVector1, localVector2);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }

  public static class SalesObjectGrouper extends DefaultObjectGrouper
  {
    public static final GrouperContext CONTEXT = new GrouperContext("Sales");

    public Object getValue(Object paramObject)
    {
      boolean bool = DemoData.c; if ((bool) || ((paramObject instanceof Number))) {
        double d = ((Number)paramObject).doubleValue();
        if (!bool) if (d < 100.0D) {
            return Integer.valueOf(0);
          }
        if (!bool) if (d < 1000.0D) {
            return Integer.valueOf(1);
          }
        if (!bool) if (d < 10000.0D) {
            return Integer.valueOf(2);
          }

        return Integer.valueOf(3);
      }

      return null;
    }

    public Class<?> getType()
    {
      return Integer.class;
    }

    public ConverterContext getConverterContext()
    {
      return DemoData.SalesConverter.CONTEXT;
    }
  }

  public static class SalesConverter extends DefaultObjectConverter
  {
    public static ConverterContext CONTEXT = new ConverterContext("Sales");

    public String toString(Object paramObject, ConverterContext paramConverterContext)
    {
      if ((DemoData.c) || ((paramObject instanceof Integer))) {
        int i = ((Integer)paramObject).intValue();
        switch (i) {
        case 0:
          return "From 0 to 100";
        case 1:
          return "From 100 to 1000";
        case 2:
          return "From 1000 to 10000";
        case 3:
          return "Greater than 10000";
        }
      }
      return null;
    }

    public boolean supportFromString(String paramString, ConverterContext paramConverterContext)
    {
      return false;
    }
  }
}