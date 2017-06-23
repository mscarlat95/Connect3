package com.scarlat.marius.connect3.GameDesign;


public class Game {

    private Player player1, player2;
    private boolean activeGame;
    private int []gameState;

    private int [][]winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},    /* rows */
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},    /* columns */
            {0, 4, 8}, {2, 4, 6}                /* diagonals */
    };

    public boolean isActiveGame() {
        return activeGame;
    }

    public int[] getGameState() { return gameState; }

    public void setActiveGame(boolean activeGame) { this.activeGame = activeGame; }

    public void updateGameState(int index, int value) {
        gameState[index] = value;
    }

    public void initialize() {
        activeGame = true;

        player1 = new Player(0, "Yellow");
        player2 = new Player(1, "Red");

        // mark unset
        gameState = new int[9];
        for (int i = 0; i < gameState.length; ++i) {
            gameState[i] = 2;
        }
    }

    private boolean checkWinning() {
        for (int[] winningPos : winningPositions) {
            if (    gameState[winningPos[0]] == gameState[winningPos[1]] &&
                    gameState[winningPos[1]] == gameState[winningPos[2]] &&
                    gameState[winningPos[0]] != 2) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDraw() {
        for (int cell : gameState) {
            if (cell == 2) {
                return false;
            }
        }
        return true;
    }

    public void changePlayer() {
        if (player1.isActive()) {
            player1.setActive(false);
            player2.setActive(true);
        } else {
            player1.setActive(true);
            player2.setActive(false);
        }
    }

    public Player getActivePlayer() {
        if (player1.isActive()) {
            return player1;
        } else {
            return  player2;
        }
    }

    public String checkState() {
        String message = "";
        if (checkWinning()) {
            message = getActivePlayer().getName() + " has won!";
        } else if (checkDraw()) {
            message = "DRAW";
        }
        return  message;
    }

}
