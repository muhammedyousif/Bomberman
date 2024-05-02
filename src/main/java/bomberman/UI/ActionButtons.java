package bomberman.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static bomberman.Game.Constants.GAME_WIDTH;

public class ActionButtons extends PauseButton{
    private BufferedImage icon;
    private float scale;
    private boolean mouseOver, mousePressed;
    public ActionButtons(int x, int y, int width, int height,String location,float scale) {
        super(x, y, width, height);
        this.scale=scale;
        loadimg(location);
        setBounds(bounds);
    }
    private void loadimg(String location){
        InputStream isa = getClass().getClassLoader().getResourceAsStream(location);
        try {
            icon= ImageIO.read(isa);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void setBounds(Rectangle bounds) {
        bounds.x=GAME_WIDTH/2- (int)((icon.getWidth())*scale/2);
        bounds.y=y;
        bounds.width=(int)(icon.getWidth()*scale);
        bounds.height=(int)(icon.getHeight()*scale);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void update(){

    }
    public void draw(Graphics g){
        g.drawImage(icon,GAME_WIDTH/2- (int)((icon.getWidth())*scale/2),y,(int)(icon.getWidth()*scale), (int)(icon.getHeight()*scale), null);
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
