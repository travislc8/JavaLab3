package src.Model;

import javax.swing.JComboBox;

/**
 * Helper class that extends JComboBox and holds the index for the column number
 * that the drop down is used to filter
 */
public class JComboBoxFilter extends JComboBox<String> {
    int columnNum;

    public JComboBoxFilter(int columnNum, String[] value) {
        super(value);
        setColumnNum(columnNum);
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

}
