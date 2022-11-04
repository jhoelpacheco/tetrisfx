package logic;

public class ViewData 
{
    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    
    public ViewData(int[][] brickData, int xPosition, int yPosition)
    {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    
    public int[][] getBrickData() { return brickData; }
    public int getXPosition() { return xPosition; } 
    public int getYPosition() { return yPosition; }
}
