package com.wilfried.agent;

import com.wilfried.environment.Piece;
import com.wilfried.utils.Position;
import com.wilfried.utils.SharedDatas;

import java.util.ArrayList;
import java.util.List;

/**
 Sensors of the agent
 */

public class Sensors {

    private Piece[][] manoir;
    private List<Position> positionsDirtsList;
    private List<Position> positionsJewelsList;

    public Sensors(SharedDatas sharedDatas) {
        this.manoir = sharedDatas.manoir;
        this.positionsDirtsList = new ArrayList<>();
        this.positionsJewelsList = new ArrayList<>();
    }

    /* Detection of Dust */

    public void detectDirts() {
        this.positionsDirtsList.clear();

        /* Detection of new positions of Dust */
        for (int i=0; i<10; i++) {
            for(int j=0; j<10; j++) {
                if(manoir[i][j] != null && manoir[i][j].getDirt()) {
                    this.positionsDirtsList.add(new Position(i,j));
                }
            }
        }
    }

    /* Detection of Jewels */

    public void detectJewels() {
        this.positionsJewelsList.clear();
        /* Detection of new positions of Jewels */
        for (int i=0; i<10; i++) {
            for(int j=0; j<10; j++) {
                if(manoir[i][j] != null && manoir[i][j].getJewel()) {
                    this.positionsJewelsList.add(new Position(i,j));
                }
            }
        }
    }

    public List<Position> getPositionsDirtsList() {
        return positionsDirtsList;
    }

    public List<Position> getPositionsJewelsList() {
        return positionsJewelsList;
    }
}
