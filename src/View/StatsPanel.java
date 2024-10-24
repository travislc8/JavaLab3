package src.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.Model.*;
import src.ViewModel.*;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatsPanel extends JPanel implements ActionListener {
    Dimension panelDimension;
    JLabel messageLabel;
    DataTableModel data;
    int Average;

    public StatsPanel(Dimension dimension) {
        panelDimension = dimension;
        setPreferredSize(panelDimension);
        setBackground(Color.red);
    }

    public void setMessage(String message) {
        messageLabel = new JLabel(message);
        this.removeAll();
        this.add(messageLabel);
        this.revalidate();
        this.repaint();
    }

    public void setData(DataTableModel data) {
        this.data = data;
        updateStats();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("action in StatsPanel");
    }

    private void updateStats() {
        String message = "top item = " + data.getColumnClass(0);
        message += data.getColumnClass(1);
        message += data.getColumnClass(2);
        message += data.getColumnClass(3);
        message += data.getColumnClass(4);
        message += data.getColumnClass(5);
        message += data.getColumnClass(6);
        message += data.getColumnClass(7);
        message += data.getColumnClass(8);
        message += data.getColumnClass(9);
        message += data.getColumnClass(10);

        setMessage(message);
    }

}
