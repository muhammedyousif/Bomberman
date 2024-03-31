package Entities;

public class Bomberman extends Entity {
    private int x;
    private int y;
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
}
