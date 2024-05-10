package bomberman.UI;

import bomberman.Game.Constants;
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
    private String location,location2;
    private BufferedImage image;
    private BufferedImage image2;
    private BufferedImage torender;
    private float scale;
    public Rectangle bounds;
    private boolean mouseEntered,mouseExited;

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
    public MenuButton(int x,int y,float scale, GameState state,String location,String location2){
        this.location=location;
        this.location2=location2;
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
            torender=image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (location2!=null){
            is = getClass().getClassLoader().getResourceAsStream(location2);
            try {
                image2= ImageIO.read(is);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update(){
        if (image2!=null){
            if (mouseEntered){
                torender=image2;
            }
            else {
                torender=image;
            }
        }
    }
    public void render(Graphics g){
        g.drawImage(torender, x, y, (int)(torender.getWidth() * scale), (int)(torender.getHeight() * scale), null);
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

    public boolean isMouseEntered() {
        return mouseEntered;
    }

    public void setMouseEntered(boolean mouseEntered) {
        this.mouseEntered = mouseEntered;
    }

    public boolean isMouseExited() {
        return mouseExited;
    }

    public void setMouseExited(boolean mouseExited) {
        this.mouseExited = mouseExited;
    }
}
