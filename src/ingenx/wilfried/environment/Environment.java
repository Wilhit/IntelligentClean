package com.wilfried.environment;

import com.wilfried.utils.Position;
import com.wilfried.utils.SharedDatas;
import com.wilfried.utils.UpdateInterfaceEvent;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 Environment
 */

public class Environment extends Thread {

    private Piece[][] manoir;
    private ConcurrentLinkedQueue<UpdateInterfaceEvent> updateInterfaceQueue;
    private int nbPiecesPropres = 100;

    public Environement(SharedDatas sharedDatas) {
        this.manoir = sharedDatas.manoir;
        this.updateInterfaceQueue = sharedDatas.updateInterfaceQueue;
    }

    public void run() {

        initializeManoir();

        while (gameIsRunning()) {
            if (shouldThereBeANewDirtySpace()) {
                generateDirt();
            }
            if (shouldThereBeANewLostJewel()) {
                generateJewel();
            }

            updateMesurePerformance();

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeManoir() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.manoir[i][j] = new Piece();
            }
        }
    }

    private Boolean gameIsRunning() {
        return true;
    }

    private Boolean shouldThereBeANewDirtySpace() {
        return new Random().nextDouble() < 0.65;
    }

    private Boolean shouldThereBeANewLostJewel() {
        return new Random().nextDouble() < 0.20;
    }

    private void generateDirt() {

        /* Determination of the placement of the dust */
        Random random = new Random();
        int i = random.nextInt(10);
        int j = random.nextInt(10);

        /* Updating the manoir */
        manoir[i][j].setDirt(true);
		
        
        updateInterfaceQueue.add(new UpdateInterfaceEvent(new Position(i, j), null, "updateContenuPiece"));
    }

    private void generateJewel() {

        /* determination of the position of the jewel */
        Random random = new Random();
        int i = random.nextInt(10);
        int j = random.nextInt(10);

        /* updating the manoir */
        manoir[i][j].setJewel(true);
		
        /* event to indicate that the place is up to date */
        updateInterfaceQueue.add(new UpdateInterfaceEvent(new Position(i, j), null, "updateContenuPiece"));
    }

    private void updateMesurePerformance() {
        this.nbPiecesPropres = 100;

        int nbDust = 0;
        int nbJewels = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(this.manoir[i][j].getJewel() || this.manoir[i][j].getDirt()) {
                    this.nbPiecesPropres--;

                    if(this.manoir[i][j].getJewel()) {
                        nbJewels++;
                    }
                    if(this.manoir[i][j].getDirt()) {
                        nbDust++;
                    }
                }
            }
        }
        updateInterfaceQueue.add(new UpdateInterfaceEvent(null, String.valueOf(nbPiecesPropres),"updateAffichageMesurePerf1"));
        updateInterfaceQueue.add(new UpdateInterfaceEvent(null, String.valueOf(nbDust),"updateAffichageMesurePerf2"));
        updateInterfaceQueue.add(new UpdateInterfaceEvent(null, String.valueOf(nbJewels),"updateAffichageMesurePerf3"));
    }

}
