import controller.MenuController;
import view.GameFrame;
import view.panel.GamePanel;
import view.panel.MenuPanel;
import view.panel.PanelType;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            MenuPanel menuPanel = new MenuPanel();
            GamePanel gamePanel = new GamePanel();

            frame.addPanel(PanelType.MENU, menuPanel);
            frame.addPanel(PanelType.GAME, gamePanel);

            MenuController menuController = new MenuController(frame, gamePanel);
            gamePanel.setMenuController(menuController);

            menuPanel.getStartButton().addActionListener(_ -> menuController.onStartGame());
            menuPanel.getExitButton().addActionListener(_ -> menuController.onExitGame());

            frame.showPanel(PanelType.MENU);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
