package edu.kit.kastel.model.entity;

import java.util.Arrays;
import java.util.List;

/**
 * Todo.
 * @author uhquw
 * @version 1.0.0
 */
public class OvergoingClass {

    private final boolean[][] depthFirstSearchArray;
    private final int[][] breadthFirstSearchArray;
    private final int size;

    /**
     * Todo.
     * @param size todo
     */
    public OvergoingClass(int size) {
        this.size = size;
        depthFirstSearchArray = new boolean[size][size];
        breadthFirstSearchArray = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                depthFirstSearchArray[row][column] = false;
                breadthFirstSearchArray[row][column] = -1;
            }
        }
    }

    /**
     * Creates and returns a deep copy of the DFS array, so for every DFS there is a new "unvisited" array.
     * @return A deep copy of the DFS array
     */
    public boolean[][] deepCopyOfBoolean() {
        final boolean[][] result = new boolean[size][];
        for (int row = 0; row < size; row++) {
            result[row] = Arrays.copyOf(depthFirstSearchArray[row], size);
        }
        return result;
    }

    /**
     * Todo.
     * @return todo.
     */
    public int[][] deepCopyOfInt() {
        final int[][] result = new int[size][];
        for (int row = 0; row < size; row++) {
            result[row] = Arrays.copyOf(breadthFirstSearchArray[row], size);
        }
        return result;
    }

    /**
     * Todo.
     * @param current   todo.
     * @param comparable    todo.
     * @return  todo.
     */
    public Vector2D moreWestVector(Vector2D current, Vector2D comparable) {
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

    /**
     * Todo.
     * @param list todo.
     * @return  todo.
     */
    public Vector2D westVector(List<Vector2D> list) {
        Vector2D westVector = list.get(0);
        for (Vector2D vector2D : list) {
            westVector = moreWestVector(westVector, vector2D);
        }
        return westVector;
    }
}
