package src.Model;

public class IndexValueData implements Comparable<IndexValueData> {
    private int index;
    private String stringValue;
    private int numValue;
    private boolean isNumValue;

    public IndexValueData() {
        index = 0;
        stringValue = "";
        numValue = 0;
        isNumValue = false;
    }

    public IndexValueData(int index, String value) {
        this.index = index;
        this.stringValue = value;
        this.numValue = 0;
        isNumValue = false;
    }

    public IndexValueData(int index, int value) {
        this.index = index;
        this.stringValue = "";
        this.numValue = value;
        isNumValue = true;
    }

    public int getIndex() {
        return index;
    }

    public int getNumValue() {
        if (!isNumValue)
            return 0;
        return numValue;
    }

    public String getStringValue() {
        if (isNumValue)
            return "";
        return stringValue;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setValue(String value) {
        this.stringValue = value;
    }

    public void setValue(int value) {
        this.numValue = value;
    }

    public boolean getIsNum() {
        return isNumValue;
    }

    public void set(IndexValueData other) {
        this.index = other.getIndex();
        this.numValue = other.getNumValue();
        this.stringValue = other.getStringValue();
        this.isNumValue = other.getIsNum();
    }

    @Override
    public int compareTo(IndexValueData other) {
        if (isNumValue) {
            return this.numValue - other.getNumValue();
        } else {
            return this.stringValue.compareToIgnoreCase(other.getStringValue());
        }
    }
}
