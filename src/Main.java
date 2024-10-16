public class Main {
    public static void main(String[] args) {
        System.out.println("test");

        DataReader data = new DataReader();
        if (!data.hasData()) {
            System.out.println("Could not read file");
            System.exit(1);
        }
        data.printData(25);
        data.sortByColumn(7);
        data.printData(25);

    }
}
