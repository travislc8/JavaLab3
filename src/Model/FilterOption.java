package src.Model;

import javax.swing.JCheckBox;

public class FilterOption extends JCheckBox {
    int columnNum;
    String value;

    public FilterOption(int columnNum, String value) {
        super(value);
        setColumnNum(columnNum);
        setValue(value);

    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
