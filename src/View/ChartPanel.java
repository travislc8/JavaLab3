package src.View;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import src.Model.*;
import src.ViewModel.*;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class ChartPanel extends JPanel {
    Dimension panelDimension;
    DataTableModel dataTableModel;
    int xAxisIndex;
    int yAxisIndex;
    DefaultCategoryDataset dataset;

    public ChartPanel(Dimension dimension) {
        this.panelDimension = dimension;
        setBackground(Color.white);
        setPreferredSize(panelDimension);

        xAxisIndex = 9;
        yAxisIndex = 11;

    }

    public void setData(DataTableModel data) {
        this.removeAll();
        this.dataTableModel = data;
        updateDataset();
        setPanel();
        this.revalidate();
        this.repaint();
    }

    private void updateDataset() {

        var options = dataTableModel.getColumnOptions(xAxisIndex);
        int[] sum = new int[options.size()];
        int[] count = new int[options.size()];

        // finds the average of the values
        for (int i = 0; i < dataTableModel.getRowCount(); i++) {
            // get the catagory of the row
            String x_axis_name = dataTableModel.getCell(i, xAxisIndex);
            // get the category index for adding to the sum of the catagory
            int x_axis_name_index = options.indexOf(x_axis_name);
            // catches if the row name is not an option
            if (x_axis_name_index == -1) {
                System.out.println("Error");
                continue;
            }

            // gets the value for the row and adds it to the sum
            sum[x_axis_name_index] += Double.parseDouble(dataTableModel.getCell(i, yAxisIndex));
            // adds to the count for the category
            count[x_axis_name_index] += 1;
        }

        dataset = new DefaultCategoryDataset();
        // adds the values to the dataset
        for (int i = 0; i < options.size(); i++) {
            if (count[i] == 0) {
                count[i] = 1;
            }
            int average = sum[i] / count[i];
            dataset.addValue(average, "Year", options.get(i));
        }
    }

    private void setPanel() {
        // drop down for options
        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(panelDimension.width, 50));

        // JComboBox<String> xDropDown = new JComboBox<String>(x_axis_options);

        this.add(controlPanel);

        // chart setup
        JFreeChart chart = ChartFactory.createBarChart("Chart",
                dataTableModel.getColumnName(xAxisIndex),
                dataTableModel.getColumnName(yAxisIndex),
                dataset,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);

        org.jfree.chart.ChartPanel chartPanel = new org.jfree.chart.ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(panelDimension.width, panelDimension.height - 100));
        this.add(chartPanel);
    }

}
