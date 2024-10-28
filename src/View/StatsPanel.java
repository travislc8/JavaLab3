package src.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import src.Model.*;
import src.ViewModel.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatsPanel extends JPanel {
    Dimension panelDimension;
    JLabel messageLabel;
    DataTableModel data;
    int Average;

    public StatsPanel(Dimension dimension) {
        panelDimension = dimension;
        setPreferredSize(panelDimension);
        this.setLayout(new GridLayout(0, 1));
    }

    public void updateData(DataTableModel data) {
        this.removeAll();
        this.data = data;
        setTitle();
        updateStats();
        this.revalidate();
        this.repaint();
    }

    private void setTitle() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Stats For Data"));
        this.add(panel);
    }

    private void updateStats() {
        // set the x-axis options
        var column_names = data.getColumnNames();
        var numeric_column_names = new ArrayList<String>();
        var numeric_column_numbers = new ArrayList<Integer>();

        // gets the columns that stats can be made for
        for (int i = 0; i < column_names.size(); i++) {
            // seperates the columns based on string and numeric types
            if (data.getColumnClass(i) != String.class) {
                numeric_column_names.add(column_names.get(i));
                numeric_column_numbers.add(i);
            }
        }

        // calculates the stats
        var sum = new ArrayList<Double>();
        var min = new ArrayList<Double>();
        var max = new ArrayList<Double>();
        for (int i = 0; i < numeric_column_names.size(); i++) {
            sum.add(0.0);
            min.add(Double.MAX_VALUE);
            max.add(Double.MIN_VALUE);
        }
        Double count_temp = 0.0, sum_temp = 0.0;
        for (int row = 0; row < data.getRowCount(); row++) {
            for (int j = 0; j < numeric_column_names.size(); j++) {
                int column = numeric_column_numbers.get(j);
                double value = 0.0;
                try {
                    value = Double.parseDouble(data.getCell(row, column));
                } catch (Exception e) {
                    System.out.println("error parsing double in StatsPanel.java");
                }
                sum_temp = value;
                sum.set(j, sum_temp + sum.get(j));

                if (min.get(j) > value) {
                    min.set(j, value);
                }
                if (max.get(j) < value) {
                    max.set(j, value);
                }
            }

        }

        // creates a new JPanel for each column and adds the stats
        for (int i = 0; i < numeric_column_names.size(); i++) {
            double average = (sum.get(i) / data.getRowCount());

            JPanel panel = new JPanel();

            JLabel title = new JLabel("Stats for " + numeric_column_names.get(i));
            panel.add(title);

            String average_string = String.format("%.2f", average);
            JLabel average_label = new JLabel("Average = " + average_string);
            panel.add(average_label);

            if (!isDoubleValueColumn(i)) {
                JLabel max_label = new JLabel("Max = " + Math.round(max.get(i)));
                panel.add(max_label);
                JLabel min_label = new JLabel("Min = " + Math.round(min.get(i)));
                panel.add(min_label);
            } else {
                JLabel max_label = new JLabel("Max = " + max.get(i));
                panel.add(max_label);
                JLabel min_label = new JLabel("Min = " + min.get(i));
                panel.add(min_label);
            }

            this.add(panel);
        }

    }

    private boolean isDoubleValueColumn(int column_num) {
        if (data.getColumnClass(column_num) == double.class)
            return true;
        return false;

    }

}
