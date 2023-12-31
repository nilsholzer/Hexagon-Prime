package edu.kit.kastel.model.logic;

import edu.kit.kastel.model.entity.Hexagon;
import edu.kit.kastel.model.entity.Vector2D;
import edu.kit.kastel.model.exceptions.PlaceException;

import java.util.ArrayList;
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
    private final Hexagon[][] winnersBoard;
    private final Hexagon[][] gameBoard;
    private int placeCount;
    /**
     * Constructs a new game board of a game of hex.
     * @param size the size of the game board
     */
    public GameBoard(final int size) {
        placeCount = 0;
        this.size = size;
        gameBoard = new Hexagon[size][size];
        winnersBoard = new Hexagon[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                gameBoard[row][column] = Hexagon.PLACEABLE;
                winnersBoard[row][column] = Hexagon.PLACEABLE;
            }
        }
        graphTraverser = new GraphTraverser(this);
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
     * Prints the game board and all its hexagons.
     * @return A visualization of the game board and its hexagons
     */
    public String printWinner() {
        StringBuilder print = new StringBuilder();
        for (int row = 0; row < size; row++) {
            print.append(WHITESPACE.repeat(row));
            for (int column = 0; column < size; column++) {
                print.append(winnersBoard[row][column].getSymbol());
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
    public void place(final Vector2D coordinates, final Hexagon token) {
        int xPos = coordinates.getxPos();
        int yPos = coordinates.getyPos();
        if (gameBoard[yPos][xPos] != Hexagon.PLACEABLE) {
            throw new PlaceException(xPos, yPos);
        } else {
            gameBoard[yPos][xPos] = token;
            winnersBoard[yPos][xPos] = token;
        }
        placeCount++;
    }

    /**
     * Checks if the player with the given token is a winner.
     * If the placeCount is lower than double the size - 1, it is not possible that there is a winner.
     * @param token the token of the player, that is checked
     * @return      A boolean if the player is a winner
     */
    public boolean isWinner(final Hexagon token) {
        if (this.placeCount < 2 * size - 1) {
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
    public Vector2D winInNextMove(final Hexagon token) {
        if (this.placeCount < ((size - 1) * 2) - 1) {
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
     *         or {@code null} if there is no free hexagon on the gameboard, but in that case there is already a winner.
     */
    public Vector2D getBogoAIBasic() {
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
     * Gets the coordinates of the first vacancy along the columns of hexagons.
     * @return the coordinates of this hexagon
     *         or {@code null} if there is no free hexagon on the gameboard, but in that case there is already a winner.
     */
    public Vector2D getHeroAIBasic() {
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
     * @return the most western hexagon of the shortest path to the eastern border
     */
    public Vector2D getHeroAIMove(final Vector2D root) {
        return graphTraverser.getOptimalHexagon(root);
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
     * Gets all the neighbours of a hexagon, with the given token.
     * @param coordinates the coordinates of the hexagon
     * @param token the token, which the neighbours need to be listed
     * @return all the neighbours of a hexagon, with the given token
     */
    public List<Vector2D> getSameNeighbours(final Vector2D coordinates, final Hexagon token) {
        List<Vector2D> neighbours = new ArrayList<>();
        for (Vector2D neighbour : getNeighbours(coordinates)) {
            if (gameBoard[neighbour.getyPos()][neighbour.getxPos()] == token) {
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    /**
     * Creates the path on the gameboard, that was used so the player could win.
     * @param traversal A list containing all the nodes that were visited on the path
     */
    private void winnersPath(final List<Vector2D> traversal) {
        for (Vector2D node : traversal) {
            winnersBoard[node.getyPos()][node.getxPos()] = Hexagon.WINNER;
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
}
