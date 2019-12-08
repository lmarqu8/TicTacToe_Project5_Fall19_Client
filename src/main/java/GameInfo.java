import java.io.Serializable;
import java.util.ArrayList;

public class GameInfo implements Serializable {

    private int point;
    private int count;
    private int difficulty;
    private int playerMove;
    private int computerMove;
    private String name;
    private String[] gameState;
    private ArrayList<GameInfo> topPlayers;

    GameInfo() {
    }

    GameInfo(int point) {
        this.point = point;
    }

    GameInfo(int point, int count) {
        this.point = point;
        this.count = count;
        this.name = "Player " + count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return this.point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public ArrayList<GameInfo> getTopPlayers() {
        return topPlayers;
    }

    public void setTopPlayers(ArrayList<GameInfo> topPlayers) {
        this.topPlayers = topPlayers;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String[] getGameState() {
        return gameState;
    }

    public void setGameState(String[] gameState) {
        this.gameState = gameState;
    }

    public int getPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(int playerMove) {
        this.playerMove = playerMove;
    }

    public int getComputerMove() {
        return computerMove;
    }

    public void setComputerMove(int computerMove) {
        this.computerMove = computerMove;
    }
}
