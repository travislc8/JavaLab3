package src.Model;

import java.util.ArrayList;

/**
 * Helper class for holding a row of a table and its associated column name data
 */
public class RowData {
    private int columnCount;
    private ArrayList<String> columnNames;
    private ArrayList<String> columnData;

    public RowData(ArrayList<String> column_names,
            ArrayList<String> column_data, int column_count) {
        columnCount = column_count;
        columnNames = column_names;
        columnData = column_data;
    }

    public RowData() {
        columnNames = new ArrayList<>();
        columnData = new ArrayList<>();
        columnCount = 0;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public ArrayList<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(ArrayList<String> columnNames) {
        this.columnNames = columnNames;
    }

    public ArrayList<String> getColumnData() {
        return columnData;
    }

    public void setColumnData(ArrayList<String> columnData) {
        this.columnData = columnData;
    }
}
