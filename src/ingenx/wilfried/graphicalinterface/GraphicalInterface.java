package com.wilfried.graphicalinterface;

import com.wilfried.environment.Piece;
import com.wilfried.utils.SharedDatas;
import com.wilfried.utils.UpdateInterfaceEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 Graphical Interface
 */

public class GraphicalInterface extends Thread {

    private Piece[][] manoir;
    private Window window;
    private ConcurrentLinkedQueue<UpdateInterfaceEvent> updateInterfaceQueue;

    /* Interface initialisation */

    public GraphicalInterface(SharedDatas sharedDatas) {
        this.manoir = sharedDatas.manoir;
        this.updateInterfaceQueue = sharedDatas.updateInterfaceQueue;
        window = new Window();
    }

    public void run() {
        UpdateInterfaceEvent updateInterfaceEvent;
        int i, j;

        while(true) {
            if((updateInterfaceEvent = updateInterfaceQueue.poll()) != null) {
                switch (updateInterfaceEvent.getTypeEvent()) {
                    case "updateContenuPiece":
                        i = updateInterfaceEvent.getPosition().getI();
                        j = updateInterfaceEvent.getPosition().getJ();
                        fenetre.updatePiece(i, j, manoir[i][j].getDirt(), manoir[i][j].getJewel());
                        break;
                    case "updatePositionRobot":
                        i = updateInterfaceEvent.getPosition().getI();
                        j = updateInterfaceEvent.getPosition().getJ();
                        fenetre.updatePositionRobot(i, j);
                        break;
                    case "updateDisplayMeasurePerf1":
                        fenetre.updateNbPiecesPropresLabel(updateInterfaceEvent.getInformations());
                        break;
                    case "updateDisplayMeasurePerf2":
                        fenetre.updateNbPoussieresLabel(updateInterfaceEvent.getInformations());
                        break;
                    case "updateDisplayMeasurePerf3":
                        fenetre.updateNbJewelsLabel(updateInterfaceEvent.getInformations());
                        break;
                    case "updateDisplayNbPoints":
                        fenetre.updateNbPoints(updateInterfaceEvent.getInformations());
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
