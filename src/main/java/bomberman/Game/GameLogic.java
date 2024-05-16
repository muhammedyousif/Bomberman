package bomberman.Game;
import bomberman.Sprite.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class GameLogic {
    private Bomberman Local;
    private Level level;
    private ArrayList<Level>levels;
    private ArrayList<Bomberman> players;
    private GameMode gamemode;
    public GameEngine gameEngine;
    public ArrayList<PowerUp> bombs;
    private boolean firstmove=false;
    public GameLogic(GameEngine gameEngine){
        levels=new ArrayList<>();
        loadLevels(gameEngine);
        level=levels.get(0);
        this.gameEngine=gameEngine;
        this.players = new ArrayList<>();
        bombs=new ArrayList<>();
        //Image player=new ImageIcon("src/bomberman/Assets/bomb.png").getImage();
        //players.add(new PlayerMP(70,70,40,50,"Muhammed",level));
    }

    private void loadLevels(GameEngine gameEngine) {
        InputStream inputStream = getClass().getResourceAsStream("/Assets/greenlevel");
        if (inputStream == null) {
            System.err.println("Resource directory not found: /Assets/greenlevel");
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList<String> fileNames = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                fileNames.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (String fileName : fileNames) {
            System.out.println(fileName);
            String fileStream = ("Assets/greenlevel/" + fileName);
            System.out.println(fileStream.toString());
            if (fileStream != null) {
                try {
                    Level tmp = new Level(fileStream, gameEngine);
                    levels.add(tmp);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                System.out.printf("lmao");
            }
        }
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
        loadLevels(gameEngine);
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

