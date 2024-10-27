package src.ViewModel;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import src.ViewModel.*;
import src.View.*;
import src.Model.*;

public class DataTableModel extends Data implements TableModel {

    public DataTableModel() {
        // reads the data from the file
        DataReader reader = new DataReader();
        reader.init();

        // sets the data
        init(reader.getData());

    }

    public DataTableModel(ArrayList<String> fileContents) {
        // sets the data
        init(fileContents);
    }

    public DataTableModel(Data data) {
        this.setDataPoints(data.getDataPoints());
        this.setColumnCount(data.getColumnCount());
        this.setColumnNames(data.getColumnNames());
        this.setColumnWidth(data.getColumnWidth());
        this.setColumnClass(data.getColumnClass());
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }

    @Override
    public int getColumnCount() {
        return super.getColumnCount();
    }

    @Override
    public int getRowCount() {
        return super.getRowCount();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return super.getColumnName(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String value = getRow(rowIndex).get(columnIndex);
        if (getColumnClass(columnIndex) == Integer.class) {
            return Integer.parseInt(value);
        } else if (getColumnClass(columnIndex) == Double.class) {
            return Double.parseDouble(value);
        } else {
            return value;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

}
