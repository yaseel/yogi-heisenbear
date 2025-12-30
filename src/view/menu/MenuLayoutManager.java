package view.menu;

import java.util.List;

public class MenuLayoutManager {

    public void updateLayout(List<MenuButton> buttons, int panelWidth, int panelHeight) {
        int baseX = (int) (panelWidth * 0.2);
        int baseY = (int) (panelHeight * 0.54);
        int spacing = (int) (panelHeight * 0.09);
        int buttonWidth = (int) (panelWidth * 0.3);
        int buttonHeight = (int) (panelHeight * 0.08);

        for (int i = 0; i < buttons.size(); i++) {
            int y = baseY + i * spacing;
            buttons.get(i).setBounds(baseX, y, buttonWidth, buttonHeight);
        }
    }
}
