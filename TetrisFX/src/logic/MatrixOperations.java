package logic;

public class MatrixOperations 
{
    public static boolean intersects(int[][] matrix, int[][] brick, int x, int y)
    {
        int targetY = 0;
        for(int i = 0; i < brick.length; i++)
        {
            for(int j = 0; j < brick[i].length; j++)
            {
                targetY = y + j;
                if(outOfBounds(matrix, targetY)) return true;
                System.out.println("y = " + y);
                System.out.println("targetY = " + targetY);
                System.out.println("******************************");
            }
        }
        return false;
    }
    
    private static boolean outOfBounds(int[][] matrix, int targetY)
    {
        if(targetY < matrix.length) return false;
        return true;
    }
}
