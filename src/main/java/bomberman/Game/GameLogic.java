package bomberman.Game;
import bomberman.Sprite.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class GameLogic {
    private Bomberman Local;
    private Level level;
    private ArrayList<Bomberman> players;
    private GameMode gamemode;
    public GameEngine gameEngine;
    public ArrayList<PowerUp> bombs;
    private boolean firstmove=false;
    public GameLogic(GameEngine gameEngine){
        try{
            this.level = new Level("Assets/level1.txt",gameEngine);
        }catch(Exception e){
            System.out.println(e);
        }
        this.gameEngine=gameEngine;
        this.players = new ArrayList<>();
        bombs=new ArrayList<>();
        //Image player=new ImageIcon("src/bomberman/Assets/bomb.png").getImage();
        //players.add(new PlayerMP(70,70,40,50,"Muhammed",level));
    }



    public boolean spritesCollides(Sprite sprite1, Sprite sprite2) {
        Rectangle rect = new Rectangle(sprite1.getX(), sprite1.getY(), sprite1.width, sprite1.height);
        Rectangle otherRect = new Rectangle(sprite2.getX(), sprite2.getY(), sprite2.width, sprite2.height);
        return rect.intersects(otherRect);
    }



    public void drawEverything(Graphics g){
        if (level!=null) {
            level.draw(g);
            for (Bomberman bomberman : players) {
                bomberman.render(g);
            }
            for (Monster monster : getLevel().getMonsters()) {
                monster.draw(g);
            }

            for (PowerUp bomb : bombs) {
                if (!bomb.isCollected()) {
                    bomb.render(g);
                }
            }
        }
    }


    public void updateMonsters() {
        for (Monster monster : level.getMonsters()) {
            monster.update();

        }
    }

    public ArrayList<Bomberman> getPlayers() {
        return players;
    }

    public Level getLevel() {
        return level;
    }

    public Bomberman getLocal() {
        if (gameEngine.multiplayer) {
            for (Bomberman man : players) {
                if (man.getUsername().equalsIgnoreCase(gameEngine.getUsername())) {
                    return man;
                }
            }
        }
        else {
            return players.get(0);
        }
        System.out.println("Local player not found, players list size: " + players.size());
        return null;
    }

    public void removePlayerMP(String username) {
        int index=0;
        for (Entity e: players){
            if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)){
                break;
            }
            index++;
        }
        players.remove(index);
    }
    public int getPlayerMPIndex(String username){
        int index=0;
        for (Bomberman player:players) {
            if (player instanceof PlayerMP && player.getUsername().equals(username))
                break;
            index++;
        }
        return index;
    }
    public void movePlayer(String username,int x,int y,boolean left,boolean right,boolean up,boolean down){
        int index=getPlayerMPIndex(username);
        players.get(index).hitbox.x=x;
        players.get(index).x=x;
        players.get(index).hitbox.y=y;
        players.get(index).y=y;
        //System.out.println(left);
        players.get(index).setDown(down);
        players.get(index).setUp(up);
        players.get(index).setLeft(left);
        players.get(index).setRight(right);
    }

    public void reset() {
        try{
            this.level = new Level("Assets/level1.txt",gameEngine);
        }catch(Exception e){
            System.out.println(e);
        }
        bombs=new ArrayList<>();
        if (!gameEngine.multiplayer){
            getPlayers().get(0).setLevel(level);
            getPlayers().get(0).reset();
        }
        else {
            for (Bomberman player:getPlayers()) {
                player.setLevel(level);
                player.reset();
            }
        }

    }
}

