package bomberman.Sprite;

import bomberman.Game.Bomberman;
import bomberman.Game.Level;

import javax.swing.*;
import java.awt.*;

public class BigBombItem extends PowerUp{
    private Image img = new ImageIcon(getClass().getResource("/Assets/bigBomb.png")).getImage();
    private int xDrawOffset=3;
    private int yDrawOffset=3;


    public BigBombItem(int x, int y, int width, int height, Image image, Level level) {
        super(x, y, width, height, image, level);
        initHitbox((x+xDrawOffset),(y+yDrawOffset),width+10,height+10);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(img, (hitbox.x+xDrawOffset)+xDrawOffset, (hitbox.y+yDrawOffset)+yDrawOffset,width,height,null);
        //drawHitbox(g);
    }

    @Override
    public void update() {
        for (Bomberman man : level.gameEngine.gameLogic.getPlayers()){
            if (collides_player(man)) {
                man.incBigBomb();
                collected=true;
            }
        }
    }
}
