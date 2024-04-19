package bomberman.Sprite;
import bomberman.Game.Level;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.stream.FactoryConfigurationError;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static bomberman.Game.Constants.IDLE;
import static bomberman.Game.Constants.getSprite;
import static java.lang.Integer.valueOf;

public class Monster extends Sprite {
    private int speed;
    private int moveBuffer = 0;
    private int turn_delay = 0;
    private boolean paused;
    private Headed headed;
    private Random random;
    private Level level;
    private BufferedImage[][] animations;
    private int aniTick;
    private int aniSpeed=90; //minel kisebb a szam annal gyorsabb az animacio
    private int aniIndex;
    private static boolean alive=true;


    public Monster(int x,int y,int width, int height, Image image){
        super(x,y,width,height,image);
        this.speed = 1;
        this.random = new Random();
        this.headed = Headed.values()[random.nextInt(Headed.values().length)];
        loadAnimations();
    }

    public static void die() {
        alive=false;
    }


    private void removeCurrentHeaded(ArrayList<Integer> canMove){
        if(this.headed == Headed.UP && canMove.contains(1)){
            canMove.remove(valueOf(1));
        }
        else if(this.headed == Headed.LEFT && canMove.contains(2)){
            canMove.remove(valueOf(2));
        }
        else if(this.headed == Headed.DOWN && canMove.contains(3)){
            canMove.remove(valueOf(3));
        }
        else if(this.headed == Headed.RIGHT && canMove.contains(4)){
            canMove.remove(valueOf(4));
        }
    }
    private void chooseHeaded(ArrayList<Integer> canMove){
        if(canMove.isEmpty()){
            return;
        }
        int intheaded = canMove.get(random.nextInt(canMove.size()));
        if(intheaded == 1){
            this.headed = Headed.UP;
        }
        else if(intheaded == 2){
            this.headed = Headed.LEFT;
        }
        else if(intheaded == 3){
            this.headed = Headed.DOWN;
        }
        else if(intheaded == 4){
            this.headed = Headed.RIGHT;
        }
    }

    private void move() {
        //Move every five frames
        if(moveBuffer != 0){
            moveBuffer = moveBuffer-1;
            return;
        }
        //After turn disable random turns for 200 UPS (1sec)
        if(turn_delay > 0){
            turn_delay--;
        }
        int yspeed = 0;
        int xspeed = 0;
        //Which direction can the monster move
        ArrayList<Integer> canMove = new ArrayList<>();//UP - 1 ,LEFT - 2 ,DOWN - 3 ,RIGHT - 4
        if(canMoveTo(this.x, this.y-speed)){
            canMove.add(1);
        }
        if(canMoveTo(this.x-speed, this.y)){
            canMove.add(2);
        }
        if(canMoveTo(this.x, this.y+speed)){
            canMove.add(3);
        }if(canMoveTo(this.x+speed, this.y)){
            canMove.add(4);
        }
        //If random direction changes are enabled
        if(turn_delay == 0){
            if(canMove.size() == 2){
                int rng = random.nextInt(512);
                if(rng == 0){
                    removeCurrentHeaded(canMove);
                    chooseHeaded(canMove);
                    turn_delay = 200;
                }
            }
            else if(canMove.size() == 3) {
                int rng = random.nextInt(4);
                if(rng==0){
                    removeCurrentHeaded(canMove);
                    chooseHeaded(canMove);
                    turn_delay = 200;
                }
            }
            else if(canMove.size() == 4){
                int rng = random.nextInt(2);
                if(rng==0){
                    removeCurrentHeaded(canMove);
                    chooseHeaded(canMove);
                    turn_delay = 200;
                }
            }
        }






        //Move to direction
        switch (headed) {
            case UP:
                yspeed = -speed;
                break;
            case DOWN:
                yspeed = speed;
                break;
            case LEFT:
                xspeed = -speed;
                break;
            case RIGHT:
                xspeed = speed;
                break;
        }
        int temp_x = x + xspeed;
        int temp_y = y + yspeed;



        if(canMoveTo(temp_x,temp_y)){
            x = x + xspeed;
            y = y + yspeed;
        }
        else{
            chooseHeaded(canMove);
        }



        moveBuffer = 3;
    }
    private boolean collides(Sprite sprite) {
        Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle otherRect = new Rectangle(sprite.x, sprite.y, sprite.width, sprite.height);
        return rect.intersects(otherRect);
    }
    private boolean collidesOnPos(Sprite sprite,int x,int y) {
        Rectangle rect = new Rectangle(x, y, this.width, this.height);
        Rectangle otherRect = new Rectangle(sprite.x, sprite.y, sprite.width, sprite.height);
        return rect.intersects(otherRect);
    }
    private boolean checkCollisionWithEnvironment(){
        ArrayList<Sprite> grid = this.level.grid;
        for(Sprite sprite : grid) {
            if(sprite instanceof Wall){
                if (collides(sprite)) {
                    return true;
                }
            }
            else if(sprite instanceof Box) {
                if (collides(sprite)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean canMoveTo(int x, int y){
        ArrayList<Sprite> grid = this.level.grid;
        for(Sprite sprite : grid) {
            if(sprite instanceof Wall){
                if (collidesOnPos(sprite,x,y)) {
                    return false;
                }
            }
            else if(sprite instanceof Box) {
                if (collidesOnPos(sprite,x,y)) {
                    return false;
                }
            }
        }
        return true;

    }
    public void draw(Graphics g)
    {
        if (!alive)
            return;
        g.drawImage(animations[0][aniIndex], x, y, 60, 50, null);
    }
    public void setLevel(Level level) {
        this.level = level;
    }
    public void update(){
        if (!alive)
            return;
        move();
        updateAnimation();
    }
    private void loadAnimations(){
        InputStream is = getClass().getResourceAsStream("/bomberman/Assets/monstersprite.png");
        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[1][3];
            for(int i =0;i<animations.length;i++){
                for(int j =0;j<animations[i].length;j++) {
                    animations[i][j] = img.getSubimage(j * 20, i*18, 20, 18);
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
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >=3) {
                aniIndex = 0;
            }
        }

    }


}
