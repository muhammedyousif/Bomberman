package bomberman.Sprite;
import bomberman.Game.Level;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
public class Monster extends Sprite {
    private int speed;
    private boolean paused;
    private Headed headed;
    private Random random;
    private Level level;
    private Image image = new ImageIcon("src/bomberman/Assets/monster.png").getImage();
    public Monster(int x,int y,int width, int height, Image image){
        super(x,y,width,height,image);
        this.speed = 1;
        this.random = new Random();
        this.headed = Headed.values()[random.nextInt(Headed.values().length)];
    }

    public void move() {
        switch (headed) {
            case UP:
                this.setY(this.getY() - speed);
                break;
            case DOWN:
                this.setY(this.getY() + speed);
                break;
            case LEFT:
                this.setX(this.getX() - speed);
                break;
            case RIGHT:
                this.setX(this.getX() + speed);
                break;
        }


    }
    public boolean collides(Sprite sprite) {
        Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle otherRect = new Rectangle(sprite.x, sprite.y, sprite.width, sprite.height);
        return rect.intersects(otherRect);
    }
    public boolean checkCollision(){
        ArrayList<Sprite> grid = this.level.getGrid();
        for(Sprite sprite : grid) {
            if (collides(sprite)) {
                return true;
            }
        }
        return false;
    }
    public void draw(Graphics g)
    {
        g.drawImage(this.image, x, y, width, height, null);
        g.drawRect(this.x,this.y,width,height);
    }
    public void setLevel(Level level) {
        this.level = level;
    }
}
