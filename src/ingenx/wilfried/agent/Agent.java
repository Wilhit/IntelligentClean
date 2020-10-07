package com.wilfried.agent;

import com.wilfried.agent.learning.ModuleLearning;
import com.wilfried.agent.thinking.Thinking;
import com.wilfried.utils.*;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 Agent's Main Class
 */

public class Agent extends Thread {

    private ConcurrentLinkedQueue<UpdateInterfaceEvent> updateInterfaceQueue;
    private ConcurrentLinkedQueue<UpdateNbPointsEvent> updateNbPointsQueue;

    private Thinking thinking;
    private Sensors sensors;
    private Actuators actuators;
    private ModuleLearning moduleLearning;

    private Position position;
    private int nbPoints;

    public Agent(SharedDatas sharedDatas) {
        this.updateInterfaceQueue = sharedDatas.updateInterfaceQueue;
        this.updateNbPointsQueue = sharedDatas.updateNbPointsQueue;
        this.position = new Position(0,0);

        this.thinking = new Thinking();
        this.sensors = new Sensors(sharedDatas);
        this.actuators = new Actuators(sharedDatas);
        this.moduleLearning = new ModuleLearning();
    }

    public void run() {

        while(amIAlive()){

            // observe the environment
            observeEnvironmentWithAllMySensors();
            // update internal state
            updateMyState();

            // choose an action to execute
            if (thinking.getBeliefs().getpositionsDirtsList() != null || thinking.getBeliefs().getpositionsJewelsList() != null) {
                if (!thinking.getBeliefs().getpositionsDirtsList().isEmpty() || !thinking.getBeliefs().getpositionsJewelsList().isEmpty()) {
                    chooseAnAction();
                    justDoIt();
                    moduleLearning.incrementeNbIterationTotales();
                }
            }


            /* Learning */

            /* After a certain time, the cleaner will adapt the the frequency of exploration */
            
			if(moduleLearning.getNbIterationsMax() == moduleLearning.getnbIterationsTotales()) {
				
                /* Saving of number of points gained with the frequency of exploration */
				
                moduleLearning.updatePerformance(moduleLearning.getFrequenceExplorationCourante(), nbPoints);
				
                /* determination of the new frequency of exploration to use (possible the same) */
				
                moduleLearning.chooseBestFrequenceExploration();

                /* Réinitialisation du nombre d'itérations */
				
                moduleLearning.setNbIterationsTotales(0);
            }
        }
    }

    private Boolean amIAlive() {
        return true;
    }

    /* Observe the environment with the sensors */

    private void observeEnvironmentWithAllMySensors() {
        sensors.detectDirts();
        sensors.detectJewels();
    }

    /* update of the agent's beliefs */

    private void updateMyState() {
        thinking.updateMyBeliefs(sensors.getPositionsDirtsList(), sensors.getPositionsJewelsList());
    }

    /* action chosen: intention determined by the desires and beliefs */

    private void chooseAnAction() {
        thinking.updateMyIntentions(this.position);
    }

    /* acting using the actuators */

    private void justDoIt() {

        UpdateNbPointsEvent updateNbPointsEvent;

        /* gathering of the list of actions */
		
        List<Action> actionList = thinking.getIntentions().getActionsList();
		
        /* decision of the real number of actions to be done before the next exploration */
		
        actionList = moduleLearning.decideWhereToStopActions(actionList);

        /* Realisation of actions */

        for(Action action: actionList) {

            try {
                TimeUnit.MILLISECONDS.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /* Usage of the Actuators */
            position = actuators.doAnAction(action, position);

            /* Updating the points */
            if((updateNbPointsEvent = updateNbPointsQueue.poll()) != null) {
                this.nbPoints += updateNbPointsEvent.getNbPoints();
            }

            /* Sending the event to the interface to inform about the change */
            updateInterfaceQueue.add(new UpdateInterfaceEvent(position, null, "updatePositionRobot"));
            updateInterfaceQueue.add(new UpdateInterfaceEvent(position, null,"updateContenuPiece"));
            updateInterfaceQueue.add(new UpdateInterfaceEvent(null, String.valueOf(this.nbPoints), "updateAffichageNbPoints"));
        }
    }

}
