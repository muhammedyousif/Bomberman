package bomberman.Sprite;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends Sprite{
    public boolean revealed;
    public int time;
    private int xDrawOffset=5;
    private int yDrawOffset=5;
    Image img = new ImageIcon("src/bomberman/Assets/bomb.png").getImage();

    public PowerUp(int x,int y,int width, int height, Image image){
        super(x,y,width,height,image);
        initHitbox(x,y,30,30);
    }
    public void render(Graphics g){
        g.drawImage(img, (hitbox.x+xDrawOffset)+width-6, (hitbox.y+yDrawOffset)+height-6,width,height,null);
        renderHitbox(g);
    }
    public void renderHitbox(Graphics g){
        g.drawRect(hitbox.x+width-6,hitbox.y+height-6, hitbox.width, hitbox.height);
    }

}
