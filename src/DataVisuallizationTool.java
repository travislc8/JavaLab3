package src;

import javax.swing.JFrame;
import javax.swing.JTable;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.ArrayList;

import src.View.*;
import src.Model.*;
import src.ViewModel.*;

public class DataVisuallizationTool {
    // device info to get the screen size
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    /**
     * Main Driver of Program
     * Instantiats the Display Panel which drives the program
     */
    public static void main(String[] args) {
        // sets JFrame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(new Dimension(screenSize.width, screenSize.height - 45));
        JFrame frame = new JFrame("Data Visualiztion Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(screenSize);

        // sets display panel
        DisplayPanel displayPanel = new DisplayPanel(screenSize, getData());
        frame.add(displayPanel);

        frame.pack();
        frame.setVisible(true);
        // frame.setUndecorated(true);
        // device.setFullScreenWindow(frame);

    }

    /**
     * Reads the file and gets the data that the program uses
     */
    private static ArrayList<String> getData() {
        DataReader reader = new DataReader();
        reader.init();
        return reader.getData();
    }
}
