package bomberman.Sprite;
import java.awt.Image;
public class Monster extends Sprite {
    private int speed;
    private boolean paused;
    private Headed headed;
    public Monster(int x,int y,int width, int height, Image image){
        super(x,y,width,height,image);
    }
}
