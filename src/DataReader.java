import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class DataReader {
    public final String FILENAME = "data/data.csv";
    private String fileName = FILENAME;
    private File dataFile;
    private Scanner fileReader;
    private ArrayList<String> fileContents = new ArrayList<>();
    private boolean dataRead;

    public void init() {
        // opens the file and if fails to open, terminates the program
        if (!openFile()) {
            dataRead = false;
            System.out.println("Could not read file. Exiting program");
            System.exit(1);
        } else {
            dataRead = true;
        }
        readFile();
    }

    public ArrayList<String> getData() {
        return fileContents;
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
}
