package bomberman.Sprite;
import java.awt.Image;
public class PowerUp extends Sprite{
    public boolean revealed;
    public int time;
    public PowerUp(int x,int y,int width, int height, Image image){
        super(x,y,width,height,image);
    }
}
