package logic;

public class NextShapeInfo {
    private final int[][] shape;
    private final int position;

    public int[][] getShape() {
        return shape;
    }

    public int getPosition() {
        return position;
    }

    public NextShapeInfo(int[][] shape, int position) {
        this.shape = shape;
        this.position = position;
    }

}
