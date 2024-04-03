package Sprite;
import java.awt.Image;
public class Bomb extends Sprite {
    private float timeLeft;
    private int strength;
    private Level level;
    public Bomb(int x,int y,int height,int width){
        super(x,y,height,width);

    }
}
