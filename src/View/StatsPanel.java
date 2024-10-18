package src.View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class StatsPanel extends JPanel {
    Dimension panelDimension;

    public StatsPanel(Dimension dimension) {
        panelDimension = dimension;
        setPreferredSize(panelDimension);
        setBackground(Color.red);

    }

}
