package bomberman.Game;
import bomberman.Sprite.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameLogic {
    private Level level;
    private ArrayList<Bomberman> players;
    private GameMode gamemode;
    public GameLogic(){
        try{
            this.level = new Level("src/bomberman/Assets/level.txt");
        }catch(Exception e){
            System.out.println(e);
        }
        this.players = new ArrayList<>();
        players.add(new Bomberman(60,60,1,new ImageIcon("src/Assets/bombermanleft.jpg").getImage(), level));
    }

    public void pressed(String button){
        for(Bomberman bomberman : players){
            bomberman.pressed(button);
        }
    }

    public boolean spritesCollides(Sprite sprite1, Sprite sprite2) {
        Rectangle rect = new Rectangle(sprite1.x, sprite1.y, sprite1.width, sprite1.height);
        Rectangle otherRect = new Rectangle(sprite2.x, sprite2.y, sprite2.width, sprite2.height);
        return rect.intersects(otherRect);
    }



    public void drawEverything(Graphics grphcs){
        level.draw(grphcs);
        for(Bomberman bomberman : players){
            bomberman.draw(grphcs);
        }
    }

    public Level getLevel() {
        return level;
    }

    public ArrayList<Bomberman> getPlayers() {
        return players;
    }
}

