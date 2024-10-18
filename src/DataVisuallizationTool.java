package src;

import javax.swing.JFrame;
import javax.swing.JTable;

import java.awt.Dimension;
import src.View.*;
import src.Model.*;
import src.ViewModel.*;

public class DataVisuallizationTool {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Data Visualiztion Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setPreferredSize(new Dimension(1000, 1000));

        // sets the table panel
        TablePanel tablePanel = new TablePanel();
        frame.add(tablePanel);

        /*
         * var data = new Object[][] {
         * { "1", "2", "3" },
         * { "1", "2", "3" },
         * { "1", "2", "3" }
         * };
         * var names = new String[] { "a", "b", "c" };
         * var table = new JTable(data, names);
         * frame.add(table);
         */

        frame.pack();
        frame.setVisible(true);

    }

}
