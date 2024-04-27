package bomberman.Sprite;
import bomberman.Game.Bomberman;
import bomberman.Game.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends Sprite{
    public boolean revealed;
    public int time;
    private int xDrawOffset=5;
    private int yDrawOffset=5;
    protected boolean collected=false;
    Level level;
    Image img = new ImageIcon(getClass().getResource("/Assets/bombitem.png")).getImage();

    public boolean isCollected() {
        return collected;
    }

    public PowerUp(int x, int y, int width, int height, Image image, Level level){
        super(x,y,width,height,image);
        initHitbox(x,y,48,50);
        this.level=level;
    }
    public void update(){
        for (Bomberman man : level.gameEngine.gameLogic.getPlayers()){
            if (collides_player(man)) {
                man.incBomb();
                collected=true;
            }
        }
    }
    public void render(Graphics g){
        g.drawImage(img, (hitbox.x+xDrawOffset)+width-45, (hitbox.y+yDrawOffset)+height-40,width,height,null);
        //renderHitbox(g);
    }
    public void renderHitbox(Graphics g){
        g.drawRect(hitbox.x+width-45,hitbox.y+height-40, hitbox.width, hitbox.height);
    }
    protected boolean collides_player(Bomberman sprite){
        Rectangle rect = new Rectangle(hitbox.x+width-45, hitbox.y+height-40, hitbox.width, hitbox.height);
        Rectangle otherRect = new Rectangle(sprite.getHitbox().x, sprite.getHitbox().y, sprite.getHitbox().width, sprite.getHitbox().height);
        return rect.intersects(otherRect);
    }

}
