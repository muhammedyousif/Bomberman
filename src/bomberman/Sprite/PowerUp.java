package bomberman.Sprite;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends Sprite{
    public boolean revealed;
    public int time;
    Image img = new ImageIcon("src/bomberman/Assets/bomb.png").getImage();

    public PowerUp(int x,int y,int width, int height, Image image){
        super(x,y,width,height,image);
    }
    public void render(Graphics g){
        g.drawImage(img,x+width,y+height,width,height,null);
    }
}
