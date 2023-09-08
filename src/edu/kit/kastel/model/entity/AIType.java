package edu.kit.kastel.model.entity;

import edu.kit.kastel.model.exceptions.PlaceException;
import edu.kit.kastel.model.logic.AIGame;
import edu.kit.kastel.model.logic.Game;
import edu.kit.kastel.model.logic.GameBoard;
import edu.kit.kastel.ui.ResultType;

/**
 * An enumeration of the different AI players.
 * @author uhquw
 * @version 1.0.5
 */
public enum AIType {
    /**
     * Represents BogoAI and executes its move.
     */
    BogoAI("BogoAI") {
        @Override
        public String turn(AIGame aiGame) {
            Vector2D playersLastMove = aiGame.getMove(1);
            int xPos = playersLastMove.getxPos();
            int yPos = playersLastMove.getyPos();
            if (aiGame.getTurnsSize() == 1 && (xPos + yPos) % 2 == 0) {
                return aiGame.swap();
            }
            GameBoard gameBoard = aiGame.getGameBoard();
            Player aiPlayer = aiGame.getCurrentPlayer();
            Hexagon aiToken = aiPlayer.getToken();
            StringBuilder result = new StringBuilder();
            int middle = (gameBoard.getSize() - 1) / 2;
            int distXMiddle = middle - xPos;
            int distYMiddle = middle - yPos;
            int newXCoor = middle + distXMiddle;
            int newYCoor = middle + distYMiddle;
            try {
                gameBoard.place(new Vector2D(newXCoor, newYCoor), aiToken);
            } catch (PlaceException exception) {
                Vector2D nextFreeHexagon = gameBoard.getNextFreeHexagon();
                newXCoor = nextFreeHexagon.getxPos();
                newYCoor = nextFreeHexagon.getyPos();
                gameBoard.place(nextFreeHexagon, aiToken);
            }
            aiGame.addTurn(new Vector2D(newXCoor, newYCoor), aiPlayer);
            result.append(AI_PLACE_FORMAT.formatted(getName(), newXCoor, newYCoor)).append(aiGame.update());
            return result.toString();
        }
    },
    /**
     * Represents HeroAI and executes its move.
     */
    HeroAI("HeroAI") {
        @Override
        public String turn(AIGame aiGame) {
            GameBoard gameBoard = aiGame.getGameBoard();
            Player aiPlayer = aiGame.getCurrentPlayer();
            Vector2D setVector;
            StringBuilder result = new StringBuilder();
            if (aiGame.getTurnsSize() == 1) {
                setVector = gameBoard.getNextFreeHexagon();
                gameBoard.place(setVector, aiPlayer.getToken());
            } else {
                setVector = setVector(aiGame.getMove(2), gameBoard, 2, aiGame);
            }
            aiGame.addTurn(setVector, aiPlayer);
            result.append(AI_PLACE_FORMAT.formatted(getName(), setVector.getxPos(), setVector.getyPos())).append(aiGame.update());
            return result.toString();
        }

        private Vector2D setVector(final Vector2D lastMove, GameBoard gameBoard, int turnCount, Game aiGame) {
            Vector2D setVector = gameBoard.shortestPathToEast(lastMove);
            if (setVector != null) {
                return setVector;
            }
            if (turnCount + 2 < aiGame.getTurnsSize()) {
                return setVector(aiGame.getMove(turnCount + 2), gameBoard, turnCount + 2, aiGame);
            } else {
                return gameBoard.getNextFreeHexagon();
            }
        }
    };
    /**
     * Format of the confirmation that an AI placed a hexagon on the game board.
     */
    public static final String AI_PLACE_FORMAT = "%s places at %d %d" + ResultType.NEW_LINE_SYMBOL;
    private final String name;
    AIType(final String name) {
        this.name = name;
    }

    /**
     * Gets the name of the AIType.
     * @return the name of the AIType
     */
    public String getName() {
        return name;
    }

    /**
     * Executes the move of the AI player.
     * @param aiGame the game, where the move is executed
     * @return A confirmation of the successful move
     */
    public abstract String turn(AIGame aiGame);
}
