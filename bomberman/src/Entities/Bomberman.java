package Entities;

import java.awt.*;

public class Bomberman extends Entity {
    private String playername;
    private int playerId;
    private float speed;
    private int hp;
    private Boolean paused;
    private Boolean alive=true;
    private Boolean detonator;
    private  Float ghost;
    private Float invincible;
    private Boolean barricade;
    private Float time;

    public Bomberman(int x,int y){
        super(x,y);
    }

    public void update(){

    }
    public void render(Graphics g){

    }
}
