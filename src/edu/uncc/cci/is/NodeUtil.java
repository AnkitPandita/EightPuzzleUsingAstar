package edu.uncc.cci.is;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Project 01
 * Authors: Ankit Pandita, Jinraj Jain
 */

public class NodeUtil {
    //constant to determines how long each heuristic technique will run
    public static final int THRESHOLD = 1000;
    //prints the expanded node
    public static <T extends Node> void printMoveDetails(T Node) {
        System.out.println(Arrays.deepToString(Node.stateOfPuzzle)
                .replace("[", "").replace("], ", "\n").replace("[[", "")
                .replace("]]", "").replace(", ", "\t"));
        System.out.println("\ng(n) = " + Node.level);
        System.out.println("h(n) = " + Node.heuristicDistance);
        System.out.println("f(n) = " + Node.aStarDistance + "\n");
    }

    //method to takes input from user
    public static int[][] generateUserInputMatrix(Scanner input) {
        int[][] puzzleInput = new int[3][3];
        for (int i = 0; i < puzzleInput.length; i++) {
            for (int j = 0; j < puzzleInput[i].length; j++) {
                puzzleInput[i][j] = input.nextInt();
                if (puzzleInput[i][j] < 0 || puzzleInput[i][j] / 9 > 0) {
                    System.out.println("Invalid Entry. Please Try Again.");
                    System.exit(0);
                }
            }
        }
        return puzzleInput;
    }

    // method to move element by swapping
    public static void moveElement(int[][] currentPuzzleState, int row1, int column1, int row2, int column2) {
        int tmp = currentPuzzleState[row1][column1];
        currentPuzzleState[row1][column1] = currentPuzzleState[row2][column2];
        currentPuzzleState[row2][column2] = tmp;
	}
}
