package bomberman.UI;

import bomberman.Game.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MenuButton {
    private int x,y;
    private GameState state;
    private String location;
    private BufferedImage image;
    private float scale;
    public Rectangle bounds;
    public MenuButton(int x, int y,float scale, GameState state,String location){
        this.x=x;
        this.y=y;
        this.location=location;
        this.scale=scale;
        loadImage();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(x,y,((int)scale* image.getWidth()),(int)scale* image.getHeight());
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
        g.drawImage(image,x,y,((int)scale* image.getWidth()),(int)scale* image.getHeight(),null);
    }

}
