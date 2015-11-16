package co.earcos.budget.view;

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
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.earcos.budget.control.DayData;
import co.earcos.budget.util.Constants;
import co.earcos.budget.util.Constants.Account;
import co.earcos.budget.util.Util;

/**
 * 
 * @author Erik
 */
public class DayPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = -7830724316276016221L;
	private static final String holydays;
	private static Log log = LogFactory.getLog(DayPanel.class);
	private DayData dayData;
	private MovementFrame movementFrame;

static{
holydays = 
"+20110101+20110110+20110321+20110421+20110422+20110501+20110606+20110627+20110704+20110704+20110720+20110807+20110815+20111017+20111107+20111114+20111208+20111225"+
"+20120101+20120109+20120319+20120401+20120405+20120406+20120408+20120501+20120521+20120611+20120618+20120702+20120720+20120807+20120820+20121015+20121105+20121112+20121208+20121225"+
"+20130101+20130107+20130324+20130325+20130328+20130329+20130331+20130501+20130513+20130603+20130610+20130701+20130720+20130807+20130819+20131014+20131104+20131111+20131208+20131225"+
"+20140101+20140106+20140324+20140417+20140418+20140501+20140602+20140623+20140630+20140630+20140720+20140807+20140818+20141013+20141103+20141117+20141208+20141225"+
"+20150101+20150112+20150323+20150402+20150403+20150501+20150518+20150608+20150615+20150629+20150720+20150807+20150817+20151012+20151102+20151116+20151208+20151225"+
"+20160101+20160111+20160321+20160324+20160325+20160501+20160509+20160530+20160606+20160704+20160720+20160807+20160815+20161017+20161107+20161114+20161208+20161225"+
"+20170101+20170109+20170320+20170413+20170414+20170501+20170529+20170619+20170626+20170703+20170720+20170807+20170821+20171016+20171106+20171113+20171208+20171225"+
"+20180101+20180108+20180319+20180329+20180330+20180501+20180514+20180604+20180611+20180702+20180720+20180807+20180820+20181015+20181105+20181112+20181208+20181225"+
"+20190101+20190107+20190325+20190418+20190419+20190501+20190603+20190624+20190701+20190701+20190720+20190807+20190819+20191014+20191104+20191111+20191208+20191225"+
"+20200101+20200106+20200323+20200409+20200410+20200501+20200525+20200615+20200622+20200629+20200720+20200807+20200817+20201012+20201102+20201116+20201208+20201225";
}

	public void init(DayData day) {
		dayData = day;
		JLabel dayLabel = initDayLabel(dayData.getDate());
		List<JLabel> accountLabelList = new ArrayList<JLabel>();
		for (Account account : Account.values()) {
			if (!account.isCreditCard()) {
				accountLabelList.add(initValueLabel(
						dayData.getAccountDayTotal(account), account));
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
		String date = String.format("%04d%02d%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || holydays.contains(date)) {
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
			ImageIcon icon = createImageIcon(value > 0 ? Constants.INGRESO
					+ account.getLabel() : Constants.EGRESO
					+ account.getLabel());
			label = new JLabel(Util.getCurrencyValue(account,
					value < 0 ? -value : value), icon, JLabel.LEADING);
			label.setForeground(value < 0 ? Color.red : Color.black);
			label.setAlignmentX(Component.RIGHT_ALIGNMENT);
		}
		return label;
	}

	private ImageIcon createImageIcon(String path) {
		try {
			InputStream is = Util.getResource("images/" + path
					+ Constants.ICON_FORMAT);
			Image image = ImageIO.read(is);
			return new ImageIcon(image);
		} catch (IOException ex) {
			log.warn("No se encuentra el archivo: " + path
					+ Constants.ICON_FORMAT, ex);
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