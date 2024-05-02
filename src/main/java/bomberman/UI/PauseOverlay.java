package bomberman.UI;

import bomberman.Game.Bomberman;
import bomberman.Game.GameEngine;
import bomberman.Game.Keyboard;
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
    private boolean pause;
    GameEngine gameEngine;
    private ActionButtons resume,menu,restart,quit;
    private static final int actionW=80;
    private static final int actionH=20;

    public PauseOverlay(GameEngine gameEngine){
        pause=true;
        this.gameEngine=gameEngine;
        LoadImg();
        bgW=overlay.getWidth()/2;
        bgH= overlay.getHeight()/2;
        bgX=GAME_WIDTH/2-bgW/2;
        bgY=60;
        createActionButtons();
    }

    private void createActionButtons() {
        int space = 80;
        int resumeY=270;
        int restartY=resumeY+space;
        int menuY=restartY+space;
        int quitY=menuY;
        resume=new ActionButtons(0,resumeY,0,0,"Assets/resume.png",0.3f);
        restart=new ActionButtons(0,restartY,0,0,"Assets/restart.png",0.29f);
        //menu=new ActionButtons(x,menuY,actionW,actionH);
        quit=new ActionButtons(0,quitY,0,0,"Assets/quitdesktop.png",0.3f);
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
        if (!pause){
            gameEngine.setPaused(false);
        }
    }
    public void draw(Graphics g){
        g.drawImage(overlay,bgX,bgY,bgW,bgH,null);
        resume.draw(g);
        restart.draw(g);
        quit.draw(g);
    }
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                pause=false;
                break;
        }
    }
    public void MousePressed(MouseEvent e){
        if (isIn(e,resume)){
            pause=false;
        } else if (isIn(e,restart)) {
            if (gameEngine.multiplayer){
                Packet06Restart packet06Restart=new Packet06Restart(gameEngine.getUsername());
                GameEngine.gameEngine.getSocketClient().sendData(packet06Restart.getData());
                gameEngine.restartGame();
            }
            else {
                gameEngine.restartGame();
            }
        } else if (isIn(e,quit)) {
            System.exit(0);
        }
    }

    private boolean isIn(MouseEvent e, ActionButtons b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
