package bomberman.Sprite;
import java.awt.Image;
import bomberman.Game.*;
public class Bomb extends Sprite {
    private float timeLeft;
    private int strength;
    private Level level;
    public Bomb(int x,int y,int height,int width, Image image){
        super(x,y,width,height,image);

    }
}
