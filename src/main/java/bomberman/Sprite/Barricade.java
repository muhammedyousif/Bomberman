package bomberman.Sprite;
import java.awt.Image;
public class Barricade extends Sprite{
    private boolean ignoreCollisionWithPlayer = false;
    private int lifeTime;
    public Barricade(int x,int y,int width, int height, Image image){
        super(x-width/2,y-height/2,width,height,image);
    }
    public void setIgnoreCollisionWithPlayer(boolean ignore) {
        this.ignoreCollisionWithPlayer = ignore;
    }

    public boolean isIgnoreCollisionWithPlayer() {
        return ignoreCollisionWithPlayer;
    }
}
