package bomberman.Game;

import bomberman.Sprite.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import static bomberman.Game.Constants.*;


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
    private int player_action= IDLE;
    private BufferedImage[][] animations;
    private int aniTick;
    private int aniSpeed=40; //minel kisebb a szam annal gyorsabb az animacio
    private int aniIndex;
    private boolean moving=false;

    //private Binds binds;
    public Bomberman(int x,int y,int playerId, Image image, Level level){
        this.x = x;
        this.y = y;
        this.playerId = playerId;
        this.level = level;
        this.bombs = new ArrayList<>();
        loadAnimations();
    }

    public void pressed(String button){
        if(Objects.equals(button, "W")){
            moveUp();
            moving=true;
        }
        if(Objects.equals(button, "A")){
            moveLeft();
            moving=true;
        }
        if(Objects.equals(button, "S")){
            moveDown();
            moving=true;
        }
        if(Objects.equals(button, "D")){
            moveRight();
            moving=true;
        }
        if(Objects.equals(button, "E")){
            placeBomb();
        }
    }
    public void released(String button) {
        switch (button) {
            case "W":
            case "S":
            case "A":
            case "D":
                moving = false;
                break;
        }
    }


    public void moveUp(){
        player_action=RUNNING_UP;

        this.y = this.y - this.speed;
        if(checkCollision()){
            this.y = this.y + this.speed;
        }
    }
    public void moveLeft(){
        player_action=RUNNING_LEFT;
        //this.image = new ImageIcon("src/bomberman/Assets/bombermanleft.png").getImage();
        this.x = this.x - this.speed;
        if(checkCollision()){
            this.x = this.x + this.speed;
        }
    }
    public void moveDown(){
        player_action=RUNNING_DOWN;
        this.y = this.y + this.speed;
        if(checkCollision()){
            this.y = this.y - this.speed;
        }
    }
    public void moveRight(){
        player_action=RUNNING_RIGHT;
        //this.image = new ImageIcon("src/bomberman/Assets/bombermanright.png").getImage();
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
        ArrayList<Sprite> grid = this.level.getGrid();
        for(Sprite sprite : grid) {
            if (collides(sprite)) {
                return true;
            }
        }
        return false;
    }
    public void update(){
        updateAnimation();
        checkState();
    }

    private void checkState() {
        if(!moving){
            player_action=IDLE;
        }
    }

    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/bomberman/Assets/pink.png");
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[7][6];
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

    private void updateAnimation() {
        aniTick++;
        if (aniTick>=aniSpeed){
            aniTick=0;
            aniIndex++;
            if (aniIndex>=getSprite(player_action)){
                aniIndex=0;
            }
        }

    }
    public void draw(Graphics g)
    {
        //g.drawImage(this.image, x, y, width, height, null);
        g.drawImage(animations[player_action][aniIndex], x, y, width, height, null);

        g.drawRect(this.x,this.y,width,height);
    }
}
