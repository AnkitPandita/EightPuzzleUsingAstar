package edu.uncc.cci.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Project 01
 * Authors: Ankit Pandita, Jinraj Jain
 */

//Main Class
public class MainPerformSearch {
    public static int[][] goalState;
    public static PriorityQueue<NodeUsingH1> priorityQueueH1;
    public static PriorityQueue<NodeUsingH2> priorityQueueH2;
    public static ArrayList<NodeUsingH1> expandedNodesH1;
    public static ArrayList<NodeUsingH2> expandedNodesH2;

    public static void main(String[] args) {
        priorityQueueH1 = new PriorityQueue<NodeUsingH1>();
        priorityQueueH2 = new PriorityQueue<NodeUsingH2>();
        expandedNodesH1 = new ArrayList<NodeUsingH1>();
        expandedNodesH2 = new ArrayList<NodeUsingH2>();
        Scanner in = new Scanner(System.in);
        int[][] startState;
        goalState = new int[3][3];
        System.out.println("8 Puzzle problem using A* searching");
        //Input start state and goal state
        System.out.println("Enter the elements for the start state:");
        startState = NodeUtil.generateUserInputMatrix(in);
        System.out.println("Enter the elements for the goal state:");
        goalState = NodeUtil.generateUserInputMatrix(in);
        calcUsingMisplacedTiles(startState);
        calcUsingManhattanDistance(startState);
    }

    //Mehod using Heuristic One (Misplaced tiles)
    private static void calcUsingMisplacedTiles(int[][] startState) {
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println("Solving 8 Puzzle using A* based on Misplaced Tiles:");
        long startTime = System.currentTimeMillis();
        //Level is 0
        NodeUsingH1 state = new NodeUsingH1(startState, 0);
        processPuzzlePlayH1(state);
        for (NodeUsingH1 nodeUsingH1 : expandedNodesH1) {
            NodeUtil.printMoveDetails(nodeUsingH1);
        }
        //Check for infinite loop
        if (priorityQueueH1.size() >= NodeUtil.THRESHOLD) {
            System.out.println("Application is performing beyond set threshold value and will be exiting.");
            System.out.println("To increase threshold value, modify the NodeUtil.THRESHOLD variable.");
            System.out.println("NOTE: This will increase the time needed for solution calculation.");
        } else {
            System.out.println("Number of expanded nodes for Misplaced Tiles = " + expandedNodesH1.size());
            System.out.println("Number of generated nodes for Misplaced Tiles = " + (expandedNodesH1.size() + priorityQueueH1.size()));
        }
        long endTime = System.currentTimeMillis();
        // Print Time Taken for Total Execution
        System.out.println("Time Taken for execution for Misplaced Tiles = " + (endTime - startTime) + " milliseconds");
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    //Method using Heuristic Two (Manhattan Distance)
    private static void calcUsingManhattanDistance(int[][] startState) {
        System.out.println("Solving 8 Puzzle using A* based on Manhattan Distance:");
        long startTime = System.currentTimeMillis();
        //Level is 0
        NodeUsingH2 state = new NodeUsingH2(startState, 0);
        processPuzzlePlayH2(state);
        for (NodeUsingH2 nodeUsingH2 : expandedNodesH2) {
            NodeUtil.printMoveDetails(nodeUsingH2);
        }
        //Check for infinite loop
        if (priorityQueueH2.size() >= NodeUtil.THRESHOLD) {
            System.out.println("Application is performing beyond set threshold value and will be exiting");
            System.out.println("To increase threshold value, modify the NodeUtil.THRESHOLD variable");
            System.out.println("NOTE: This will increase the time needed for solution calculation");
        } else {
            System.out.println("Number of expanded nodes for Manhattan Distance = " + expandedNodesH2.size());
            System.out.println("Number of generated nodes for Manhattan Distance = " + (expandedNodesH2.size() + priorityQueueH2.size()));
        }
        long endTime = System.currentTimeMillis();
        // Print Time Taken for Total Execution
        System.out.println("Time Taken for execution using Manhattan Distance = " + (endTime - startTime) + " milliseconds");
        System.out.println("-----------------------------------------------------------------------------------------------");
    }

    // Processes the current state of the Puzzle and PriorityQueue for Heuristic One
    public static void processPuzzlePlayH1(NodeUsingH1 move) {
        priorityQueueH1.add(move);
        ArrayList<NodeUsingH1> childNodesList = new ArrayList<NodeUsingH1>();
        do {
            boolean isNodeVisited;
            //Poll function retrieves and removes the head of this queue
            NodeUsingH1 currentPuzzleState = priorityQueueH1.poll();
            //Once removed it is added to the expandedNodes queue to avoid duplicate processing
            expandedNodesH1.add(currentPuzzleState);
            //Keep checking if goal state has been reached by comparing every element position with the goal state
            if (currentPuzzleState != null && Arrays.deepEquals(currentPuzzleState.stateOfPuzzle, goalState)) {
                break;
            }
            if (currentPuzzleState != null) {
                childNodesList = currentPuzzleState.generateChildNodesH1(currentPuzzleState);
            }
            //Check if expanded node is already visited
            for (NodeUsingH1 childNode : childNodesList) {
                isNodeVisited = false;
                for (NodeUsingH1 expandedNode : expandedNodesH1) {
                    if (Arrays.deepEquals(childNode.stateOfPuzzle, expandedNode.stateOfPuzzle)) {
                        isNodeVisited = true;
                    }
                }
                if (isNodeVisited) {
                    continue;
                }
                priorityQueueH1.add(childNode);
            }
        } while (!priorityQueueH1.isEmpty() && priorityQueueH1.size() <= NodeUtil.THRESHOLD);
    }

    // Processes the current state of the Puzzle and PriorityQueue for Heuristic Two
    public static void processPuzzlePlayH2(NodeUsingH2 move) {
        priorityQueueH2.add(move);
        ArrayList<NodeUsingH2> childNodesList = new ArrayList<NodeUsingH2>();
        do {
            boolean isNodeVisited;
            //Poll function retrieves and removes the head of this queue
            NodeUsingH2 currentPuzzleState = priorityQueueH2.poll();
            //Once removed it is added to the expandedNodes queue to avoid duplicate processing
            expandedNodesH2.add(currentPuzzleState);
            //Keep checking if goal state has been reached by comparing every element position with the goal state
            if (currentPuzzleState != null && Arrays.deepEquals(currentPuzzleState.stateOfPuzzle, goalState)) {
                break;
            }
            if (currentPuzzleState != null) {
                childNodesList = currentPuzzleState.generateChildNodesH2(currentPuzzleState);
            }
            //Check if expanded node is already visited
            for (NodeUsingH2 childNode : childNodesList) {
                isNodeVisited = false;
                for (NodeUsingH2 expandedNode : expandedNodesH2) {
                    if (Arrays.deepEquals(childNode.stateOfPuzzle, expandedNode.stateOfPuzzle)) {
                        isNodeVisited = true;
                    }
                }
                if (isNodeVisited) {
                    continue;
                }
                priorityQueueH2.add(childNode);
            }
        } while (!priorityQueueH2.isEmpty() && priorityQueueH2.size() <= NodeUtil.THRESHOLD);
    }
}