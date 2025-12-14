package view;

import model.GameConfig;
import view.panel.PanelType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private static final double ASPECT_RATIO = (double) GameConfig.LEVEL_WIDTH / GameConfig.LEVEL_HEIGHT;

    public GameFrame() {
        setTitle("Yogi Heisenbear");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                enforceAspectRatio();
            }
        });
    }

    private void enforceAspectRatio() {
        Insets insets = getInsets();
        int frameWidth = getWidth();
        int frameHeight = getHeight();

        int contentWidth = frameWidth - insets.left - insets.right;
        int contentHeight = frameHeight - insets.top - insets.bottom;

        int targetHeight = (int) (contentWidth / ASPECT_RATIO);

        if (Math.abs(contentHeight - targetHeight) > 5) {
            setSize(frameWidth, targetHeight + insets.top + insets.bottom);
        }
    }

    public void addPanel(PanelType panelType, JPanel panel) {
        mainPanel.add(panel, panelType.toString());
    }

    public void showPanel(PanelType panelType) {
        cardLayout.show(mainPanel, panelType.toString());
    }
}