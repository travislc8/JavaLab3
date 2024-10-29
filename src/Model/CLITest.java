package src.Model;

/**
 * Tester Class for the data class
 */
public class CLITest {
    public static void main(String[] args) {
        DataReader reader = new DataReader();
        reader.init();

        Data data = new Data();
        data.init(reader.getData());
        // data.printData(25);
        // data.sortByColumn(7);

        var options = data.getColumnOptions(4);
        System.out.println(options);
        data.sortByColumn(4);
    }
}
