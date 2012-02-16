package co.earcos.budget.view;

import co.earcos.budget.control.DayData;
import co.earcos.budget.util.Util;
import co.earcos.budget.util.Util.Account;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Erik
 */
public class DayPanel extends JPanel implements MouseListener {

  private static final String ICON_LOCATION = "src/main/resources/images/";
  private static final String ICON_FORMAT = ".png";
  private static final String INGRESO = "ingreso";
  private static final String EGRESO = "egreso";
  private DayData dayData;
  private MovementFrame movementFrame;
  private String holydays;

  public void init(DayData day) {
    dayData = day;
    holydays =
            "+2011-1-1+2011-1-10+2011-3-21+2011-4-21+2011-4-22+2011-6-6+2011-6-27+2011-7-4+2011-7-20+2011-8-15+2011-10-17+2011-11-7+2011-11-14+2011-12-8"
            + "+2012-1-9+2012-3-19+2012-4-5+2012-4-6+2012-5-1+2012-5-21+2012-6-11+2012-6-18+2012-7-2+2012-7-20+2012-8-7+2012-8-20+2012-10-15+2012-11-5+2012-11-12+2012-12-8+2012-12-25+";

    JLabel dayLabel = initDayLabel(dayData.getDate());
    List<JLabel> accountLabelList = new ArrayList<JLabel>();
    for (Account account : Account.values()) {
      if (!account.isCreditCard()) {
        accountLabelList.add(initValueLabel(dayData.getAccountDayTotal(account), account));
      }
    }

    Box mainBox = Box.createVerticalBox();
    Box dayBox = Box.createVerticalBox();
    Box accountBox = Box.createVerticalBox();

    dayBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    accountBox.setAlignmentX(Component.LEFT_ALIGNMENT);

    dayBox.add(dayLabel);
    for (JLabel jlabel : accountLabelList) {
      accountBox.add(jlabel);
    }

    mainBox.add(dayBox);
    mainBox.add(accountBox);
    add(mainBox);

    setBorder(BorderFactory.createLineBorder(Color.black));
    setBackground(Color.white);

    addMouseListener(this);
  }

  private JLabel initDayLabel(Calendar calendar) {
    JLabel label = new JLabel("" + calendar.get(Calendar.DATE));
    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
            || holydays.indexOf("+" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE) + "+") != -1) {
      label.setForeground(Color.red);
    }
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    return label;
  }

  private JLabel initValueLabel(double value, Account account) {
    JLabel label;
    if (value == 0) {
      label = new JLabel(".");
      label.setForeground(Color.white);
    } else {
      ImageIcon icon = createImageIcon(value > 0 ? INGRESO + account.getLabel() : EGRESO + account.getLabel());
      label = new JLabel(Util.getCurrencyValue(value < 0 ? -value : value), icon, JLabel.LEADING);
      label.setForeground(value < 0 ? Color.red : Color.black);
      label.setAlignmentX(Component.RIGHT_ALIGNMENT);
    }
    return label;
  }

  private ImageIcon createImageIcon(String path) {
    try {
      InputStream is = ClassLoader.getSystemResourceAsStream("images/" + path + ICON_FORMAT);
      Image image = ImageIO.read(is);
      return new ImageIcon(image);
    } catch (IOException ex) {
      System.err.println("No se encuentra el archivo: " + ICON_LOCATION + path + ICON_FORMAT+" <"+ex.getMessage()+">");
      return null;
    }
  }

  public void closeMovementFrame() {
    movementFrame.validateMovements();
    movementFrame.dispose();
    movementFrame = null;
  }

  @Override
  public void mouseClicked(MouseEvent event) {
    if (movementFrame == null) {
      movementFrame = new MovementFrame(dayData, this);
    }
    movementFrame.requestFocus();
  }

  @Override
  public void mouseEntered(MouseEvent event) {
    setBackground(new Color(0xe0, 0xe0, 0xe0));
  }

  @Override
  public void mouseExited(MouseEvent event) {
    setBackground(Color.white);
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }
}
