package com.wilfried.agent.learning;

import com.wilfried.utils.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleLearning {

    private int nbIterationsMax;
    private int nbIterationsTotales;
    private FrequencesExploration frequenceExplorationCourante;
    Map<FrequencesExploration, Integer> performancesMap;

    public ModuleLearning() {
        this.nbIterationsMax = 10;
        this.nbIterationsTotales = 0;
        this.frequenceExplorationCourante = FrequencesExploration.TOTALE;
        this.performancesMap = new HashMap<>();

        performancesMap.put(FrequencesExploration.TOTALE, null);
        performancesMap.put(FrequencesExploration.HALF, null);
        performancesMap.put(FrequencesExploration.QUARTER, null);
    }

    public List<Action> decideWhereToStopActions(List<Action> actionsList) {

        /* if you are too close,  we go anyways */
        if(actionsList.size() < 4) {
            return actionsList;
        }

        /* else, we do not do anything */
        switch (frequenceExplorationCourante) {
            case TOTALE:
                return actionsList;
            case HALF:
                return  actionsList.subList(0, (actionsList.size() + 1) / 2);
            case QUARTER:
                return  actionsList.subList(0, (actionsList.size() + 1) / 4);
            default:
                return actionsList;
        }
    }

    public void updatePerformance(FrequencesExploration frequencesExploration, int performance) {
        performancesMap.put(frequencesExploration, performance);
    }

    public void chooseBestFrequenceExploration() {

        int bestFrequence = performancesMap.get(FrequencesExploration.TOTALE);

        for (Map.Entry<FrequencesExploration, Integer> entry : performancesMap.entrySet()) {
            if(entry.getValue() == null) {
                frequenceExplorationCourante = entry.getKey();
                break;
            }
            if(entry.getValue() >= bestFrequence) {
                frequenceExplorationCourante = entry.getKey();
            }
        }

    }

    public void incrementeNbIterationTotales() {
        this.nbIterationsTotales++;
    }

    public int getNbIterationsMax() {
        return nbIterationsMax;
    }

    public void setNbIterationsMax(int nbIterationsMax) {
        this.nbIterationsMax = nbIterationsMax;
    }

    public int getnbIterationsTotales() {
        return nbIterationsTotales;
    }

    public void setNbIterationsTotales(int nbIterationsTotales) {
        this.nbIterationsTotales = nbIterationsTotales;
    }

    public FrequencesExploration getFrequenceExplorationCourante() {
        return frequenceExplorationCourante;
    }

    public void setFrequenceExploration(FrequencesExploration frequenceExplorationCourante) {
        this.frequenceExplorationCourante = frequenceExplorationCourante;
    }
}
