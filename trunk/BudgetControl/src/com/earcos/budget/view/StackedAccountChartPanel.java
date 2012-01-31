package com.earcos.budget.view;

import com.earcos.budget.dao.ControlDao;
import com.earcos.budget.dao.DBConnection;
import com.earcos.budget.util.Util;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

public class StackedAccountChartPanel extends JPanel implements ActionListener {

    private ChartPanel chartPanel;
    private JButton currentMonth;
    private JButton lastYear;
    private Box mainBox;

    public StackedAccountChartPanel() {
        currentMonth = new JButton("Mes actual");
        lastYear = new JButton("Ultimo año");

        currentMonth.addActionListener(this);
        lastYear.addActionListener(this);

        mainBox = Box.createVerticalBox();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, Util.GAP, Util.GAP));
        buttonPanel.add(currentMonth);
        buttonPanel.add(lastYear);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        mainBox.add(buttonPanel);
        mainBox.add(Util.getBoxFiller());

        add(mainBox);
    }

    public void restartStackedAccountChartPanel() {
        restartStackedAccountChartPanel(ControlDao.CURRENT_MONTH);
    }

    private void restartStackedAccountChartPanel(int option) {
        if (chartPanel != null) {
            mainBox.remove(chartPanel);
        }

        chartPanel = initChartPanel(option);
        chartPanel.setPreferredSize(new Dimension(1100, 550));
        chartPanel.setMouseZoomable(false);
        chartPanel.setAlignmentX(CENTER_ALIGNMENT);

        mainBox.add(chartPanel);
        validate();
    }

    private ChartPanel initChartPanel(int option) {
        JFreeChart accountChart;

        if (option == ControlDao.LAST_YEAR) {
            accountChart = ChartFactory.createStackedBarChart3D(
                    "Activos", "Fecha", "Dinero", createDataset(option), PlotOrientation.VERTICAL, true, false, false);
        } else {
            accountChart = ChartFactory.createStackedAreaChart(
                    "Activos", "Fecha", "Dinero", createDataset(option), PlotOrientation.VERTICAL, true, false, false);
        }

        CategoryPlot localCategoryPlot = (CategoryPlot) accountChart.getPlot();
        localCategoryPlot.setForegroundAlpha(0.85F);

        CategoryAxis dateAxis = localCategoryPlot.getDomainAxis();
        dateAxis.setLowerMargin(0.0D);
        dateAxis.setUpperMargin(0.0D);
        dateAxis.setCategoryMargin(0.0D);
        dateAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        NumberAxis moneyAxis = (NumberAxis) localCategoryPlot.getRangeAxis();
        moneyAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        moneyAxis.setNumberFormatOverride(NumberFormat.getCurrencyInstance());

        if (option == ControlDao.LAST_YEAR) {
            BarRenderer localBarRenderer = (BarRenderer) localCategoryPlot.getRenderer();
            localBarRenderer.setDrawBarOutline(false);
            localBarRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            localBarRenderer.setBaseItemLabelsVisible(true);
            localBarRenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER));
            localBarRenderer.setItemLabelFont(new Font("Arial Narrow", Font.PLAIN, 10));
        }

        Font titleFont = accountChart.getTitle().getFont();
        accountChart.getTitle().setFont(new Font(titleFont.getFontName(), Font.BOLD, 16));

        return new ChartPanel(accountChart);
    }

    private CategoryDataset createDataset(int option) {
        DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();

        List<String[]> chartData = (new ControlDao().loadAccountChartData(DBConnection.getConnection(), option));
        DBConnection.closeConnection();

        for (String[] data : chartData) {
            localDefaultCategoryDataset.addValue(Double.parseDouble(data[0]), data[1], data[2]);
        }

        return localDefaultCategoryDataset;
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(currentMonth)) {
            restartStackedAccountChartPanel(ControlDao.CURRENT_MONTH);
        } else if (event.getSource().equals(lastYear)) {
            restartStackedAccountChartPanel(ControlDao.LAST_YEAR);
        }

    }
}
