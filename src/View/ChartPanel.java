package src.View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class ChartPanel extends JPanel {
    Dimension panelDimension;

    public ChartPanel(Dimension dimension) {
        this.panelDimension = dimension;
        setBackground(Color.white);
        setPreferredSize(panelDimension);

    }

}
