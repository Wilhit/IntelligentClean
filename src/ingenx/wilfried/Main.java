package com.wilfried;

import com.wilfried.agent.Agent;
import com.wilfried.environment.Environment;
import com.wilfried.graphicalinterface.GraphicalInterface;
import com.wilfried.utils.Position;
import com.wilfried.utils.SharedDatas;
import com.wilfried.utils.UpdateInterfaceEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 Main Progam
 */

public class Main {

    public static void main(String[] args) {

        SharedDatas sharedDatas = new SharedDatas();

        /* environment */
        Environment environment = new Environment(sharedDatas);
        /* agent */
        Agent agent = new Agent(sharedDatas);
        /* Graphical Interface */
        GraphicalInterface graphicalinterface = new GraphicalInterface(sharedDatas);

        /* Start */
        environment.start();
        agent.start();
        graphicalinterface.start();
    }
}
