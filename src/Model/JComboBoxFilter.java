package src.Model;

import javax.swing.JComboBox;

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
