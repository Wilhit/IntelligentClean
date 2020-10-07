package com.wilfried.agent.thinking;

import com.wilfried.utils.Position;
import java.util.List;

/**
 Thinking of the agent
 */

public class Thinking {

    private Beliefs beliefs;
    private Desires desires;
    private Intentions intentions;

    public Thinking() {
        this.beliefs = new Beliefs();
        this.desires = new Desires();
        this.intentions = new Intentions();
    }

    /* updating the beliefs from the information received */

    public void updateMyBeliefs(List<Position> positionsDirt, List<Position> positionJewels) {
        this.beliefs.setpositionsDirtsList(positionsDirt);
        this.beliefs.setpositionsJewelsList(positionJewels);
    }

    /* updating intentions from the beliefs and desires */

    public void updateMyIntentions(Position position) {
        /* if the desires are reached, the intention is to do nothing, we will send back an empty list */
        /* if the desires are not reached, we determined the actions to reach them */

        if(!desires.iWantATotallyCleanMansion(beliefs.getpositionsDirtsList(), beliefs.getpositionsJewelsList())) {
            this.intentions.findPaths(position, beliefs.getpositionsDirtsList(), beliefs.getpositionsJewelsList());
        }
    }

    public Intentions getIntentions() {
        return intentions;
    }

    public Beliefs getBeliefs() {
        return beliefs;
    }

}
