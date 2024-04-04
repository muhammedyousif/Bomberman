package bomberman.Sprite;
import java.awt.*;
import javax.swing.*;


public class Sprite {

    public int x;
    public int y;
    public int width;
    public int height;
    private Image image;

    public Sprite(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;

    }
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }


}
