package com.wilfried.agent;

import com.wilfried.environment.Piece;
import com.wilfried.utils.*;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 Actuators of the Agent
 */

public class Actuators {

    private Piece[][] manoir;
    private ConcurrentLinkedQueue<UpdateNbPointsEvent> updateNbPointsQueue;

    public Actuators(SharedDatas sharedDatas) {
        this.manoir = sharedDatas.manoir;
        this.updateNbPointsQueue = sharedDatas.updateNbPointsQueue;
    }

    /* Realisation of the requested action */

    public Position doAnAction(Action action, Position position) {

        Position newPosition = position;

        switch (action) {
            case DOWN:
                newPosition = movingDown(position);
                break;
				
            case UP:
                newPosition = movingUp(position);
                break;
				
            case RIGHT:
                newPosition = movingRight(position);
                break;
				
            case LEFT:
                newPosition = movingLeft(position);
                break;
				
            case CLEAN:
                cleanDirt(position);
                System.out.println("I clean");
                break;
				
            case PICKUP:
                collectJewels(position);
                System.out.println("I pick up");
                break;
				
            case CLEANANDPICKUP:
                cleanDirtAndCollectJewels(position);
                System.out.println("I clean and pick up");
                break;
				
            default:
                break;
        }
        return newPosition;
    }

    /* cleaning of the dust  */

    public void cleanDirt(Position position) {
        if(manoir[position.getI()][position.getJ()].getDirt()) {
            manoir[position.getI()][position.getJ()].setDirt(false);
            updateNbPointsQueue.add(new UpdateNbPointsEvent(2));
        }
        if(manoir[position.getI()][position.getJ()].getJewel()) {
            manoir[position.getI()][position.getJ()].setJewel(false);
            updateNbPointsQueue.add(new UpdateNbPointsEvent(-8));
        }
    }

    /* Picking up the jewels  */

    public void collectJewels(Position position) {
        if(manoir[position.getI()][position.getJ()].getJewel()) {
            manoir[position.getI()][position.getJ()].setJewel(false);
            updateNbPointsQueue.add(new UpdateNbPointsEvent(4));
        }
    }

        /* cleaning the dust and picking up the jewels  */

    public void cleanDirtAndCollectJewels(Position position) {
        if(manoir[position.getI()][position.getJ()].getDirt()) {
            manoir[position.getI()][position.getJ()].setDirt(false);
            updateNbPointsQueue.add(new UpdateNbPointsEvent(2));
        }
        if(manoir[position.getI()][position.getJ()].getJewel()) {
            manoir[position.getI()][position.getJ()].setJewel(false);
            updateNbPointsQueue.add(new UpdateNbPointsEvent(4));
        }
    }

    /* moving the robot to the left  */

    public Position movingLeft(Position position) {
        Position newPosition = position;
        if (position.getJ()-1 >= 0) {
            newPosition.setJ(position.getJ()-1);
            updateNbPointsQueue.add(new UpdateNbPointsEvent(-1));
        }
        return newPosition;
    }

    /* moving the robot to the right  */

    public Position movingRight(Position position) {
        Position newPosition = position;
        if (position.getJ()+1 < 10) {
            newPosition.setJ(position.getJ()+1);
            updateNbPointsQueue.add(new UpdateNbPointsEvent(-1));
        }
        return newPosition;
    }

    /* moving the robot up */

    public Position movingUp(Position position) {
        Position newPosition = position;
        if (position.getI()-1 >= 0) {
            newPosition.setI(position.getI()-1);
            updateNbPointsQueue.add(new UpdateNbPointsEvent(-1));
        }
        return newPosition;
    }

    /* moving the robot down  */

    public Position movingDown(Position position) {
        Position newPosition = position;
        if (position.getI()+1 < 10) {
            newPosition.setI(position.getI()+1);
            updateNbPointsQueue.add(new UpdateNbPointsEvent(-1));
        }
        return newPosition;
    }
}
