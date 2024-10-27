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

public class TablePanel extends JPanel implements ActionListener {
    private DataTableModel dataTableModel;
    JTable table;
    JScrollPane scrollPane;
    private Dimension panelDimension;
    int selectedItem = 0;
    DetailsPanel detailsPanel;
    StatsPanel statsPanel;
    ChartPanel chartPanel;

    public TablePanel(Dimension dimension, DataTableModel data) {
        panelDimension = dimension;
        setPreferredSize(panelDimension);
        setBackground(Color.white);
        this.setLayout(new BorderLayout());

        // initializes the data
        this.dataTableModel = new DataTableModel(data);

        // initializes the tabel
        table = new JTable();
        table.setModel(data);

        setTableSorter();
        setTableLayout();
        setSelectionModel();

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(panelDimension);

        this.add(scrollPane, BorderLayout.PAGE_START);
    }

    private void setTable() {
        // initializes the tabel
        table = new JTable();
        table.setModel(dataTableModel);

        setTableSorter();
        setTableLayout();
        setSelectionModel();

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(panelDimension);

        this.add(scrollPane, BorderLayout.PAGE_START);
    }

    private void setTableLayout() {
        table.setFillsViewportHeight(true);

        // sets the column width
        TableColumnModel column = table.getColumnModel();

        for (int i = 0; i < dataTableModel.getColumnCount(); i++) {
            column.getColumn(i).setPreferredWidth(dataTableModel.getColumnWidth(i) * 3);
        }

    }

    class SelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel model = (ListSelectionModel) e.getSource();
            selectedItem = e.getFirstIndex();
            updateDetailPanel();
            statsPanel.setData(dataTableModel);
            chartPanel.setData(dataTableModel);
        }
    }

    private void setSelectionModel() {
        ListSelectionModel listModel = table.getSelectionModel();
        listModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModel.addListSelectionListener(new SelectionHandler());
    }

    public RowData getSelectedItem() {
        int index = table.convertRowIndexToModel(selectedItem);
        var columnNames = new ArrayList<String>();
        var row = new ArrayList<String>();
        for (int i = 0; i < dataTableModel.getColumnCount(); i++) {
            columnNames.add(dataTableModel.getColumnName(i));
            row.add(dataTableModel.getValueAt(index, i).toString());
        }
        RowData rowData = new RowData(columnNames, row, row.size());

        return rowData;
    }

    private void updateDetailPanel() {
        RowData selectedData = getSelectedItem();
        detailsPanel.setItem(selectedData);
    }

    private void setTableSorter() {
        Comparator<String> stringComparator = new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        };
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
        Comparator<Integer> intComparator = new Comparator<Integer>() {
            public int compare(Integer i1, Integer i2) {
                return i1 - i2;
            }

        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        for (int i = 0; i < dataTableModel.getColumnCount(); i++) {
            if (dataTableModel.getColumnClass(i) == String.class)
                sorter.setComparator(i, stringComparator);
            else if (dataTableModel.getColumnClass(i) == Double.class)
                sorter.setComparator(i, doubleComparator);
            else
                sorter.setComparator(i, intComparator);
        }
        table.setRowSorter(sorter);
    }

    private void setTableFilters() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("In table panel action performed");
    }

    public DataTableModel getDataModel() {
        return dataTableModel;
    }

    public void addDependentPanels(StatsPanel statsPanel, ChartPanel chartPanel, DetailsPanel detailsPanel) {
        this.statsPanel = statsPanel;
        this.chartPanel = chartPanel;
        this.detailsPanel = detailsPanel;
        chartPanel.setData(getDataModel());
        updateDetailPanel();
        statsPanel.setData(getDataModel());
    }

    public void setData(DataTableModel data) {
        this.removeAll();
        this.dataTableModel = new DataTableModel(data);
        updateDetailPanel();
        statsPanel.setData(dataTableModel);
        chartPanel.setData(dataTableModel);
        setTable();
        this.repaint();
    }

}
