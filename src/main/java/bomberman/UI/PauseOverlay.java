package bomberman.UI;

import bomberman.Game.Bomberman;
import bomberman.Game.GameEngine;
import bomberman.Packets.Packet02Move;
import bomberman.Packets.Packet06Restart;
import bomberman.Sprite.Entity;
import bomberman.Sprite.PlayerMP;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static bomberman.Game.Constants.GAME_HEIGHT;
import static bomberman.Game.Constants.GAME_WIDTH;

public class PauseOverlay {
    BufferedImage overlay;
    private int bgX,bgY,bgW,bgH;
    public PauseOverlay(){
        LoadImg();
        bgW=overlay.getWidth()/2;
        bgH= overlay.getHeight()/2;
        bgX=GAME_WIDTH/2-bgW/2;
        bgY=60;
    }
    private void LoadImg(){
        InputStream isa = getClass().getClassLoader().getResourceAsStream("Assets/pause.png");
        try {
            overlay= ImageIO.read(isa);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(){

    }
    public void draw(Graphics g){
        g.drawImage(overlay,bgX,bgY,bgW,bgH,null);
    }
    public void keyPressed(KeyEvent e){

    }
    public void MousePressed(MouseEvent e){

    }

}
