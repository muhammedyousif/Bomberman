package bomberman.Sprite;
import java.awt.*;
import javax.swing.*;


public class Sprite {

    private int x;
    private int y;
    private int width;
    private int height;
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
