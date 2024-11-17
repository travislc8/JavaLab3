package src.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import src.ViewModel.*;
import src.Model.*;

/**
 * Class to hold the filtering elements of the program. Also holds the title
 * of the program. Holds a reference to the TablePanel and updates it any
 * time there is a change to the data
 */
public class FilterPanel extends JPanel {
    private DataTableModel allData;
    private DataTableModel filteredData;
    private Dimension panelDimension;
    private TablePanel tablePanel;
    private ArrayList<FilterDecorator> filterCheckBoxes;
    private ArrayList<ArrayList<String>> allFilterOptions;
    private ArrayList<ArrayList<FilterDecorator>> selectedFilterOptions;
    private ArrayList<JComboBoxDecorator> dropDownBoxes;
    final private String noSelectionString = "Select an Option";

    /**
     * Constructor. Sets the elements of the panel and displays them.
     *
     * @param dimension the dimension that the filter panel should be
     * @param data      the data that will be shown by the program
     */
    public FilterPanel(Dimension dimension, ArrayList<String> data) {
        panelDimension = dimension;

        // sets the panel
        setPreferredSize(panelDimension);
        this.setBackground(Color.lightGray);
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.lightGray);

        setTitle();

        allData = new DataTableModel(data);
        // no filters are applied so the filtered equals unfiltered
        filteredData = allData;

        setFilters();
    }

    /**
     * Sets the TablePanel reference
     *
     * @param tablePanel reference to the TablePanel of the program
     */
    public void setTablePanel(TablePanel tablePanel) {
        this.tablePanel = tablePanel;
    }

    /**
     * Helper method to set the title in the panel
     */
    private void setTitle() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        JLabel label = new JLabel("Data Visualization Tool");
        label.setFont(new Font(null, Font.PLAIN, 22));
        panel.add(label);
        this.add(panel, BorderLayout.PAGE_START);
    }

    /**
     * Method to set the layout and contents of all the filters that will
     * be displayed. Will display the filters and set the functionality of the
     * filters
     */
    private void setFilters() {
        filterCheckBoxes = new ArrayList<>();
        allFilterOptions = new ArrayList<>();
        dropDownBoxes = new ArrayList<JComboBoxDecorator>();
        selectedFilterOptions = new ArrayList<>();

        // iterates through the columns in the table
        // if the column has more than one option
        // adds the filters to the filter array
        // if the column has more than 5 options
        // adds the filters as a dropdown menu
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayout(1, 0));
        filterPanel.setBackground(Color.lightGray);
        for (int i = 0; i < filteredData.getColumnCount(); i++) {
            // does not add filters if it is numeric data
            if (filteredData.getColumnClass(i) == Integer.class ||
                    filteredData.getColumnClass(i) == Double.class) {
                // sets an empty array list as a placeholder
                allFilterOptions.add(new ArrayList<>());
                continue;
            }

            // gets all the options for the column
            var options = allData.getColumnOptions(i);
            ArrayList<String> options_list = new ArrayList<>();
            for (String option : options) {
                options_list.add(option);
            }
            allFilterOptions.add(options_list);

            // if there are not 2 or more options, does not set a filter
            if (options.size() < 2) {
                continue;
            }

            // if there are more than 5 options
            // makes a drop-down
            if (options.size() > 5) {
                // panel to hold the drop-down
                JPanel panel = new JPanel();
                panel.setBackground(Color.lightGray);
                panel.add(new JLabel(allData.getColumnName(i)));

                options.add(0, noSelectionString); // default value of drop down
                // converts the ArrayList to a String[]
                String[] drop_down_options = new String[options.size()];
                drop_down_options = options.toArray(drop_down_options);

                // creates the drop-down
                JComboBoxDecorator dropDown = new JComboBoxDecorator(i, drop_down_options);
                dropDown.setSelectedIndex(0);
                dropDownBoxes.add(dropDown);
                // sets the action listener to call the filterData method
                dropDown.addItemListener(l -> {
                    filterData();
                });

                panel.add(dropDown);
                filterPanel.add(panel);

            }
            // if there were not more than 5 options, uses checkboxes
            else {
                // sets panel to hold the checkboxes
                JPanel panel = new JPanel();
                panel.setBackground(Color.lightGray);
                panel.add(new JLabel(allData.getColumnName(i)));

                // creates a checkbox for each option
                for (String option : options) {
                    FilterDecorator box = new FilterDecorator(i, option);
                    box.setBackground(Color.lightGray);
                    filterCheckBoxes.add(box);
                    box.addItemListener(e -> {
                        filterData();
                    });
                    panel.add(box);
                }
                filterPanel.add(panel);
            }
        }

        // adds the panel containing all the filters to the filter panel
        this.add(filterPanel, BorderLayout.CENTER);

    }

    public DataTableModel getFilteredData() {
        return filteredData;
    }

    /**
     * Method that filters the data based on the selected filters. Once the
     * data is filtered, the TablePanel is updated.
     */
    private void filterData() {
        // sets the filtered data to equal the complete data set.
        // Done to reset any previous filtering
        filteredData = new DataTableModel(allData);

        // finds the number of checkboxes that are checked
        int num_checked = 0;
        for (int i = 0; i < filterCheckBoxes.size(); i++) {
            if (filterCheckBoxes.get(i).isSelected())
                num_checked += 1;
        }

        // if the number of checkboxes is not 0, filter to display the ones
        // that are checked
        // if zero are checked, all the data is shown.
        if (num_checked != 0) {

            // filter out items not checked
            for (FilterDecorator option : filterCheckBoxes) {
                if (!option.isSelected()) {
                    filteredData.removeCategory(option.getColumnNum(), option.getValue());
                }
            }
        }

        // filters drop down items
        for (JComboBoxDecorator drop_down : dropDownBoxes) {
            int selected_index = drop_down.getSelectedIndex();
            // if item is not selected
            if (selected_index == 0)
                continue;
            else {
                // decrements the index by 1 to account
                // for the no item selected option
                selected_index -= 1;
            }

            // removes all but the option that was selected
            String selected_option = allFilterOptions.get(drop_down.getColumnNum()).get(selected_index);
            filteredData.removeAllButCategory(drop_down.getColumnNum(),
                    selected_option);
        }

        // updates any dependent panels
        updateDependents();
    }

    /**
     * Method to update all the dependents when the filtered data is changed
     */
    private void updateDependents() {
        tablePanel.updateData(filteredData);
    }
}
