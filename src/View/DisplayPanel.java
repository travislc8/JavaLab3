package src.View;

import src.Model.*;
import src.ViewModel.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DisplayPanel extends JPanel implements ActionListener {
    FilterPanel filterPanel;
    TablePanel tablePanel;
    ChartPanel chartPanel;
    StatsPanel statsPanel;
    Dimension windowDimension;

    public DisplayPanel(Dimension screen_size) {
        windowDimension = screen_size;

        setLayout(new BorderLayout());

        // filter panel
        filterPanel = new FilterPanel(getDimension(1, .05));
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
        tablePanel = new TablePanel(getDimension(.5, .9), statsPanel);
        left_panel.add(tablePanel);

        this.add(left_panel, BorderLayout.LINE_START);

    }

    private int getWindowWidth() {
        return windowDimension.width;
    }

    private int getWindowHeight() {
        return windowDimension.height;
    }

    private Dimension getDimension(double percent_width, double percent_height) {
        int width = (int) (windowDimension.width * percent_width);
        int height = (int) (windowDimension.height * percent_height);
        return new Dimension(width, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("In display panel action performed");
    }

}
