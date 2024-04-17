package bomberman.Sprite;
import java.awt.Image;

public class Wall extends Sprite{
    Image image;
    public Wall(int x,int y,int width, int height,Image image){
        super(x,y,width,height,image);
        this.image=image;
    }
}
