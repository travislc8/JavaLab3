package src.Model;

import java.util.ArrayList;

/**
 * some explanation
 */
public class Data {
    private int columnCount;
    private ArrayList<ArrayList<String>> dataPoints = new ArrayList<>();
    private ArrayList<String> columnNames = new ArrayList<>();
    private ArrayList<Integer> columnWidth = new ArrayList<>();
    private ArrayList<Class<?>> columnClass = new ArrayList<>();

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public void setColumnNames(ArrayList<String> columnNames) {
        this.columnNames = columnNames;
    }

    public ArrayList<Integer> getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(ArrayList<Integer> columnWidth) {
        this.columnWidth = columnWidth;
    }

    public ArrayList<Class<?>> getColumnClass() {
        return columnClass;
    }

    public void setColumnClass(ArrayList<Class<?>> columnClass) {
        this.columnClass = columnClass;
    }

    public Data() {
    }

    public Data(ArrayList<String> fileContents) {
        init(fileContents);
    }

    public void init(ArrayList<String> fileContents) {

        // initializes the column width array
        setInitialColumnWidth(fileContents.get(0));

        // gets the coumn name data
        columnNames = extractRowData(fileContents.get(0));

        // add the row data
        for (int i = 1; i < fileContents.size(); i += 1) {
            dataPoints.add(extractRowData(fileContents.get(i)));
        }

        // initializes the column types
        setColumnTypes();
        // sets all "" values in number rows to 0
        convertNumberRows();

    }

    protected void setDataPoints(ArrayList<ArrayList<String>> dataPoints) {
        this.dataPoints = dataPoints;
    }

    private ArrayList<String> extractRowData(String row_string) {
        String word = "";
        char letter;
        ArrayList<String> row_data = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < row_string.length(); i += 1) {
            letter = row_string.charAt(i);
            // case column has a , so is wrapped in ""
            if (letter == '"') {
                i += 1;
                letter = row_string.charAt(i);
                while (letter != '"') {
                    if (letter != ',') {
                        word += letter;
                    }
                    i += 1;
                    letter = row_string.charAt(i);
                }
            }
            // adds letter to word if not a ,
            else if (letter != ',') {
                word += letter;
            } else {
                // adds the word and updates the column
                row_data.add(word);
                updateColumnWidth(word, count);
                count += 1;
                word = "";
            }
        }
        row_data.add(word);
        updateColumnWidth(word, count);
        return row_data;
    }

    private void updateColumnWidth(String word, int column) {
        if (word.length() >= columnWidth.get(column)) {
            columnWidth.set(column, word.length() + 1);
        }
    }

    public void printData() {
        printHeader();
        // prints all of the rows
        for (int i = 0; i < getRowCount(); i++) {
            printDataPoint(i);
        }
    }

    public void printData(int count) {
        printHeader();
        // caps the count to the max
        if (count > getRowCount())
            count = getRowCount();

        // prints count number of rows
        for (int i = 0; i < count; i++) {
            printDataPoint(i);
        }
    }

    public ArrayList<String> getRow(int index) {
        // returns index + 1 to account for header
        return dataPoints.get(index);
    }

    public void printHeader() {
        System.out.print("| ");
        int total_width = 2;
        for (int i = 0; i < columnNames.size(); i += 1) {
            String out = Util.rightPadString(columnNames.get(i), columnWidth.get(i));
            System.out.print(out);
            System.out.print("| ");

            // calculate the total width of the line
            total_width += columnWidth.get(i) + 2;
        }
        char[] char_line = new char[total_width];
        while (total_width > 0) {
            char_line[total_width - 1] = '_';
            total_width -= 1;
        }

        System.out.println("\n" + new String(char_line));
    }

    public void printDataPoint(int index) {
        System.out.print("| ");
        ArrayList<String> row = dataPoints.get(index + 1);
        for (int i = 0; i < row.size(); i += 1) {
            String out = Util.rightPadString(row.get(i), columnWidth.get(i));
            System.out.print(out);
            System.out.print("| ");
        }
        System.out.println();
    }

    private void setInitialColumnWidth(String line) {
        int count = 3;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ',')
                count += 1;
        }

        // pre set the column width data
        for (int i = 0; i < count; i++) {
            columnWidth.add(1);
        }
        columnCount = count;
    }

    public int getRowCount() {
        return dataPoints.size();
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public void sortByColumn(int column_num) {
        IndexValueData[] array = new IndexValueData[getRowCount()];
        // try to parse into int values and sort
        // if fails to parse all of the values, sort as string
        try {
            int num;
            String value;
            // makes a sortable array of the column that will be sorted
            // is int values
            for (int i = 0; i < getRowCount(); i++) {
                value = dataPoints.get(i).get(column_num);
                // check if value is blank and make value 0
                if (value.equals("")) {
                    num = 0;
                } else {
                    num = Integer.parseInt(value);
                }
                // creates the object
                array[i] = new IndexValueData(i, num);
            }
        } catch (Exception e) {
            // makes a sortable array of the column that will be sorted
            // is string values
            for (int i = 0; i < getRowCount(); i++) {
                array[i] = new IndexValueData(i, dataPoints.get(i).get(column_num));
            }
        }

        // sorts the array
        array = Util.sortData(array, getRowCount());

        // new array list is created to put the sorted values in
        ArrayList<ArrayList<String>> newList = new ArrayList<>();
        int old_row_position;

        // adds the proper row from the old array into the new array
        for (int i = 0; i < getRowCount(); i++) {
            ArrayList<String> row = new ArrayList<>();
            old_row_position = array[i].getIndex();

            for (int column = 0; column < getColumnCount(); column++) {
                row.add(dataPoints.get(old_row_position).get(column));
            }
            newList.add(row);
        }
        dataPoints = newList;
    }

    public ArrayList<String> getColumnOptions(int column_num) {
        ArrayList<String> options = new ArrayList<>();
        String item;
        for (ArrayList<String> row : dataPoints) {
            item = row.get(column_num);
            if (!options.contains(item)) {
                options.add(item);
            }
        }

        return options;
    }

    public Class<?> getColumnClass(int column_num) {
        return columnClass.get(column_num);
    }

    public String getColumnName(int column_num) {
        return columnNames.get(column_num);
    }

    public String getCell(int row_num, int column_num) {
        return dataPoints.get(row_num).get(column_num);
    }

    public int getColumnWidth(int column_num) {
        return columnWidth.get(column_num);
    }

    public ArrayList<String> getColumnNames() {
        return columnNames;
    }

    private void updataColumnClass(int column_num, String item) {
        if (columnClass.get(column_num) == String.class) {
            return;
        }
        try {
            if (!item.equals("")) {
                Double.parseDouble(item);
                if (columnClass.get(column_num) == Integer.class) {
                    columnClass.set(column_num, Double.class);
                }
            }
        } catch (Exception e1) {
            try {
                if (!item.equals("")) {
                    Integer.parseInt(item);
                    // does noting becuase int is default
                }
            } catch (Exception e2) {
                columnClass.set(column_num, String.class);
            }
        }
    }

    private void setColumnTypes() {
        columnClass = new ArrayList<>();
        for (int i = 0; i < columnCount; i++) {
            columnClass.add(Integer.class);
        }

        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColumnCount(); j++) {
                updataColumnClass(j, dataPoints.get(i).get(j));
            }
        }
    }

    private void convertNumberRows() {
        for (int i = 0; i < getColumnCount(); i++) {

            // if it is a string column
            if (columnClass.get(i) == "".getClass())
                continue;

            for (int j = 0; j < getRowCount(); j++) {
                if (dataPoints.get(i).size() > getColumnCount()) {
                    continue;
                }

                if (dataPoints.get(j).get(i).equals("")) {
                    dataPoints.get(j).set(i, "0");
                }
            }

        }
    }

    public ArrayList<ArrayList<String>> getDataPoints() {
        ArrayList<ArrayList<String>> new_data = new ArrayList<>();
        for (ArrayList<String> old_row : dataPoints) {
            var new_row = new ArrayList<String>();
            for (String word : old_row) {
                new_row.add(word);
            }
            new_data.add(new_row);
        }

        return new_data;
    }

    public void removeAllButCatagory(int column_num, String catagory) {
        for (int i = 0; i < getRowCount();) {
            String value = dataPoints.get(i).get(column_num);
            if (!value.equals(catagory)) {
                dataPoints.remove(i);
            } else {
                i += 1;
            }
        }

        if (dataPoints.size() == 0) {
            var empty_row = new ArrayList<String>();
            for (int i = 0; i < getColumnCount(); i++) {
                if (getColumnClass(i) != String.class)
                    empty_row.add("0");
                else
                    empty_row.add("");
            }
            dataPoints.add(empty_row);
        }
    }

    public void removeCatagory(int column_num, String catagory) {
        for (int i = 0; i < getRowCount();) {
            String value = dataPoints.get(i).get(column_num);
            if (value.equals(catagory)) {
                dataPoints.remove(i);
            } else {
                i += 1;
            }
        }

        if (dataPoints.size() == 0) {
            var empty_row = new ArrayList<String>();
            for (int i = 0; i < getColumnCount(); i++) {
                if (getColumnClass(i) != String.class)
                    empty_row.add("0");
                else
                    empty_row.add("");
            }
            dataPoints.add(empty_row);
        }
    }

}
