package src.View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class FilterPanel extends JPanel {
    private Dimension panelDimension;

    public FilterPanel(Dimension dimension) {
        panelDimension = dimension;
        setPreferredSize(panelDimension);
        this.setBackground(Color.gray);
    }

}
