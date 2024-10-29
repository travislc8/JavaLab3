package src.View;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.*;
import src.View.*;
import src.Model.*;
import src.ViewModel.*;

/**
 * Class that displays data in a table form. Uses a JTable in a JScrollPane
 * to display the data. Holds references to depenednet panels to update them
 * based on the state of the data.
 */
public class TablePanel extends JPanel {
    private DataTableModel dataTableModel;
    JTable table;
    JScrollPane scrollPane;
    private Dimension panelDimension;
    int selectedItem = 0;
    DetailsPanel detailsPanel;
    StatsPanel statsPanel;
    ChartPanel chartPanel;

    /**
     * Constructor. Sets the layout of the panel and displays the panel
     *
     * @param dimension dimension that the panel should be
     * @param data      data the should be displayed in the table
     */
    public TablePanel(Dimension dimension, DataTableModel data) {
        panelDimension = dimension;
        // sets the panel
        setPreferredSize(panelDimension);
        setBackground(Color.white);
        this.setLayout(new BorderLayout());

        // initializes the data
        this.dataTableModel = new DataTableModel(data);

        // adds the table to the panel
        setTable();
    }

    /**
     * Method to set the table. Should be used any time the data is changed.
     */
    private void setTable() {
        // initializes the tabel
        table = new JTable();
        table.setModel(dataTableModel);

        setTableSorter();
        setTableLayout();
        setSelectionModel();

        // adds the table to a scroll panel
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(panelDimension);

        this.add(scrollPane, BorderLayout.PAGE_START);
    }

    /**
     * Method to set the general layout of the JTable.
     */
    private void setTableLayout() {
        table.setFillsViewportHeight(true);

        // sets the column width
        TableColumnModel column = table.getColumnModel();

        for (int i = 0; i < dataTableModel.getColumnCount(); i++) {
            column.getColumn(i).setPreferredWidth(dataTableModel.getColumnWidth(i) * 3);
        }

    }

    /**
     * Class to handle events related to user selection of a cell in the table
     */
    class SelectionHandler implements ListSelectionListener {
        /**
         * Method that responds to the selection of a cell by setting the
         * detailsPanel item to be the row of the selected cell
         */
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel model = (ListSelectionModel) e.getSource();
            selectedItem = e.getFirstIndex();
            updateDetailPanel();
        }
    }

    /**
     * Method to set the response to a selection in the table by the user
     */
    private void setSelectionModel() {
        ListSelectionModel listModel = table.getSelectionModel();
        listModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModel.addListSelectionListener(new SelectionHandler());
    }

    /**
     * Helper method to get the item that was selected by the user and
     * return a RowData object containing the data of the selected item
     */
    public RowData getSelectedItem() {
        int index = table.convertRowIndexToModel(selectedItem);
        var columnNames = dataTableModel.getColumnNames();
        var row = new ArrayList<String>();
        // adds each of the columns data to an array containing the row data
        for (int i = 0; i < dataTableModel.getColumnCount(); i++) {
            row.add(dataTableModel.getValueAt(index, i).toString());
        }
        // creates a RowData object that holds the relevent data of the selection
        RowData rowData = new RowData(columnNames, row, row.size());

        return rowData;
    }

    /**
     * Helper function to update the details panel
     */
    private void updateDetailPanel() {
        RowData selectedData = getSelectedItem();
        detailsPanel.setItem(selectedData);
    }

    /**
     * Helper function to set up the sorter for each of the tables columns
     */
    private void setTableSorter() {
        // Comparator for strings
        Comparator<String> stringComparator = new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        };
        // Comparator for double
        Comparator<Double> doubleComparator = new Comparator<Double>() {
            public int compare(Double d1, Double d2) {
                double result = d1 - d2;
                if (result == 0) {
                    return 0;
                }
                if (result < 1 && result > -1) {
                    if (result > 0)
                        return 1;
                    else
                        return -1;
                }

                return (int) result;
            }

        };
        // Comparator for integers
        Comparator<Integer> intComparator = new Comparator<Integer>() {
            public int compare(Integer i1, Integer i2) {
                return i1 - i2;
            }

        };

        // adds the proper coparator to each of the columns
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        for (int i = 0; i < dataTableModel.getColumnCount(); i++) {
            if (dataTableModel.getColumnClass(i) == String.class)
                sorter.setComparator(i, stringComparator);
            else if (dataTableModel.getColumnClass(i) == Double.class)
                sorter.setComparator(i, doubleComparator);
            else
                sorter.setComparator(i, intComparator);
        }

        // adds the sorter to the table
        table.setRowSorter(sorter);
    }

    public DataTableModel getDataModel() {
        return dataTableModel;
    }

    /**
     * Method to set all of the dependencies to the panel
     *
     * @param statsPanel   reference to a StatsPanel
     * @param chartPanel   reference to a ChartPanel
     * @param detailsPanel reference to a DetailsPanel
     */
    public void addDependentPanels(StatsPanel statsPanel, ChartPanel chartPanel, DetailsPanel detailsPanel) {
        this.statsPanel = statsPanel;
        this.chartPanel = chartPanel;
        this.detailsPanel = detailsPanel;
        this.chartPanel.setData(getDataModel());
        updateDetailPanel();
        this.statsPanel.updateData(getDataModel());
    }

    /**
     * Method to set the data of the table panel. Calls the setData method of
     * the dependents. Should be called for the initialization of the TablePanel
     * and not for updating the data after the first initialization
     *
     * @param data data that the table displays
     */
    public void setData(DataTableModel data) {
        this.removeAll();
        this.dataTableModel = new DataTableModel(data);
        updateDetailPanel();
        statsPanel.updateData(dataTableModel);
        chartPanel.setData(dataTableModel);
        setTable();
        this.repaint();
    }

    /**
     * Method to update the data of the table. Calls the updateData methods of
     * the dependents. Should be called only if the setData method has already
     * been called
     */
    public void updateData(DataTableModel data) {
        this.removeAll();
        this.dataTableModel = new DataTableModel(data);
        updateDetailPanel();
        statsPanel.updateData(dataTableModel);
        chartPanel.updatDataset(dataTableModel);
        setTable();
        this.repaint();
    }

}
