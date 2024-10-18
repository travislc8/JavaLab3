package src.View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class DetailsPanel extends JPanel {
    Dimension panelDimension;

    public DetailsPanel(Dimension dimension) {
        panelDimension = dimension;
        setPreferredSize(panelDimension);
        setBackground(Color.blue);
    }

}
