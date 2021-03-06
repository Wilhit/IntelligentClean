package com.wilfried.agent.exploration;

import com.wilfried.utils.Node;
import com.wilfried.utils.Position;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 Informed Exploration 
 */

public class AStarPathFinder {

    private List<Node> openList;
    private List<Node> closedList;

    public List<Node> AStarPathFinder(Position startPos, Position targetPos) {
        openList = new ArrayList<>();
        closedList = new ArrayList<>();

        Node current = new Node();
        current.setPosition(startPos);
        current.setgCost(0);
        current.sethCost(manhattanDistance(targetPos.getI(),targetPos.getJ(),startPos.getI(),startPos.getJ()));
        current.setfCost(current.getgCost()+current.gethCost());
        current.setParent(null);

        closedList.add(current);
        addAdjacentNode(current,startPos,targetPos);

        while(!((current.getPosition().getI() == targetPos.getI()) && (current.getPosition().getJ() == targetPos.getJ())) && (!openList.isEmpty())) {
            // we look for the best open node of lists, we know it is not empty
            current = bestNode(openList);
            closedList.add(current);
            openList.remove(current);
            addAdjacentNode(current,startPos,targetPos);
        }

        return closedList;
    }

    private void addAdjacentNode(Node current, Position startPos, Position targetPos) {
        for (int j=current.getPosition().getJ()-1;j<=current.getPosition().getJ()+1;j++) {
            if (j<0 || j>9) { continue; }
            for (int i=current.getPosition().getI()-1;i<=current.getPosition().getI()+1;i++) {
                if (i<0 || i>9) { continue; }
                if (j==current.getPosition().getJ() && i==current.getPosition().getI()) { continue; }

                if (j==current.getPosition().getJ()-1 && i==current.getPosition().getI()-1) { continue; }
                if (j==current.getPosition().getJ()-1 && i==current.getPosition().getI()+1) { continue; }
                if (j==current.getPosition().getJ()+1 && i==current.getPosition().getI()-1) { continue; }
                if (j==current.getPosition().getJ()+1 && i==current.getPosition().getI()+1) { continue; }

                Node tmp = new Node();
                Position adjacentPos = new Position(i,j);
                tmp.setPosition(adjacentPos);
                if (!alreadyInList(tmp,closedList)) {
                    tmp.setParent(current);
                    tmp.setgCost(current.getgCost()+1);
                    tmp.sethCost(manhattanDistance(j,i,targetPos.getJ(),targetPos.getI()));
                    tmp.setfCost(tmp.getgCost()+tmp.gethCost());

                    if (alreadyInList(tmp, openList)){
                        /* in the open list, we compare the costs */
                        for (Node node : openList) {
                            if (tmp.getfCost()<node.getfCost()) openList.add(tmp);break;
                        }
                    } else {
                        /* not in the open list, we add  */
                        openList.add(tmp);
                    }
                }
            }
        }
    }

    /* Calculate the distance between two positions */
    public int manhattanDistance(int x1, int y1, int x2, int y2){
        return abs(x2 - x1) + abs(y2 - y1);
    }

    /* Check if a node is in a list already */
    private boolean alreadyInList(Node node, List<Node> list){
        for (Node index : list) {
            if (node.getPosition().getI()==index.getPosition().getI() && node.getPosition().getJ()==index.getPosition().getJ()) return true;
        }
        return false;
    }

    private Node bestNode(List<Node> list){
        Node bestNode = list.get(0);
        for (Node node : list) {
            if (node.getfCost() < bestNode.getfCost()) bestNode=node;
            if (node.getfCost() == bestNode.getfCost()) {
                if (node.gethCost() < bestNode.gethCost()) bestNode=node;
            }
        }
        return bestNode;
    }
}
