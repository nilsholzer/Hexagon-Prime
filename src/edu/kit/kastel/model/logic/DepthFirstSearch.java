package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Hexagon;
import edu.kit.kastel.model.entity.Vector2D;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * The DFS algorithm for {@link GameBoard undirected grpahs}.
 * @author uhquw
 * @version 1.0.0
 */
public class DepthFirstSearch implements SearchAlgorithm {
    private final GameBoard gameBoard;
    private final Hexagon[][] graph;
    private final int borderLength;
    private final boolean[][] dfsArray;

    /**
     * Todo.
     * @param gameBoard todo.
     */
    public DepthFirstSearch(final GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.graph = gameBoard.getGraph();
        this.borderLength = graph.length;
        dfsArray = new boolean[borderLength][borderLength];
        for (int row = 0; row < borderLength; row++) {
            for (int column = 0; column < borderLength; column++) {
                dfsArray[row][column] = false;
            }
        }
    }

    @Override
    public List<Vector2D> search(final Vector2D root, final Hexagon token) {
        Deque<Vector2D> dfsStack = new ArrayDeque<>();
        dfsStack.push(root);
        boolean[][] validityBoard = createNewDFSArray();
        List<Vector2D> spanningTree = new ArrayList<>();
        while (!dfsStack.isEmpty()) {
            Vector2D current = dfsStack.pop();
            int row = current.getyPos();
            int column = current.getxPos();
            if (validityBoard[row][column]) {
                continue;
            }
            validityBoard[row][column] = true;
            spanningTree.add(current);
            for (Vector2D neighbour : gameBoard.getSameNeighbours(current, token)) {
                dfsStack.push(neighbour);
            }
        }
        return spanningTree;
    }

    private boolean[][] createNewDFSArray() {
        final boolean[][] dfsArray = new boolean[borderLength][];
        for (int row = 0; row < borderLength; row++) {
            dfsArray[row] = Arrays.copyOf(this.dfsArray[row], borderLength);
        }
        return dfsArray;
    }
}
