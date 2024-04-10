package bomberman.Sprite;
import bomberman.Game.Level;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
public class Monster extends Sprite {
    private int speed;
    private boolean paused;
    private Headed headed;
    private Random random;
    private Level level;
    private BufferedImage[][] animations;

    public Monster(int x,int y,int width, int height, Image image){
        super(x,y,width,height,image);
        this.speed = 1;
        this.random = new Random();
        this.headed = Headed.values()[random.nextInt(Headed.values().length)];
        loadAnimations();
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
        ArrayList<Sprite> grid = this.level.grid;
        for(Sprite sprite : grid) {
            if (collides(sprite)) {
                return true;
            }
        }
        return false;
    }
    public void draw(Graphics g)
    {
        g.drawImage(animations[0][0], x, y, width, height, null);
    }
    public void setLevel(Level level) {
        this.level = level;
    }
    public void update(){

    }
    private void loadAnimations(){
        InputStream is = getClass().getResourceAsStream("/bomberman/Assets/monstersprite.png");
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[1][3];
            for(int i =0;i<animations.length;i++){
                for(int j =0;j<animations[i].length;j++) {
                    animations[i][j] = img.getSubimage(j * 18, i*27, 18, 27);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }

}
