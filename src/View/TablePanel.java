package src.View;

import javax.swing.JPanel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import javax.swing.*;
import src.View.*;
import src.Model.*;
import src.ViewModel.*;

public class TablePanel extends JPanel {
    private DataTableModel data;
    JTable table;
    JScrollPane scrollPane;
    private int width = 900;
    private int height = 900;

    public TablePanel() {
        // reads the data from the file
        DataReader reader = new DataReader();
        reader.init();

        // initializes the data
        data = new DataTableModel(reader.getData());
        data.sortByColumn(7);
        this.setBackground(Color.red);

        // initializes the tabel
        table = new JTable();
        table.setModel(data);

        setTableLayout();

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(width, height));
        this.add(scrollPane);

        this.setBackground(Color.red);
    }

    private void setTableLayout() {
        table.setFillsViewportHeight(true);

        // sets the column width
        TableColumnModel column = table.getColumnModel();

        for (int i = 0; i < data.getColumnCount(); i++) {
            column.getColumn(i).setPreferredWidth(data.getColumnWidth(i) * 3);
        }

    }
}
