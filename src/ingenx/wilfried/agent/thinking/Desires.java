package com.wilfried.agent.thinking;

import com.wilfried.utils.Position;

import java.util.List;

/**
 Agent's Desires
 */

public class Desires {

    public Desires() {

    }

    /* Desire: I would like the manoir to be very clean and without jewels */

    public Boolean iWantATotallyCleanMansion(List<Position> positionsDirtsList, List<Position> positionsJewelsList) {

        Boolean mansionIsClean = false;

        if(positionsDirtsList.isEmpty() && positionsJewelsList.isEmpty()) {
            mansionIsClean = true;
        }

        return mansionIsClean;
    }

}
