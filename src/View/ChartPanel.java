package src.View;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import src.Model.*;
import src.ViewModel.*;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class that creates a JPanel that will display a set of data in a chart
 */
public class ChartPanel extends JPanel {
    Dimension panelDimension;
    DataTableModel dataTableModel;
    int xAxisIndex;
    int yAxisIndex;
    DefaultCategoryDataset dataset;
    JComboBox<String> xDropDown;
    JComboBox<String> yDropDown;
    JPanel controlPanel;

    /**
     * ChartPanel Constructor
     * Does not set the data
     * 
     * @param dimension the dimension that the chart panel should be
     */
    public ChartPanel(Dimension dimension) {
        this.panelDimension = dimension;
        setBackground(Color.white);
        setPreferredSize(panelDimension);

        // defualt vaules for axis of graph
        xAxisIndex = 9;
        yAxisIndex = 11;

    }

    /**
     * Sets the data of the charta and displays the chart
     *
     * @param data data that the chart will display
     */
    public void setData(DataTableModel data) {
        this.dataTableModel = data;
        if (data.getColumnCount() < 9) {
            xAxisIndex = 2;
            yAxisIndex = 1;
        }

        setPanel();
    }

    /**
     * Sets the data and initializes the panel
     * Do not call to update the data as it will reset the input
     */
    private void setPanel() {
        updateDataset();
        setControlPanel();
        updateChart();
    }

    /**
     * Method to update the chart
     * Will not reset any of the user selections
     * Should be called anytime updateDataset is called
     */
    private void updatePanel() {
        this.removeAll();
        this.add(controlPanel);
        updateChart();
        this.revalidate();
        this.repaint();
    }

    /**
     * Helper function to display the JFreeChart component
     * Does not display the chart
     */
    private void updateChart() {
        // chart setup
        JFreeChart chart = ChartFactory.createBarChart("Data Chart",
                dataTableModel.getColumnName(xAxisIndex),
                dataTableModel.getColumnName(yAxisIndex),
                dataset,
                PlotOrientation.VERTICAL,
                false,
                false,
                false);

        // adds the chart to the panel
        org.jfree.chart.ChartPanel chartPanel = new org.jfree.chart.ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(panelDimension.width, panelDimension.height));
        this.add(chartPanel);
    }

    /**
     * Method to update the data set
     * Will update the chart and display the updated chart
     *
     * @param data the new data set
     */
    public void updatDataset(DataTableModel data) {
        this.dataTableModel = data;
        updateDataset();
        updatePanel();
    }

    /**
     * Helper method to update the dataset
     * Updates the dataset used for the chart and the stats for the StatsPanel
     */
    private void updateDataset() {

        // variable initialization
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

    /**
     * Sets the control panel based on the dataset
     */
    private void setControlPanel() {
        // drop down for options
        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(panelDimension.width, 50));

        // set the x-axis options
        var column_names = dataTableModel.getColumnNames();
        var string_column_names = new ArrayList<String>();
        var numeric_column_names = new ArrayList<String>();

        string_column_names.add("Select an Option");
        numeric_column_names.add("Select an Option");

        for (int i = 0; i < column_names.size(); i++) {
            // seperates the columns based on string and numeric types
            if (dataTableModel.getColumnClass(i) == String.class) {
                string_column_names.add(column_names.get(i));
            } else {
                numeric_column_names.add(column_names.get(i));
            }
        }

        // x axis drop down
        String[] x_axis_options = new String[string_column_names.size()];
        x_axis_options = string_column_names.toArray(x_axis_options);
        xDropDown = new JComboBox<String>(x_axis_options);
        xDropDown.addItemListener(l -> {
            setAxisCatagory();
        });

        // y axis drop down
        String[] y_axis_options = new String[numeric_column_names.size()];
        y_axis_options = numeric_column_names.toArray(y_axis_options);
        yDropDown = new JComboBox<String>(y_axis_options);
        yDropDown.addItemListener(l -> {
            setAxisCatagory();
        });

        // add the elements to the panel
        controlPanel.add(new JLabel("X-Axis Catagory"));
        controlPanel.add(yDropDown);
        controlPanel.add(new JLabel("Y-Axis Catagory"));
        controlPanel.add(xDropDown);
        this.add(controlPanel);
    }

    /**
     * Method to set a new x or y axis catagory based on user input
     */
    private void setAxisCatagory() {
        var columnNames = dataTableModel.getColumnNames();

        // loops through the columns and sets the axis catagories based on the
        // user input
        for (int i = 0; i < columnNames.size(); i++) {
            if (columnNames.get(i).equals(xDropDown.getSelectedItem())) {
                xAxisIndex = i;
            } else if (columnNames.get(i).equals(yDropDown.getSelectedItem())) {
                yAxisIndex = i;
            }
        }

        // updates the chart to reflect the new changes
        updateDataset();
        updatePanel();
    }
}
