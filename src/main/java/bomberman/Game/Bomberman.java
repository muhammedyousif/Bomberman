package bomberman.Game;

import bomberman.Packets.Packet04Bomb;
import bomberman.Packets.Packet05PlayerStatus;
import bomberman.Sprite.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import static bomberman.Game.Constants.*;


public class Bomberman extends Entity{
    private String playername;
    private int speed = 1;
    private int hp;
    private boolean paused;
    private boolean alive=true;
    private boolean detonator;
    private float ghost;
    private float invincible;
    private boolean barricade;
    private float time;
    //private int x=60;
    //private int y=60;
    private int playerId;
    private Level level;
    private ArrayList<Bomb> bombs;
    private ArrayList<BigBomb> bigBombs;
    private Image image = new ImageIcon("src/bomberman/Assets/bombermanright.png").getImage();
    public final int width = 40;
    public final int height = 57;
    private int player_action= IDLE;
    private BufferedImage[][] animations;
    private int aniTick;
    private int aniSpeed=25; //minel kisebb a szam annal gyorsabb az animacio
    private int aniIndex;
    private boolean moving=false;
    private boolean up,down,left,right;
    private final int xDrawOffset=5;
    private final int yDrawOffset=6;
    private boolean died=false;
    private int deathFrameDelay = 0;
    private int defaultBombCount=6;
    private int bigBombCount=0;
    private int bombCounter=defaultBombCount;
    public boolean firstbomb=true;
    private String username;

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Bomberman(int x, int y, int width, int height, String username, Level level){
        super(x,y,width,height);
        this.username = username;
        this.level = level;
        this.bombs = new ArrayList<>();
        this.bigBombs=new ArrayList<>();
        loadAnimations();
        initHitbox(x,y,25,44);
    }


    public void placeBomb(){
        firstbomb=false;
        if (alive && bombCounter>0) {
            int middlepos_x = this.x + (this.width / 2);
            int middlepos_y = this.y + (this.height / 2);
            Bomb bomb = this.level.placeBomb(middlepos_x, middlepos_y);

            if (bomb == null) {
                System.out.println("Bomb placement failed - spot already taken.");
            } else {
                bombs.add(bomb);
                bombCounter--;
                if (GameEngine.gameEngine.multiplayer){
                    Packet04Bomb packet=new Packet04Bomb(username,middlepos_x,middlepos_y,BOMB);
                    GameEngine.gameEngine.getSocketClient().sendData(packet.getData());
                }
                System.out.println("Bomb placed at (" + bomb.getX() + ", " + bomb.getY() + ")");
            }
        }
    }


    public void update(){
        if (!died) {//death animation complete
            if (alive) {
                updatePOS();
            }
            updateAnimation();
            setAnimations();
            checkDeath();
        }
        //System.out.println("Bomberman position: x=" + x + ", y=" + y);
    }


    private void checkDeath() {
        if (!alive){
            player_action=DEAD;
            if (GameEngine.gameEngine.multiplayer) {
                Packet05PlayerStatus packet = new Packet05PlayerStatus(username, alive);
                GameEngine.gameEngine.getSocketClient().sendData(packet.getData());
            }
        }
    }

    private void loadAnimations() {
        InputStream isa = getClass().getClassLoader().getResourceAsStream("Assets/pink.png");
        try {
            BufferedImage img = ImageIO.read(isa);
            animations = new BufferedImage[7][6];
            for(int i =0;i<animations.length;i++){
                for(int j =0;j<animations[i].length;j++) {
                    animations[i][j] = img.getSubimage(j * 20, i*27, 20, 27);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (isa != null) {
                try {
                    isa.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void updateAnimation() {
        aniTick++;
        if (player_action==DEAD) {
            aniSpeed = 30;
            if (aniIndex < 5) {

                if (aniTick >= aniSpeed) {
                    aniTick = 0;
                    aniIndex++;
                }
            } else if (aniIndex == 5) {
                if (deathFrameDelay < 300) {
                    deathFrameDelay++;
                    aniIndex = 5;
                } else {

                    died = true;
                    deathFrameDelay = 0;
                }

            }
        }
        if (player_action == IDLE) {
            aniIndex = 0;
        } else {
            if (player_action!=DEAD) {
                if (aniTick >= aniSpeed) {
                    aniTick = 0;
                    aniIndex++;
                    if (aniIndex >= animations[player_action].length || aniIndex >= getSprite(player_action)) {
                        aniIndex = 0;
                    }
                }
            }
        }
    }
    public void render(Graphics g)
    {   if (!died) {
        g.drawImage(animations[player_action][aniIndex], hitbox.x - xDrawOffset, hitbox.y - yDrawOffset, width, height, null);
        //drawHitbox(g);
        }
    }

    public int getBombCounter() {
        return bombCounter;
    }

    public int getDefaultBombCount() {
        return defaultBombCount;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isAlive() {
        return alive;
    }
    public int getBigBombCount() {
        return bigBombCount;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    private void updatePOS() {
        moving = false;

        int xspeed = 0;
        int yspeed = 0;

        if (left) {
            xspeed -= speed;
        }
        if (right) {
            xspeed += speed;
        }


        if (up) {
            yspeed -= speed;
        }
        if (down) {
            yspeed += speed;
        }


        if (canMoveHere(hitbox.x+ xspeed, hitbox.y, hitbox.width, hitbox.height)) {
            x+=xspeed;
            hitbox.x+=xspeed;

        }

        if (canMoveHere(hitbox.x, hitbox.y + yspeed, hitbox.width, hitbox.height)) {
            y+=yspeed;
            hitbox.y+=yspeed;
        }

        moving = true;
    }
    private boolean canMoveHere(int x, int y, int width, int height) {
        Rectangle proposedRect = new Rectangle(x, y, width, height);
        for (Sprite sprite : level.grid) {
            Rectangle spriteRect = new Rectangle(sprite.getX(), sprite.getY(), sprite.width, sprite.height);
            if (sprite instanceof Box){
                spriteRect=new Rectangle(sprite.getHitbox().x,sprite.getHitbox().y,sprite.getHitbox().width,sprite.getHitbox().height);
            } else if (sprite instanceof Barricade) {
                if (((Barricade) sprite).isIgnoreCollisionWithPlayer()){
                    if (!sprite.collides_with_sprite(hitbox.x,hitbox.y,hitbox.width,hitbox.height,sprite)){
                        ((Barricade) sprite).setIgnoreCollisionWithPlayer(false);
                    }
                    return true;
                }
            }
            if (proposedRect.intersects(spriteRect)) {
                return false;
            }
        }
        return true;
    }
    public void setAnimations(){
        if (up){
            player_action=RUNNING_UP;
        } else if (down) {
            player_action=RUNNING_DOWN;
        }
        if (right){
            player_action=RUNNING_RIGHT;
        } else if (left) {
            player_action=RUNNING_LEFT;
        }
        if (!up&&!down&&!left&&!right)
            player_action=IDLE;
    }
    public boolean collides_with_sprite(int x1, int y1, int w1, int h1, Entity sprite) {
        Rectangle rect = new Rectangle(x1, y1, w1, h1);
        Rectangle otherRect = new Rectangle(sprite.getX(), sprite.getY(), sprite.width, sprite.height);
        return rect.intersects(otherRect);
    }
    public void incBomb(){
        bombCounter+=1;
    }

    public String getUsername() {
        return username;
    }

    public void reset() {
        alive=true;
        player_action=IDLE;
        died=false;
        x=75;
        y=75;
        hitbox.x=x;
        hitbox.y=y;
        bombCounter=defaultBombCount;
        bigBombCount=0;
    }


    public void placeBigBomb() {
        firstbomb=false;
        if (alive && bigBombCount>0) {
            int middlepos_x = this.x + (this.width / 2);
            int middlepos_y = this.y + (this.height / 2);
            BigBomb bomb = this.level.placeBigBomb(middlepos_x, middlepos_y);
            if (bomb == null) {
                System.out.println("Bomb placement failed - spot already taken.");
            } else {
                bigBombs.add(bomb);
                bigBombCount--;
                if (GameEngine.gameEngine.multiplayer){
                    Packet04Bomb packet=new Packet04Bomb(username,middlepos_x,middlepos_y,BIGBOMB);
                    GameEngine.gameEngine.getSocketClient().sendData(packet.getData());
                }
                System.out.println("Bomb placed at (" + bomb.getX() + ", " + bomb.getY() + ")");
            }
        }

    }
    public void placeBarricade(){
        if (alive) {
            int middlepos_x = this.x + (this.width / 2);
            int middlepos_y = this.y + (this.height / 2);
            Barricade box = this.level.placeBarricade(middlepos_x, middlepos_y);
            if (box == null) {
                System.out.println("Barricade placement failed - spot already taken.");
            } else {
                box.setIgnoreCollisionWithPlayer(true);
                if (GameEngine.gameEngine.multiplayer){
                    Packet04Bomb packet=new Packet04Bomb(username,middlepos_x,middlepos_y,BARRICADE);
                    GameEngine.gameEngine.getSocketClient().sendData(packet.getData());
                }
                System.out.println("Barricade placed at (" + box.getX() + ", " + box.getY() + ")");
            }
        }

    }

    public void incBigBomb() {
        bigBombCount++;
    }
}
