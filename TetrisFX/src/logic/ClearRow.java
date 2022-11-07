package logic;

public class ClearRow {
    private final int linesRemoved;
    private final int [][] nextMatrix;

    public ClearRow(int linesRemoved, int[][] nextMatrix) {
        this.linesRemoved = linesRemoved;
        this.nextMatrix = nextMatrix;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public int[][] getNextMatrix(){
        return nextMatrix;
    }
}
