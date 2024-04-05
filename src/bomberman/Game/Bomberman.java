package bomberman.Game;

import bomberman.Sprite.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;


public class Bomberman extends JPanel{
    private String playername;
    private int speed = 5;
    private int hp;
    private boolean paused;
    private boolean alive=true;
    private boolean detonator;
    private float ghost;
    private float invincible;
    private boolean barricade;
    private float time;
    public int x;
    public int y;
    private int playerId;
    private Level level;
    private ArrayList<Bomb> bombs;
    private Image image = new ImageIcon("src/bomberman/Assets/bombermanright.png").getImage();
    public final int width = 40;
    public final int height = 50;
    //private Binds binds;
    public Bomberman(int x,int y,int playerId, Image image, Level level){
        this.x = x;
        this.y = y;
        this.playerId = playerId;
        this.level = level;
        this.bombs = new ArrayList<>();
    }

    public void pressed(String button){
        if(Objects.equals(button, "W")){
            moveUp();
        }
        if(Objects.equals(button, "A")){
            moveLeft();
        }
        if(Objects.equals(button, "S")){
            moveDown();
        }
        if(Objects.equals(button, "D")){
            moveRight();
        }
        if(Objects.equals(button, "E")){
            placeBomb();
        }
    }

    public void moveUp(){
        this.y = this.y - this.speed;
        if(checkCollision()){
            this.y = this.y + this.speed;
        }
    }
    public void moveLeft(){
        this.image = new ImageIcon("src/bomberman/Assets/bombermanleft.png").getImage();
        this.x = this.x - this.speed;
        if(checkCollision()){
            this.x = this.x + this.speed;
        }
    }
    public void moveDown(){
        this.y = this.y + this.speed;
        if(checkCollision()){
            this.y = this.y - this.speed;
        }
    }
    public void moveRight(){
        this.image = new ImageIcon("src/bomberman/Assets/bombermanright.png").getImage();
        this.x = this.x + this.speed;
        if(checkCollision()){
            this.x = this.x - this.speed;
        }
    }
    public void placeBomb(){
        int middlepos_x = this.x + (this.width/2);
        int middlepos_y = this.y + (this.height/2);
        bombs.add(this.level.placeBomb(middlepos_x,middlepos_y));
    }


    public boolean collides(Sprite sprite) {
        Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle otherRect = new Rectangle(sprite.x, sprite.y, sprite.width, sprite.height);
        return rect.intersects(otherRect);
    }
    public boolean checkCollision(){
        ArrayList<Sprite> grid = level.grid;
        for(Sprite sprite : grid) {
            if (collides(sprite)) {
                return true;
            }
        }
        return false;
    }





    public void update(){

    }

    public void draw(Graphics g)
    {
        g.drawImage(this.image, x, y, width, height, null);
        g.drawRect(this.x,this.y,width,height);
    }
}
