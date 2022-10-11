package tetrisfx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Piece 
{
    Rectangle a;
    Rectangle b;
    Rectangle c;
    Rectangle d;
    Color color;
    private String name;
    public int form = 1;
    
    public Piece(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name)
    {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.name = name;
    }
    public void setColor()
    {
        switch(name)
        {
            case "j":
                color = Color.BLUE;
                break;
            case "l":
                color = Color.ORANGE;
                break;
            case "o":
                color = Color.YELLOW;
                break;
            case "i":
                color = Color.CYAN;
                break;
            case "s":
                color = Color.GREEN;
                break;
            case "z":
                color = Color.RED;
                break;
        }
        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public void changeForm()
    {
        if(form != 4)
        {
            form++;
        }
        else
        {
            form = 1;
        }
    }
}
