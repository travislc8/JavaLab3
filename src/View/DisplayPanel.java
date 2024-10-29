package src.View;

import src.Model.*;
import src.ViewModel.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class that holds all of the individual parts of the program. Sets up the
 * dependencies between the different parts of the program so that all parts
 * are updated to match the same data set
 */
public class DisplayPanel extends JPanel {
    FilterPanel filterPanel;
    TablePanel tablePanel;
    ChartPanel chartPanel;
    StatsPanel statsPanel;
    DetailsPanel detailsPanel;
    Dimension windowDimension;

    /**
     * Constructor. Adds all of the individual panles and displays them.
     * Also adds the dependencies between the panels
     */
    public DisplayPanel(Dimension screen_size, ArrayList<String> data) {
        windowDimension = screen_size;
        setLayout(new BorderLayout());

        // filter panel
        filterPanel = new FilterPanel(new Dimension(windowDimension.width, 100),
                data);
        this.add(filterPanel, BorderLayout.PAGE_START);

        // right panel
        JPanel right_panel = new JPanel();
        right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.PAGE_AXIS));

        // stat panel
        statsPanel = new StatsPanel(getDimension(.5, .4));
        right_panel.add(statsPanel);

        // chart panel
        chartPanel = new ChartPanel(getDimension(.5, .6));
        right_panel.add(chartPanel);

        this.add(right_panel, BorderLayout.LINE_END);

        // left panel
        JPanel left_panel = new JPanel();
        left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.PAGE_AXIS));

        // table panel
        tablePanel = new TablePanel(getDimension(.5, .7),
                filterPanel.getFilteredData());
        left_panel.add(tablePanel);

        // detail panel
        detailsPanel = new DetailsPanel(getDimension(.5, .4));
        left_panel.add(detailsPanel);

        this.add(left_panel, BorderLayout.LINE_START);

        // adds the dependencies between the panels
        tablePanel.addDependentPanels(statsPanel, chartPanel, detailsPanel);
        filterPanel.setTablePanel(tablePanel);

    }

    /**
     * Helper method to find the appropriate dimension based on the percent
     * of the screen that needs to be filled by a new component
     *
     * @param percent_width  a decimal value representing the percentage of
     *                       of the width the new dimension will be of the screen
     *                       size
     * @param percent_height a decimal value representing the percentage of
     *                       of the height the new dimension will be of the screen
     *                       size
     */
    private Dimension getDimension(double percent_width, double percent_height) {
        int width = (int) ((windowDimension.width - 20) * percent_width);
        int height = (int) ((windowDimension.height - 250) * percent_height);
        return new Dimension(width, height);
    }
}
