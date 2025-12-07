package view.collision;

import model.agent.Agent;
import model.level.Level;
import model.yogi.YogiBear;

import java.awt.Rectangle;

public class AgentCollisionHandler {
    private YogiBear yogi;
    private Level level;

    public AgentCollisionHandler(YogiBear yogi, Level level) {
        this.yogi = yogi;
        this.level = level;
    }

    public boolean checkAgentCollisions() {
        Rectangle yogiBounds = yogi.getBounds();

        for (Agent agent : level.getAgents()) {
            if (yogiBounds.intersects(agent.getBounds())) {
                return true;
            }
            if (agent.canSeeYogi(yogi)) {
                return true;
            }
        }
        return false;
    }
}
