package bomberman.Game;

import bomberman.Sprite.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import static bomberman.Game.Constants.*;


public class Bomberman extends Sprite{
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
    public Bomberman(int x,int y,int width,int height,int playerId, Level level,Image image){
        super(x,y,width,height,image);
        this.playerId = playerId;
        this.level = level;
        this.bombs = new ArrayList<>();
        this.bombs = new ArrayList<>();
        loadAnimations();
        initHitbox(x,y,25,44);
    }


    public void placeBomb(){
        int middlepos_x = this.x + (this.width/2);
        int middlepos_y = this.y + (this.height/2);
        Bomb bomb = this.level.placeBomb(middlepos_x, middlepos_y);
        if (bomb == null) {
            System.out.println("Bomb placement failed - spot already taken.");
        } else {
            bombs.add(bomb);
            System.out.println("Bomb placed at (" + bomb.getX() + ", " + bomb.getY() + ")");
        }

    }


    public void update(){
        updatePOS();
        updateAnimation();
        setAnimations();
        //System.out.println("Bomberman position: x=" + x + ", y=" + y);


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
        if (player_action == IDLE) {
            aniIndex = 0;
        } else {
            if (aniTick >= aniSpeed) {
                aniTick = 0;
                aniIndex++;
                if (aniIndex >= animations[player_action].length || aniIndex>=getSprite(player_action)) {
                    aniIndex = 0;
                }
            }
        }
    }
    public void render(Graphics g)
    {
        g.drawImage(animations[player_action][aniIndex], hitbox.x-xDrawOffset, hitbox.y-yDrawOffset, width, height, null);
        //drawHitbox(g);
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


}
