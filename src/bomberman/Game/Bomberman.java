package bomberman.Game;

import bomberman.Sprite.*;
import java.awt.*;

public class Bomberman{
    private String playername;
    private int playerId;
    private int speed;
    private int hp;
    private boolean paused;
    private boolean alive=true;
    private boolean detonator;
    private float ghost;
    private float invincible;
    private boolean barricade;
    private float time;
    private Level level;
    private Binds binds;
    private ArrayList<Bomb> bombs;

    public Bomberman(int x,int y,int playerId){
        this.x = x;
        this.y = y;
        this.playerId = playerId;
    }

    public void update(){

    }
    public void render(Graphics g){
        g.drawImage(image, x, y, width, height, null);
    }
}
