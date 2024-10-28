package src.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import src.Model.RowData;

public class DetailsPanel extends JPanel {
    Dimension panelDimension;
    ArrayList<JLabel> data;

    public DetailsPanel(Dimension dimension) {

        panelDimension = dimension;
        setPreferredSize(panelDimension);
        setBackground(Color.white);
        this.setLayout(new BorderLayout());
    }

    private void init() {
        data = new ArrayList<>();
    }

    public void setItem(RowData rowData) {
        var column_data = rowData.getColumnData();
        var column_names = rowData.getColumnNames();

        this.removeAll();
        setTitle();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        for (int i = 0; i < rowData.getColumnCount(); i++) {
            JLabel name = new JLabel(column_names.get(i) + ":");
            JLabel data = new JLabel(column_data.get(i));
            panel.add(name);
            panel.add(data);
        }
        this.add(panel, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();

    }

    private void setTitle() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Selected Item Data"));
        this.add(panel, BorderLayout.PAGE_START);
    }
}
