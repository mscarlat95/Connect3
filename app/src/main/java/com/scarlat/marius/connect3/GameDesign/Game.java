package com.scarlat.marius.connect3.GameDesign;


import android.graphics.Color;

public class Game {

    private Player player1, player2;
    private boolean activeGame;
    private boolean firstContact;
    private int [] cell = { 2, 2, 2, 2, 2, 2, 2, 2, 2 };
    private int [][]winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},    /* rows */
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},    /* columns */
            {0, 4, 8}, {2, 4, 6}                /* diagonals */
    };

    public Game() {
        firstContact = true;
    }

    public boolean isActiveGame() {
        return activeGame;
    }

    public int[] getCell() { return cell; }

    public void setActiveGame(boolean activeGame) { this.activeGame = activeGame; }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void updateGameState(int index, int value) {
        cell[index] = value;
    }

    public void initialize() {
        setActiveGame(true);

        if (firstContact) {

            player1 = new Player(0, "Yellow", true, Color.rgb(255, 247, 117));
            player2 = new Player(1, "Red", false, Color.rgb(254, 99, 104));

            firstContact = false;
        } else {

            // mark all cells as being unset
            for (int i = 0; i < 9; ++i) {
                cell[i] = 2;
            }

            // last winner has priority
            Player lastWinner = getWinner();
            if (lastWinner != null) {
                if (lastWinner.equals(player1)) {
                    player1.setActive(true);
                } else {
                    player2.setActive(true);
                }
            }
        }
    }

    private boolean checkWinning() {
        for (int[] winningPos : winningPositions) {
            if (    cell[winningPos[0]] == cell[winningPos[1]] &&
                    cell[winningPos[1]] == cell[winningPos[2]] &&
                    cell[winningPos[0]] != 2) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDraw() {
        for (int cell : this.cell) {
            if (cell == 2) {
                return false;
            }
        }
        return true;
    }

    public void changePlayer() {
        boolean state = player1.isActive();

        player1.setActive(!state);
        player2.setActive(state);
    }

    public Player getActivePlayer() {
        if (player1.isActive()) {
            return player1;
        } else {
            return  player2;
        }
    }

    public String getGameState() {
        String message = "";
        if (checkWinning()) {
            message = getActivePlayer().getName() + " has won!";
        } else if (checkDraw()) {
            message = "DRAW";
        }
        return  message;
    }

    public Player getWinner() {

        if (checkWinning()) {
            return getActivePlayer();
        }

        return null;
    }
}
