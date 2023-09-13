package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Hexagon;
import edu.kit.kastel.model.entity.Vector2D;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

/**
 * This class describes a graph traverser, which traverses a graph according to certain search algorithms.
 * @author uhquw
 * @version 1.0.0
 */
public class GraphTraverser {
    private final GameBoard gameBoard;
    private final int borderLength;
    private final boolean[][] dfsArray;
    private final int[][] bfsArray;
    private boolean isUniquePath;
    private final List<Vector2D> uniquePath;

    /**
     * Constructs a new GraphTraverser.
     * @param gameBoard the game board the traverser is used on
     */
    public GraphTraverser(final GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        borderLength = gameBoard.getSize();
        dfsArray = new boolean[borderLength][borderLength];
        bfsArray = new int[borderLength][borderLength];
        for (int row = 0; row < borderLength; row++) {
            for (int column = 0; column < borderLength; column++) {
                dfsArray[row][column] = false;
                bfsArray[row][column] = -1;
            }
        }
        isUniquePath = true;
        uniquePath = new ArrayList<>();
    }

    /**
     * Executes a depth first search on the traversers game board.
     * @param root  the root, where the dfs starts
     * @param token the token being searched for
     * @return the spanning tree of the dfs
     */
    public List<Vector2D> dfs(final Vector2D root, final Hexagon token) {
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

    /**
     * Gets the optimal hexagon for HeroAI.
     * @param root The hexagon, where the search for the optimal hexagon starts
     * @return the optimal hexagon according to HeroAI
     *         or {@code null} when there is no optimal hexagon
     */
    public Vector2D getOptimalHexagon(final Vector2D root) {
        int[][] bfsArray = createNewBFSArray();
        List<Vector2D> shortestNodes = bfs(bfsArray, root);
        Vector2D optimalHexagon = null;
        boolean[][] validityBoard = createNewDFSArray();
        for (Vector2D node : shortestNodes) {
            optimalHexagon = getCorrectHexagon(optimalHexagon, node, bfsArray, validityBoard);
        }
        if (isUniquePath && optimalHexagon != null) {
            optimalHexagon = mostWesternVector(uniquePath);
        }
        return optimalHexagon;
    }

    /**
     * Performs a bfs over the graph, with a given root.
     * @param bfsArray the bfs array containing the bfs numbers of the graph
     * @param root      the root of the bfs spanning tree
     * @return A list of Hexagons containing the vectors at the eastern boarder, with the lowest bfs number
     */
    private List<Vector2D> bfs(final int[][] bfsArray, final Vector2D root) {
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
     * Performs a dfs starting at the root and traversing all the nodes with 1 less BFS number.
     * @param correctVector the vector which is the correct Vector for HeroAI at the moment
     * @param root          the root of the dfs spanning tree
     * @param bfsArray      the bfs array containing the bfs number of every hexagon
     * @param validityBoard the validity board, checking if a hexagon is visited or not
     * @return the optimal hexagon for HeroAI at the moment
     */
    private Vector2D getCorrectHexagon(final Vector2D correctVector, final Vector2D root, final int[][] bfsArray,
                                       boolean[][] validityBoard) {
        Vector2D result = correctVector;
        Deque<Vector2D> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Vector2D current = stack.pop();
            int row = current.getyPos();
            int column = current.getxPos();
            if (validityBoard[row][column]) {
                continue;
            }
            validityBoard[row][column] = true;
            if (isUniquePath) {
                uniquePath.add(current);
            }
            if (bfsArray[row][column] == 1) {
                result = moreWesternVector(correctVector, current);
                continue;
            }
            List<Vector2D> neighbours = gameBoard.getSameNeighbours(current, Hexagon.PLACEABLE);
            setUniquePathFalse(neighbours.size());
            for (Vector2D neighbour : neighbours) {
                int nRow = neighbour.getyPos();
                int nColumn = neighbour.getxPos();
                if (bfsArray[nRow][nColumn] + 1 == bfsArray[row][column]) {
                    stack.push(neighbour);
                }
            }
        }
        return result;
    }

    private boolean[][] createNewDFSArray() {
        final boolean[][] dfsArray = new boolean[borderLength][];
        for (int row = 0; row < borderLength; row++) {
            dfsArray[row] = Arrays.copyOf(this.dfsArray[row], borderLength);
        }
        return dfsArray;
    }

    private int[][] createNewBFSArray() {
        final int[][] bfsArray = new int[borderLength][];
        for (int row = 0; row < borderLength; row++) {
            bfsArray[row] = Arrays.copyOf(this.bfsArray[row], borderLength);
        }
        return bfsArray;
    }
    private void setUniquePathFalse(final int children) {
        if (children > 1) {
            this.isUniquePath = false;
        }
    }
    //Gets the more western vector of the given one.
    private Vector2D moreWesternVector(Vector2D current, Vector2D comparable) {
        if (current == null) {
            return comparable;
        }
        if (current.getxPos() > comparable.getxPos()) {
            return comparable;
        } else if (current.getxPos() < comparable.getxPos()) {
            return current;
        } else if (current.getyPos() > comparable.getyPos()) {
            return comparable;
        } else {
            return current;
        }
    }
    //Gets the most western vector of a list of vectors.
    private Vector2D mostWesternVector(List<Vector2D> list) {
        Vector2D westVector = list.get(0);
        for (Vector2D vector2D : list) {
            westVector = moreWesternVector(westVector, vector2D);
        }
        return westVector;
    }
}
