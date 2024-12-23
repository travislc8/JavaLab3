package src.Model;

import java.util.Comparator;

/**
 * Helper class for sorting an ArrayList<ArrayList<String>>. Holds a value for
 * the inner ArrayList and the index for the location in the outer ArrayList.
 * Extends Comparator so that the values can be compared when sorting.
 */
public class IndexValueData implements Comparable<IndexValueData>, Comparator<IndexValueData> {
    private int index;
    private String stringValue;
    private int numValue;
    private boolean isNumValue;

    /**
     * Default constructor
     */
    public IndexValueData() {
        index = 0;
        stringValue = "";
        numValue = 0;
        isNumValue = false;
    }

    /**
     * Constructor for values that are strings
     */
    public IndexValueData(int index, String value) {
        this.index = index;
        this.stringValue = value;
        this.numValue = 0;
        isNumValue = false;
    }

    /**
     * Constructor for values that are integers
     */
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

    @Override
    public int compare(IndexValueData o1, IndexValueData o2) {
        if (isNumValue) {
            return o1.numValue - o2.getNumValue();
        } else {
            return o1.stringValue.compareToIgnoreCase(o2.getStringValue());
        }
    }
}
