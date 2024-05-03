package bomberman.Sprite;
import bomberman.Game.Level;

import javax.swing.*;
import java.awt.Image;
public class Barricade extends Sprite{
    private boolean ignoreCollisionWithPlayer = true;
    private Level level;
    private int lifeTime;
    public Barricade(int x, int y, int width, int height, Image image, Level level){
        super(x-width/2,y-height/2,width,height,image);
        this.level=level;
    }
    public void setIgnoreCollisionWithPlayer(boolean ignore) {
        this.ignoreCollisionWithPlayer = ignore;
    }

    public boolean isIgnoreCollisionWithPlayer() {
        return ignoreCollisionWithPlayer;
    }
    public void blowUp(){
        level.grid.remove(this);
    }

}
