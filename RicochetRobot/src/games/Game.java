package games;

import games.structures.Target;

import java.util.ArrayList;

public class Game {

    Grid grid;
    ArrayList<Target> targetDeck;
    Player player1, player2, player3, player4;
    Player current_player;
    Player[] player_list = { player1, player2, player3, player4 };
    ArrayList<Grid> grid_list;

    public Game(Grid grid, ArrayList<Target> targetDeck, Player current_player, ArrayList<Grid> grid_list) {
        this.grid = grid;
        this.targetDeck = targetDeck;
        this.current_player = current_player;
        this.grid_list = grid_list;

    }

    public boolean isOver() {
        if (targetDeck.isEmpty() || current_player.getScore() == 5) {
            return true;
        } else {
            return false;
        }
    }

    public Player getWinner() {
        Player winner = null;
        if (isOver() == true) {
            for (Player player : player_list) {
                if (player.getScore() == 5) {
                    winner = player;
                }
            }
        }
        return winner;
    }

    public Player getCurrentPlayer() {
        return this.current_player;
    }
}
