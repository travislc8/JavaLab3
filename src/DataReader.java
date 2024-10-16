import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class DataReader {
    private Data data;
    private String fileName = "data/data.csv";
    private File dataFile;
    private Scanner fileReader;
    private ArrayList<String> fileContents = new ArrayList<>();
    private boolean dataRead;

    public DataReader() {
        // opens the file and if fails to open, terminates the program
        if (!openFile()) {
            dataRead = false;
            return;
        } else {
            dataRead = true;
        }
        readFile();
        data = new Data(fileContents);
    }

    public boolean hasData() {
        return dataRead;
    }

    private boolean openFile() {
        try {
            dataFile = new File(fileName);
            fileReader = new Scanner(dataFile);

            return true;
        } catch (FileNotFoundException e) {

            // throws error message with information on how to solve the error
            System.out.println("*****Error reading data file*****");
            var system_properties = System.getProperties();
            var filePath = system_properties.getProperty("user.dir", "not found");
            System.out.println("Program is lookin for file at: " + filePath + fileName);
            System.out.println("Working directory should be .../JavaLabThree,");
            System.out.print("present working directory is: " + filePath);

            return false;
        }
    }

    private void readFile() {
        while (fileReader.hasNextLine()) {
            fileContents.add(fileReader.nextLine());
        }

        // closes the file
        fileReader.close();
    }

    public void printData() {
        data.printHeader();
        // prints all of the rows
        for (int i = 0; i < data.getRowCount(); i++) {
            data.printDataPoint(i);
        }
    }

    public void printData(int count) {
        data.printHeader();
        // caps the count to the max
        if (count > data.getRowCount())
            count = data.getRowCount();

        // prints count number of rows
        for (int i = 0; i < count; i++) {
            data.printDataPoint(i);
        }
    }

    public void printHeader() {
        data.printHeader();
    }

    public void printRow(int row_num) {
        data.printDataPoint(row_num);
    }

    public void sortByColumn(int column_num) {
        data.sortByColumn(column_num);
    }
}
