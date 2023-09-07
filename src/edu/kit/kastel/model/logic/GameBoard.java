package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Hexagon;
import edu.kit.kastel.model.entity.Vector2D;
import edu.kit.kastel.model.exceptions.PlaceException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * A game board of a game of hex.
 * @author uhquw
 * @version 1.0.2
 */
public class GameBoard {
    private static final String WHITESPACE = " ";
    private final int size;
    private final Hexagon[][] gameBoard;
    private final boolean[][] depthFirstSearchArray;
    private int placeCount;
    /**
     * Constructs a new game board of a game of hex.
     * @param size the size of the game board
     */
    public GameBoard(final int size) {
        placeCount = 0;
        this.size = size;
        gameBoard = new Hexagon[this.size][this.size];
        depthFirstSearchArray = new boolean[this.size][this.size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                gameBoard[row][column] = Hexagon.PLACEABLE;
                depthFirstSearchArray[row][column] = false;
            }
        }
    }

    /**
     * Gets the size of the game board.
     * @return the size of the game board
     */
    public int getSize() {
        return this.size;
    }
    /**
     * Prints the game board and all its hexagons.
     * @return A visualization of the game board and its hexagons
     */
    public String print() {
        StringBuilder print = new StringBuilder();
        for (int row = 0; row < size; row++) {
            print.append(WHITESPACE.repeat(row));
            for (int column = 0; column < size; column++) {
                print.append(gameBoard[row][column].getSymbol());
                if (column < size - 1) {
                    print.append(WHITESPACE);
                }
            }
            if (row < size - 1) {
                print.append(System.lineSeparator());
            }
        }
        return print.toString();
    }
    /**
     * Places a token on the game board, if no other token is on that hexagon.
     * @param coordinates     the coordinates, where the token should be placed
     * @param token           the token planned to be placed on the game board
     * @throws PlaceException when there already is a token on these coordinates
     */
    public void place(Vector2D coordinates, Hexagon token) {
        int xPos = coordinates.getxPos();
        int yPos = coordinates.getyPos();
        if (gameBoard[yPos][xPos] != Hexagon.PLACEABLE) {
            throw new PlaceException(xPos, yPos);
        } else {
            gameBoard[yPos][xPos] = token;
        }
        placeCount++;
    }

    /**
     * Checks if the player with the given token is a winner.
     * @param token the token of the player, that is checked
     * @return      A boolean if the player is a winner
     */
    public boolean isWinner(Hexagon token) {
        if (this.placeCount < 2 * (this.size) - 1) {
            return false;
        }
        if (token == Hexagon.RED) {
            return checkRedWinner();
        } else if (token == Hexagon.BLUE) {
            return checkBlueWinner();
        }
        return false;
    }

    /**
     * Checks, if the player with the given token can win by placing exactly one token.
     * @param token the token of the player, that is being checked
     * @return      The coordinates, that could lead to a win of the player, if his token is being placed on
     *              Or {@code null} if there is no such coordinate
     */
    public Vector2D winInNextMove(Hexagon token) {
        if (this.placeCount < (this.size * 2) - 2) {
            return null;
        }
        for (int column = 0; column < this.size; column++) {
            for (int row = 0; row < this.size; row++) {
                if (gameBoard[row][column] == Hexagon.PLACEABLE && isWinnersToken(row, column, token)) {
                    return new Vector2D(column, row);
                }
            }
        }
        return null;
    }

    /**
     * Gets the coordinates of the first vacancy along the rows of hexagons.
     * @return the coordinates of this hexagon
     */
    public Vector2D getNextFreeHexagon() {
        for (int row = 0; row < this.size; row++) {
            for (int column = 0; column < this.size; column++) {
                if (gameBoard[row][column] == Hexagon.PLACEABLE) {
                    return new Vector2D(column, row);
                }
            }
        }
        return null;
    }

    private List<Vector2D> getNeighbours(final Vector2D coordinates) {
        int xPos = coordinates.getxPos();
        int yPos = coordinates.getyPos();
        List<Vector2D> neighbours = new ArrayList<>();
        if (xPos > 0) {
            neighbours.add(new Vector2D(xPos - 1, yPos));
            if (yPos < size - 1) {
                neighbours.add(new Vector2D(xPos - 1, yPos + 1));
            }
        }
        if (xPos < this.size - 1) {
            neighbours.add(new Vector2D(xPos + 1, yPos));
            if (yPos > 0) {
                neighbours.add(new Vector2D(xPos + 1, yPos - 1));
            }
        }
        if (yPos > 0) {
            neighbours.add(new Vector2D(xPos, yPos - 1));
        }
        if (yPos < this.size - 1) {
            neighbours.add(new Vector2D(xPos, yPos + 1));
        }
        return neighbours;
    }

    private boolean checkRedWinner() {
        for (int column = 0; column < size; column++) {
            if (gameBoard[0][column] != Hexagon.RED) {
                continue;
            }
            List<Vector2D> traversal = depthFirstSearch(new Vector2D(column, 0), Hexagon.RED);
            if (listIsWinnersPath(traversal, false)) {
                winnersPath(traversal);
                return true;
            }
        }
        return false;
    }
    private boolean checkBlueWinner() {
        for (int row = 0; row < size; row++) {
            if (gameBoard[row][0] != Hexagon.BLUE) {
                continue;
            }
            List<Vector2D> traversal = depthFirstSearch(new Vector2D(0, row), Hexagon.BLUE);
            if (listIsWinnersPath(traversal, true)) {
                winnersPath(traversal);
                return true;
            }
        }
        return false;
    }

    private List<Vector2D> depthFirstSearch(final Vector2D coordinates, final Hexagon token) {
        Deque<Vector2D> stack = new ArrayDeque<>();
        stack.push(coordinates);
        boolean[][] validityBoard = deepCopy();
        List<Vector2D> graph = new ArrayList<>();
        while (!stack.isEmpty()) {
            Vector2D current = stack.pop();
            int row = current.getyPos();
            int column = current.getxPos();
            if (!isValid(validityBoard, row, column)) {
                continue;
            }
            validityBoard[row][column] = true;
            graph.add(current);
            for (Vector2D neighbour : getSameNeighbours(current, token)) {
                stack.push(neighbour);
            }
        }
        return graph;
    }

    private boolean[][] deepCopy() {
        final boolean[][] result = new boolean[this.size][];
        for (int row = 0; row < this.size; row++) {
            result[row] = Arrays.copyOf(depthFirstSearchArray[row], this.size);
        }
        return result;
    }

    private List<Vector2D> getSameNeighbours(final Vector2D coordinates, Hexagon token) {
        List<Vector2D> neighbours = new ArrayList<>();
        for (Vector2D neighbour : getNeighbours(coordinates)) {
            if (gameBoard[neighbour.getyPos()][neighbour.getxPos()] == token) {
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    private boolean isValid(boolean[][] validityBoard, int row, int column) {
        return !validityBoard[row][column];
    }
    private void winnersPath(List<Vector2D> traversal) {
        for (Vector2D node : traversal) {
            gameBoard[node.getyPos()][node.getxPos()] = Hexagon.WINNER;
        }
    }
    private boolean isWinnersToken(final int row, final int column, final Hexagon token) {
        List<Vector2D> sameNeighbours = getSameNeighbours(new Vector2D(column, row), token);
        int neighbours = sameNeighbours.size();
        if (neighbours < 1) {
            return false;
        }
        if (token == Hexagon.BLUE) {
            gameBoard[row][column] = token;
            List<Vector2D> traversal = depthFirstSearch(new Vector2D(column, row), token);
            gameBoard[row][column] = Hexagon.PLACEABLE;
            return listIsWinnersPath(traversal, true);
        } else if (token == Hexagon.RED) {
            gameBoard[row][column] = token;
            List<Vector2D> traversal = depthFirstSearch(new Vector2D(column, row), token);
            gameBoard[row][column] = Hexagon.PLACEABLE;
            return listIsWinnersPath(traversal, false);
        }
        return false;
    }
    private boolean listIsWinnersPath(final List<Vector2D> list, final boolean searchXPos) {
        if (searchXPos) {
            return list.stream().anyMatch(vector2D -> vector2D.getxPos() == 0)
                    && list.stream().anyMatch(vector2D -> vector2D.getxPos() == this.size - 1);
        }
        return list.stream().anyMatch(vector2D -> vector2D.getyPos() == 0)
                && list.stream().anyMatch(vector2D -> vector2D.getyPos() == this.size - 1);
    }
}
