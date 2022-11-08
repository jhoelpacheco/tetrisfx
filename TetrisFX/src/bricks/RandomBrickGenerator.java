package bricks;

import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBrickGenerator 
{
    private final List<Brick> brickList;
    private final Deque<Brick> nextBricks = new ArrayDeque<>();
    
    public RandomBrickGenerator()
    {
        brickList = new ArrayList<>();
        brickList.add(new IBrick());
        brickList.add(new SBrick());
        brickList.add(new ZBrick());
        brickList.add(new LBrick());
        brickList.add(new JBrick());
        brickList.add(new OBrick());
        brickList.add(new TBrick());
        
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));

    }
    public Brick nextBrick {
        return nextBricks.peek()

}
    
    public Brick getBrick() {
        if(nextBricks.size() <= 1){
            nextBricks.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
        }
        return nextBricks.poll();
    }
}