package src.View;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.*;
import src.View.*;
import src.Model.*;
import src.ViewModel.*;

public class TablePanel extends JPanel {
    private DataTableModel data;
    JTable table;
    JScrollPane scrollPane;
    private Dimension panelDimension;
    int selectedItem = 0;
    DetailsPanel detailPanel;

    public TablePanel(Dimension dimension) {
        panelDimension = dimension;
        setPreferredSize(panelDimension);
        setBackground(Color.white);
        this.setLayout(new BorderLayout());
        // reads the data from the file
        DataReader reader = new DataReader();
        reader.init();

        // initializes the data
        data = new DataTableModel(reader.getData());

        // initializes the tabel
        table = new JTable();
        table.setModel(data);

        setTableSorter();
        setTableLayout();
        setSelectionModel();

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(panelDimension.width,
                (int) (panelDimension.height * .6)));

        detailPanel = new DetailsPanel(new Dimension(panelDimension.width,
                (int) (panelDimension.height * .4)));
        updateDetailPanel();

        this.add(scrollPane, BorderLayout.PAGE_START);
        this.add(detailPanel, BorderLayout.PAGE_END);
    }

    private void setTableLayout() {
        table.setFillsViewportHeight(true);

        // sets the column width
        TableColumnModel column = table.getColumnModel();

        for (int i = 0; i < data.getColumnCount(); i++) {
            column.getColumn(i).setPreferredWidth(data.getColumnWidth(i) * 3);
        }

    }

    class SelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel model = (ListSelectionModel) e.getSource();
            selectedItem = e.getFirstIndex();
            updateDetailPanel();
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
        for (int i = 0; i < data.getColumnCount(); i++) {
            columnNames.add(data.getColumnName(i));
            row.add(data.getValueAt(index, i).toString());
        }
        RowData rowData = new RowData(columnNames, row, row.size());

        return rowData;
    }

    private void updateDetailPanel() {
        RowData selectedData = getSelectedItem();
        detailPanel.setItem(selectedData);
    }

    private void setTableSorter() {
        Comparator<String> comparator = new Comparator<String>() {
            public int compare(String s1, String s2) {
                try {
                    double num1 = Double.parseDouble(s1);
                    double num2 = Double.parseDouble(s2);
                    double result = num1 - num2;
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
                } catch (Exception e) {
                    return s1.compareToIgnoreCase(s2);
                }
            }
        };
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        for (int i = 0; i < data.getColumnCount(); i++) {
            sorter.setComparator(i, comparator);
        }
        table.setRowSorter(sorter);
    }
}
