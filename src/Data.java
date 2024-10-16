import java.util.ArrayList;

public class Data {
    private ArrayList<ArrayList<String>> dataPoints = new ArrayList<>();
    private ArrayList<String> columnNames = new ArrayList<>();
    private ArrayList<String> fileContents = new ArrayList<>();
    private ArrayList<Integer> columnWidth = new ArrayList<>();

    public Data(ArrayList<String> fileContents) {
        // initializes the column width array
        setColumnWidth(fileContents.get(0));

        // gets the coumn name data
        columnNames = getRowData(fileContents.get(0));

        // add the row data
        for (int i = 1; i < fileContents.size(); i += 1) {
            dataPoints.add(getRowData(fileContents.get(i)));
        }
    }

    private ArrayList<String> getRowData(String row_string) {
        String word = "";
        char letter;
        ArrayList<String> row_data = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < row_string.length(); i += 1) {
            letter = row_string.charAt(i);
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
            } else if (letter != ',') {
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
        for (String item : columnNames) {
            System.out.print(item + "     ");
        }

        for (ArrayList<String> row : dataPoints) {
            for (String item : row) {
                System.out.print(item + "     ");
            }
            System.out.println();

        }
    }

    public ArrayList<String> getRow(int index) {
        // returns index + 1 to account for header
        return dataPoints.get(index + 1);
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

    private void setColumnWidth(String line) {
        int count = 3;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ',')
                count += 1;
        }

        // pre set the column width data
        for (int i = 0; i < count; i++) {
            columnWidth.add(1);
        }
    }

    public int getRowCount() {
        return dataPoints.size() - 1;
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public void sortByColumn(int column_num) {
        IndexValueData[] array = new IndexValueData[getRowCount()];
        // makes a sortable array of the column that will be sorted
        for (int i = 0; i < getRowCount(); i++) {
            array[i] = new IndexValueData(i, dataPoints.get(i).get(column_num));
        }

        System.out.println(array[1].getIndex() + " " + array[1].getStringValue());
        System.out.println(array[3].getIndex() + " " + array[3].getStringValue());
        System.out.println(array.toString());
        // sorts the array
        array = sortData(array, getRowCount());
        System.out.println(array[1].getIndex() + " " + array[1].getStringValue());
        System.out.println(array[3].getIndex() + " " + array[3].getStringValue());
        System.out.println(array.toString());

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

    private IndexValueData[] sortData(IndexValueData[] data, int length) {
        // IndexValueData[] new_data = new IndexValueData[length];
        IndexValueData temp = new IndexValueData(0, "");
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < (length - i - 1); j++) {
                if (data[j].compareTo(data[j + 1]) >= 0) {
                    temp.set(data[j + 1]);
                    data[j + 1].set(data[j]);
                    data[j].set(temp);
                }
            }
        }
        return data;
    }
}
