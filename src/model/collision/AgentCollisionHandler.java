package model.collision;

import model.entity.agent.Agent;
import model.level.Level;
import model.entity.yogi.YogiBear;

import java.awt.Rectangle;

public class AgentCollisionHandler {
    private YogiBear yogi;
    private Level level;

    public AgentCollisionHandler(YogiBear yogi, Level level) {
        this.yogi = yogi;
        this.level = level;
    }

    public boolean checkAgentCollisions() {
        Rectangle yogiBounds = yogi.getHitbox();
        boolean caught = false;

        for (Agent agent : level.getAgents()) {
            if (!caught && yogiBounds.intersects(agent.getHitbox())) {
                caught = true;
            }
            if (!caught && agent.canSeeYogi(yogi)) {
                caught = true;
            }
        }

        return caught;
    }
}
