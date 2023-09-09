package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Hexagon;
import edu.kit.kastel.model.entity.Vector2D;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * The BFS algorithm for {@link GameBoard undirected grpahs}.
 * @author uhquw
 * @version 1.0.0
 */
public class BreadthFirstSearch {
    private final GameBoard gameBoard;
    private final int borderLength;
    private final int[][] bfsArray;

    /**
     * Todo.
     * @param gameBoard todo.
     */
    public BreadthFirstSearch(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.borderLength = gameBoard.getSize();
        bfsArray = new int[borderLength][borderLength];
        for (int row = 0; row < borderLength; row++) {
            for (int column = 0; column < borderLength; column++) {
                bfsArray[row][column] = -1;
            }
        }
    }

    /**
     * Todo.
     * @param bfsArray todo.
     * @param root todo.
     * @return todo.
     */
    public List<Vector2D> search(final int[][] bfsArray, final Vector2D root) {
        Queue<Vector2D> bfsQueue = new ArrayDeque<>();
        bfsQueue.add(root);
        bfsArray[root.getyPos()][root.getxPos()] = 0;
        List<Vector2D> shortestBorderNodes = new ArrayList<>();
        while (!bfsQueue.isEmpty()) {
            Vector2D current = bfsQueue.poll();
            int row = current.getyPos();
            int column = current.getxPos();
            if (column == borderLength - 1) {
                if (shortestBorderNodes.isEmpty()) {
                    shortestBorderNodes.add(current);
                }
                int rowOfBorderNode = shortestBorderNodes.get(0).getyPos();
                if (bfsArray[row][column] == bfsArray[rowOfBorderNode][column]) {
                    shortestBorderNodes.add(current);
                } else {
                    break;
                }
            }
            for (Vector2D neighbour : gameBoard.getSameNeighbours(current, Hexagon.PLACEABLE)) {
                int neighbourRow = neighbour.getyPos();
                int neighbourColumn = neighbour.getxPos();
                if (bfsArray[neighbourRow][neighbourColumn] == -1) {
                    bfsQueue.add(neighbour);
                    bfsArray[neighbourRow][neighbourColumn] = bfsArray[row][column] + 1;
                }
            }
        }
        return shortestBorderNodes;
    }

    /**
     * Todo.
     * @return todo.
     */
    public int[][] createNewBFSArray() {
        final int[][] bfsArray = new int[borderLength][];
        for (int row = 0; row < borderLength; row++) {
            bfsArray[row] = Arrays.copyOf(this.bfsArray[row], borderLength);
        }
        return bfsArray;
    }
}
