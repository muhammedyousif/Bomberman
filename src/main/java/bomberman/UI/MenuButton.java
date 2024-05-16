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
    private boolean border=false;
    private boolean mouseEntered,mouseExited;
    private boolean arrow=false;

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
        if (image2!=null && !arrow){
            if (mouseEntered){
                torender=image2;
            }
            else {
                torender=image;
            }
        }
        if (arrow){
            if (mouseEntered){
                bigArrow();
            }
            else {
                smallArrow(0.2f);
            }
        }
    }
    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Stroke originalStroke = g2d.getStroke();
        g.drawImage(torender, x, y, (int) (torender.getWidth() * scale), (int) (torender.getHeight() * scale), null);


        if (border) {
            drawBorder(g2d, originalStroke);
        }
    }

    private void drawBorder(Graphics2D g2d, Stroke originalStroke) {
        g2d.setColor(Color.WHITE);


        float outlineWidth = 2f; // Adjust outline width as needed
        Stroke outlineStroke = new BasicStroke(outlineWidth);

        g2d.setStroke(outlineStroke);

        g2d.drawRect(x - (int) outlineWidth, y - (int) outlineWidth,
                (int) (torender.getWidth() * scale) + 2 * (int) outlineWidth,
                (int) (torender.getHeight() * scale) + 2 * (int) outlineWidth);

        g2d.setStroke(originalStroke);
    }
    public void bigArrow(){
        setScale(0.22f);
        setY(GAME_HEIGHT / 2 - (int)(getImage().getHeight() * getScale() / 2)+1);

    }
    public void smallArrow(float defaultscale){
        setScale(defaultscale);
        setY(GAME_HEIGHT / 2 - (int)(getImage().getHeight() * getScale() / 2)+1);

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

    public void setBorder(boolean border) {
        this.border = border;
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

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isArrow() {
        return arrow;
    }

    public void setArrow(boolean arrow) {
        this.arrow = arrow;
    }

    public void setMouseExited(boolean mouseExited) {
        this.mouseExited = mouseExited;
    }
}
