package edu.uncc.cci.is;

import java.util.ArrayList;

/**
 * Project 01
 * Authors: Ankit Pandita, Jinraj Jain
 */

//By counting the number of misplaced tiles
public class NodeUsingH1 extends Node implements Comparable<NodeUsingH1> {

	public NodeUsingH1(int[][] array, int level) {
		int lengthOfArray = array.length;
		this.stateOfPuzzle = new int[lengthOfArray][lengthOfArray];
		for (int i = 0; i < lengthOfArray; i++) {
			System.arraycopy(array[i], 0, this.stateOfPuzzle[i], 0, lengthOfArray);
		}
		this.level = level;
		this.heuristicDistance = calcMisplacedTilesDistance();
		this.aStarDistance = this.level + this.heuristicDistance;
	}

	//method to calculate sum of the distances of the tiles from their goal positions
	private int calcMisplacedTilesDistance() {
		int count = 0;
		int lengthOfArray = MainPerformSearch.goalState.length;
		for(int i=0; i < lengthOfArray; i++) {
			for(int j = 0 ; j < lengthOfArray; j++) {
				if (this.stateOfPuzzle[i][j] == 0) {
					continue;
				}
				if(this.stateOfPuzzle[i][j] != MainPerformSearch.goalState[i][j]) {
					count++;
				}
			}
		}
		return count;
	}

	//Method to generate child nodes
	public ArrayList<NodeUsingH1> generateChildNodesH1(NodeUsingH1 parentNode) {
		ArrayList<NodeUsingH1> childNodes = new ArrayList<NodeUsingH1>();
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (parentNode.stateOfPuzzle[row][column] == 0) {
					//Up
					if (column - 1 >= 0) {
						int[][] a = new int[3][3];
						for (int row1 = 0; row1 < 3; row1++) {
							System.arraycopy(parentNode.stateOfPuzzle[row1], 0, a[row1], 0, 3);
						}
						NodeUtil.moveElement(a, row, column, row, column - 1);
						addChildNodesToList(parentNode, childNodes, a);
					}
					//Down
					if (column + 1 < 3) {
						int[][] a = new int[3][3];
						for (int row2 = 0; row2 < 3; row2++) {
							System.arraycopy(parentNode.stateOfPuzzle[row2], 0, a[row2], 0, 3);
						}
						NodeUtil.moveElement(a, row, column, row, column + 1);
						addChildNodesToList(parentNode, childNodes, a);
					}
					//Left
					if (row - 1 >= 0) {
						int[][] a = new int[3][3];
						for (int row3 = 0; row3 < 3; row3++) {
							System.arraycopy(parentNode.stateOfPuzzle[row3], 0, a[row3], 0, 3);
						}
						NodeUtil.moveElement(a, row, column, row - 1, column);
						addChildNodesToList(parentNode, childNodes, a);
					}
					//Right
					if (row + 1 < 3) {
						int[][] a = new int[3][3];
						for (int row4 = 0; row4 < 3; row4++) {
							System.arraycopy(parentNode.stateOfPuzzle[row4], 0, a[row4], 0, 3);
						}
						NodeUtil.moveElement(a, row, column, row + 1, column);
						addChildNodesToList(parentNode, childNodes, a);
					}
				}
			}
		}
		return childNodes;
	}
	
	//method increments the current level by one for every expansion
	public void addChildNodesToList(NodeUsingH1 parentNode, ArrayList<NodeUsingH1> childNodes, int[][] a) {
		NodeUsingH1 childNode = new NodeUsingH1(a, parentNode.level + 1);
		childNodes.add(childNode);
	}
	
	//comparator that determines the order in which the elements are accessed in the Priority Queue
	@Override
	public int compareTo(NodeUsingH1 nodeUsingH1) {
		return Integer.compare(this.aStarDistance, nodeUsingH1.aStarDistance);
	}
}