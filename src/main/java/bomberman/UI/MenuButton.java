package bomberman.UI;

import bomberman.Game.GameState;
import bomberman.Game.StateMethods;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static bomberman.Game.Constants.GAME_HEIGHT;
import static bomberman.Game.Constants.GAME_WIDTH;

public class MenuButton {
    private int x,y;
    private GameState state;
    private String location;
    private BufferedImage image;
    private float scale;
    public Rectangle bounds;
    public MenuButton(int y,float scale, GameState state,String location){
        this.location=location;
        this.y=y;
        this.scale=scale;
        loadImage();
        x = GAME_WIDTH / 2 - (int)(image.getWidth() * scale / 2);
        initBounds();
    }
    public MenuButton(int x,int y,float scale, GameState state,String location){
        this.location=location;
        this.x=x;
        this.y=y;
        this.scale=scale;
        loadImage();
        initBounds();
    }


    private void initBounds() {
        bounds = new Rectangle(x, y, (int)(image.getWidth() * scale), (int)(image.getHeight() * scale));
    }

    private void loadImage(){
        InputStream is = getClass().getClassLoader().getResourceAsStream(location);
        try {
            image= ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(){

    }
    public void render(Graphics g){
        g.drawImage(image, x, y, (int)(image.getWidth() * scale), (int)(image.getHeight() * scale), null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GameState getState() {
        return state;
    }

    public String getLocation() {
        return location;
    }

    public BufferedImage getImage() {
        return image;
    }

    public float getScale() {
        return scale;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
