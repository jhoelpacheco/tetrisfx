package logic.bricks;

import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

//class that generates a random brick
public class BrickGenerator {
    private final List<Brick> brickList;
    private final Deque<Brick> nextBrick = new ArrayDeque<>();
    
    public BrickGenerator(){
        brickList = new ArrayList<>();
        
        //brickList.add(new IBrick());
        //brickList.add(new JBrick());
        //brickList.add(new LBrick());
        brickList.add(new OBrick());
        //brickList.add(new SBrick());
        //brickList.add(new TBrick());
        //brickList.add(new ZBrick());
        
        nextBrick.add(brickList.get(ThreadLocalRandom.current().nextInt(brickList.size())));
    }
    
    public Brick getBrick(){
        return nextBrick.poll();
    }
}
