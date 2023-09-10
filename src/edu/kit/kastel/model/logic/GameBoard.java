package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Hexagon;
import edu.kit.kastel.model.entity.OvergoingClass;
import edu.kit.kastel.model.entity.Vector2D;
import edu.kit.kastel.model.exceptions.PlaceException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * A game board of a game of hex.
 * @author uhquw
 * @version 1.0.3
 */
public class GameBoard {
    private static final String WHITESPACE = " ";
    private final int size;
    private final GraphTraverser graphTraverser;
    private final BreadthFirstSearch bfs;
    private final Hexagon[][] gameBoard;
    private final OvergoingClass overgoingClass;
    private int placeCount;
    /**
     * Constructs a new game board of a game of hex.
     * @param size the size of the game board
     */
    public GameBoard(final int size) {
        placeCount = 0;
        this.size = size;
        gameBoard = new Hexagon[this.size][this.size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                gameBoard[row][column] = Hexagon.PLACEABLE;
            }
        }
        bfs = new BreadthFirstSearch(this);
        graphTraverser = new GraphTraverser(this);
        overgoingClass = new OvergoingClass(size);
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
     * If the placeCount is lower than double the size - 1, it is not possible that there is a winner.
     * @param token the token of the player, that is checked
     * @return      A boolean if the player is a winner
     */
    public boolean isWinner(Hexagon token) {
        if (this.placeCount < 2 * (size) - 1) {
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
        if (this.placeCount < (size * 2) - 2) {
            return null;
        }
        for (int column = 0; column < size; column++) {
            for (int row = 0; row < size; row++) {
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
     *         or{@code null} if there is no free hexagon on the gameboard, but in that case there is already a winner.
     */
    public Vector2D getNextFreeHexagon() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (gameBoard[row][column] == Hexagon.PLACEABLE) {
                    return new Vector2D(column, row);
                }
            }
        }
        return null;
    }

    /**
     * Todo.
     * @return Todo.
     */
    public Vector2D getEasternHexagon() {
        for (int column = 0; column < size; column++) {
            for (int row = 0; row < size; row++) {
                if (gameBoard[row][column] == Hexagon.PLACEABLE) {
                    return new Vector2D(column, row);
                }
            }
        }
        return null;
    }

    /**
     * Finds the shortest path of free hexagons to the eastern border starting at the given coordinate.
     * @param root the coordinates of the starting hexagon
     * @return todo.
     */
    public Vector2D getHeroAIMove(Vector2D root) {
        return graphTraverser.getOptimalHexagon(root);
        /*int[][] bfsArray = bfs.createNewBFSArray();
        List<Vector2D> shortestPath = bfs.search(bfsArray, root);
        if (shortestPath.isEmpty()) {
            return null;
        }
        return getCorrectVector(shortestPath, bfsArray);*/
    }

    /**
     * Todo.
     * @return todo.
     */
    protected Hexagon[][] getGraph() {
        return gameBoard.clone();
    }

    //Gets all the neighbours from a hexagon with the given coordinates.
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

    /**
     * Checks for the player with the red token, if he has won.
     * If he has a token on the top and on the bottom and they are connected via a path, he has won.
     * @return A boolean telling if he has won
     */
    private boolean checkRedWinner() {
        for (int column = 0; column < size; column++) {
            if (gameBoard[0][column] != Hexagon.RED) {
                continue;
            }
            List<Vector2D> traversal = graphTraverser.dfs(new Vector2D(column, 0), Hexagon.RED);
            if (listIsWinnersPath(traversal, false)) {
                winnersPath(traversal);
                return true;
            }
        }
        return false;
    }
    //Same procedure as checkRedWinner, expect it checks for a connected path from left to right.
    private boolean checkBlueWinner() {
        for (int row = 0; row < size; row++) {
            if (gameBoard[row][0] != Hexagon.BLUE) {
                continue;
            }
            List<Vector2D> traversal = graphTraverser.dfs(new Vector2D(0, row), Hexagon.BLUE);
            if (listIsWinnersPath(traversal, true)) {
                winnersPath(traversal);
                return true;
            }
        }
        return false;
    }

    /**
     * Todo.
     * @param coordinates todo.
     * @param token todo.
     * @return  todo.
     */
    public List<Vector2D> getSameNeighbours(final Vector2D coordinates, Hexagon token) {
        List<Vector2D> neighbours = new ArrayList<>();
        for (Vector2D neighbour : getNeighbours(coordinates)) {
            if (gameBoard[neighbour.getyPos()][neighbour.getxPos()] == token) {
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    //Checks if the hexagon with the given coordinates is marked as visited in the DFS.
    private boolean isValid(boolean[][] validityBoard, int row, int column) {
        return !validityBoard[row][column];
    }

    /**
     * Creates the path on the gameboard, that was used so the player could win.
     * @param traversal A list containing all the nodes that were visited on the path
     */
    private void winnersPath(List<Vector2D> traversal) {
        for (Vector2D node : traversal) {
            gameBoard[node.getyPos()][node.getxPos()] = Hexagon.WINNER;
        }
    }

    /**
     * Checks if there is a winner, when a token is placed on the node with the given coordinates.
     * @param row    the row of the node
     * @param column the column of the node
     * @param token  the token that is being placed on the node
     * @return A Boolean representing, if there is a winner, if the token was placed.
     */
    private boolean isWinnersToken(final int row, final int column, final Hexagon token) {
        List<Vector2D> sameNeighbours = getSameNeighbours(new Vector2D(column, row), token);
        int neighbours = sameNeighbours.size();
        boolean hexagonOnEdge = isOnEdge(row, column);
        //If the hexagon has less than one neighbour on the edge or less than 2 neighbours in the middle of the board
        //There is no winning path including this hexagon.
        if (!hexagonOnEdge && neighbours < 2 || hexagonOnEdge && neighbours < 1) {
            return false;
        }
        gameBoard[row][column] = token;
        List<Vector2D> traversal = graphTraverser.dfs(new Vector2D(column, row), token);
        gameBoard[row][column] = Hexagon.PLACEABLE;
        return listIsWinnersPath(traversal, token == Hexagon.BLUE);
    }

    /**
     * Checks if the given list is a winners path or not.
     * @param list       the list containing a path
     * @param playerBlue a boolean telling if the path should be tested as a winning path for player blue or red
     * @return A boolean
     */
    private boolean listIsWinnersPath(final List<Vector2D> list, final boolean playerBlue) {
        if (playerBlue) {
            //Lambda expression checking the list, if it contains coordinates with the most eastern and western coordinate.
            return list.stream().anyMatch(vector2D -> vector2D.getxPos() == 0)
                    && list.stream().anyMatch(vector2D -> vector2D.getxPos() == size - 1);
        }
        //Lambda expression checking the list, if it contains coordinates with the most northern and southern coordinate.
        return list.stream().anyMatch(vector2D -> vector2D.getyPos() == 0)
                && list.stream().anyMatch(vector2D -> vector2D.getyPos() == size - 1);
    }
    //Checks if the heaxagon with the given coordinates is on the edge of the gameboard.
    private boolean isOnEdge(final int row, final int column) {
        return row == 0 || column == 0 || row == size - 1 || column == size - 1;
    }
    private Vector2D getCorrectVector(final List<Vector2D> list, int[][] bfsArray) {
        Vector2D correctVector = null;
        boolean[][] validityBoard = overgoingClass.deepCopyOfBoolean();
        boolean isUniquePath = list.size() == 1;
        List<Vector2D> uniquePath = new ArrayList<>();
        Deque<Vector2D> stack = new ArrayDeque<>();
        for (Vector2D edge : list) {
            stack.push(edge);
            while (!stack.isEmpty()) {
                Vector2D current = stack.pop();
                int row = current.getyPos();
                int column  = current.getxPos();
                if (isUniquePath) {
                    uniquePath.add(current);
                }
                if (bfsArray[row][column] == 1) {
                    correctVector = overgoingClass.moreWestVector(correctVector, current);
                    continue;
                }
                int childCount = 0;
                for (Vector2D neighbour : getSameNeighbours(current, Hexagon.PLACEABLE)) {
                    int nRow = neighbour.getyPos();
                    int nColumn = neighbour.getxPos();
                    if (bfsArray[nRow][nColumn] + 1 == bfsArray[row][column] && isValid(validityBoard, nRow, nColumn)) {
                        stack.push(neighbour);
                        childCount++;
                        validityBoard[nRow][nColumn] = true;
                    }
                }
                if (childCount > 1) {
                    isUniquePath = false;
                }
            }
        }
        if (isUniquePath) {
            correctVector = overgoingClass.westVector(uniquePath);
        }
        return correctVector;
    }
}
