package src.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.nio.file.Path;

/**
 * Class to read data from a file and convert it into an ArrayList of the
 * lines of the file
 */
public class DataReader {
    public final String FILENAME = "data/data.csv";
    private ArrayList<String> fileContents = new ArrayList<>();
    private boolean dataRead;

    /**
     * Method to initiate the reading of the file.
     */
    public void init() {
        // opens the file and if fails to open, terminates the program
        if (!readFile()) {
            dataRead = false;
            System.out.println("Could not read file. Exiting program");
            System.exit(1);
        } else {
            dataRead = true;
        }
    }

    public ArrayList<String> getData() {
        return fileContents;
    }

    public boolean hasData() {
        return dataRead;
    }

    /**
     * Helper function to open the file. Uses stream to read the file. Prints an
     * error message if the file is not found.
     *
     * @return true if the file was opened
     */
    private boolean readFile() {
        // opens the file
        try {
            Path path = Paths.get(FILENAME);
            Files.lines(path)
                    .forEach(l -> fileContents.add(l));

            return true;
        }
        // if the file fails to open, prints an error message
        catch (IOException e) {

            // throws error message with information on how to solve the error
            System.out.println("*****Error reading data file*****");
            var system_properties = System.getProperties();
            var filePath = system_properties.getProperty("user.dir", "not found");
            System.out.println("Program is looking for file at: " + filePath + FILENAME);
            System.out.println("Working directory should be .../JavaLabThree,");
            System.out.print("present working directory is: " + filePath);

            return false;
        }
    }

}
