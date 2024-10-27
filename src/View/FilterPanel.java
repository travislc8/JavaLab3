package src.View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import src.ViewModel.*;
import src.Model.*;

public class FilterPanel extends JPanel {
    private DataTableModel allData;
    private DataTableModel filteredData;
    private Dimension panelDimension;
    private TablePanel tablePanel;
    private ArrayList<FilterOption> filterCheckBoxes;
    private ArrayList<ArrayList<String>> allFilterOptions;
    private ArrayList<ArrayList<FilterOption>> selectedFilterOptions;
    private ArrayList<JComboBoxFilter> dropDownBoxes;
    final private String noSelectionString = "Select an Option";

    public FilterPanel(Dimension dimension, ArrayList<String> data) {
        panelDimension = dimension;
        setPreferredSize(panelDimension);
        this.setBackground(Color.gray);

        allData = new DataTableModel(data);
        // no filters are applied so the filtered equals unfiltered
        filteredData = allData;

        setFilterPanel();
    }

    public void setTablePanel(TablePanel tablePanel) {
        this.tablePanel = tablePanel;
    }

    private void setFilterPanel() {
        setFilters();

    }

    private void setFilters() {
        filterCheckBoxes = new ArrayList<>();
        allFilterOptions = new ArrayList<>();
        dropDownBoxes = new ArrayList<JComboBoxFilter>();
        selectedFilterOptions = new ArrayList<>();
        // iterates through the columns in the table
        // if the column has more than one option
        // adds the filters to the filter array
        for (int i = 0; i < filteredData.getColumnCount(); i++) {
            // does not add filters if it is numeric data
            if (filteredData.getColumnClass(i) == Integer.class ||
                    filteredData.getColumnClass(i) == Double.class) {
                setNumberFilters();
                allFilterOptions.add(new ArrayList<>());
                continue;
            }
            var options = allData.getColumnOptions(i);

            int count = 0;
            ArrayList<String> options_list = new ArrayList<>();
            for (String option : options) {
                options_list.add(option);
            }
            allFilterOptions.add(options_list);
            // if there are not 2 or more options
            // do not set a filter
            if (options.size() < 2) {
                continue;
            }

            ArrayList<FilterOption> selected_options_list = new ArrayList<>();

            // if there are more than 5 options
            // makes a drop down
            if (options.size() > 5) {
                JPanel panel = new JPanel();
                panel.add(new JLabel(allData.getColumnName(i)));

                options.add(0, noSelectionString);
                String[] drop_down_options = new String[options.size()];
                drop_down_options = options.toArray(drop_down_options);
                JComboBoxFilter dropDown = new JComboBoxFilter(i, drop_down_options);
                dropDown.setSelectedIndex(0);
                dropDownBoxes.add(dropDown);
                dropDown.addItemListener(l -> {
                    filterData();
                });

                panel.add(dropDown);
                this.add(panel);

            } else {

                JPanel panel = new JPanel();
                panel.add(new JLabel(allData.getColumnName(i)));

                for (String option : options) {
                    FilterOption box = new FilterOption(i, option);
                    filterCheckBoxes.add(box);
                    box.addItemListener(e -> {
                        filterData();
                    });
                    panel.add(box);
                }
                this.add(panel);
            }
        }

    }

    private void setNumberFilters() {

    }

    public DataTableModel getFilteredData() {
        return filteredData;
    }

    private void filterData() {
        int num_checked = 0;
        for (int i = 0; i < filterCheckBoxes.size(); i++) {
            if (filterCheckBoxes.get(i).isSelected())
                num_checked += 1;
        }
        filteredData = new DataTableModel(allData);

        if (num_checked != 0) {

            // filter out items not checked
            for (FilterOption option : filterCheckBoxes) {
                if (!option.isSelected()) {
                    filteredData.removeCatagory(option.getColumnNum(), option.getValue());
                }
            }
        }

        // filter drop down items
        for (JComboBoxFilter drop_down : dropDownBoxes) {
            int selected_index = drop_down.getSelectedIndex();
            // if item is not selected
            if (selected_index == 0)
                continue;
            else {
                // decriments the index by 1 to account
                // for the no item selected option
                selected_index -= 1;
            }

            String selected_option = allFilterOptions.get(drop_down.getColumnNum()).get(selected_index);

            filteredData.removeAllButCatagory(drop_down.getColumnNum(),
                    selected_option);
        }

        updateDependents();
    }

    private void updateDependents() {
        tablePanel.setData(filteredData);
    }
}
