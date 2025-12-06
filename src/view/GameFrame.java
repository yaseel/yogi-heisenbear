package view;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public GameFrame() {
        setTitle("Yogi Heisenbear");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);
    }

    public void addPanel(PanelType panelType, JPanel panel) {
        mainPanel.add(panel, panelType.toString());
    }

    public void showPanel(PanelType panelType) {
        cardLayout.show(mainPanel, panelType.toString());
    }
}