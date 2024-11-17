package src.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import src.Model.RowData;

/**
 * Class to show the details of a value that is selected by the user on the
 * TablePanel
 */
public class DetailsPanel extends JPanel {
    Dimension panelDimension;

    /**
     * Constructor only sets the layout of the panel but does not display the
     * panel because there is no data. Must call the setItem method to display
     * the Details.
     */
    public DetailsPanel(Dimension dimension) {

        panelDimension = dimension;
        setPreferredSize(panelDimension);
        setBackground(Color.white);
        this.setLayout(new BorderLayout());
    }

    /**
     * Method to set the data that will be displayed by the detail panel
     *
     * @param rowData data that will be displayed
     */
    public void setItem(RowData rowData) {
        var column_data = rowData.getColumnData();
        var column_names = rowData.getColumnNames();

        this.removeAll();

        // sets the title of the panel
        setTitle();

        // adds the data from each of the columns as its own JPanel, then
        // adds each to a single JPanel to be added to the DetailsPanel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        // iterates through each data point representing a single column
        // adds the data from the column to the panel
        for (int i = 0; i < rowData.getColumnCount(); i++) {
            JLabel name = new JLabel(column_names.get(i) + ":");
            JLabel data = new JLabel(column_data.get(i));
            panel.add(name);
            panel.add(data);
        }
        // adds the panel containing the data to the detail panel
        this.add(panel, BorderLayout.CENTER);

        // displays the panel
        this.revalidate();
        this.repaint();

    }

    /**
     * Helper function that adds a title to the DetailsPanel
     */
    private void setTitle() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Selected Item Data"));
        this.add(panel, BorderLayout.PAGE_START);
    }
}
