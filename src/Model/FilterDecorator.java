package src.Model;

import javax.swing.JCheckBox;

/**
 * Class that extends JCheckBox and holds a column number that is associated
 * with the data that the check box is a filter for.
 */
public class FilterDecorator extends JCheckBox {
    int columnNum;
    String value;

    public FilterDecorator(int columnNum, String value) {
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
