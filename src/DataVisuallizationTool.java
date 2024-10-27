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
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.height = screenSize.height - 45;
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

    private static ArrayList<String> getData() {
        DataReader reader = new DataReader();
        reader.init();
        return reader.getData();
    }
}
