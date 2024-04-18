package bomberman.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.awt.*;


public class GameEngine extends JPanel implements Runnable,StateMethods{
    private Image background = new ImageIcon("src/bomberman/Assets/mapbackground.png").getImage();
    public GameLogic gameLogic;
    private int FPS_SET=120;
    private int UPS_SET= 200;
    private Thread gameThread;

    public GameEngine(){
        int FPS = 60;
        gameLogic = new GameLogic(this);
        addKeyListener(new Keyboard(this));
        setFocusable(true);
        startGameLoop();

    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 896, 775, null);
        gameLogic.drawEverything(grphcs);

    }
    @Override
    public void run() {

        double timeframe = 1000000000.0/FPS_SET;
        double timeupdtae = 1000000000.0/UPS_SET;
        long previousTime= System.nanoTime();

        int updates = 0;
        int frames=0;
        long lastCheck = System.currentTimeMillis();
        double deltaU=0;
        double deltaF=0;
        while (true){
            long currentTime = System.nanoTime();
            deltaU+=(currentTime-previousTime)/timeupdtae;
            deltaF+=(currentTime-previousTime)/timeframe;

            previousTime=currentTime;
            if (deltaU>=1){
                update();
                updates++;
                deltaU--;
            }
            if (deltaF>=1){
                this.repaint();
                deltaF--;
                frames++;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " +frames+"| UPS: "+updates+"|x: ");
                frames = 0;
                updates=0;
            }
        }
    }

    private void update() {
        for (int i = 0; i <gameLogic.getPlayers().size() ; i++) {
            gameLogic.getPlayers().get(i).update();
        }
        for (int i = 0; i < gameLogic.getLevel().getMonsters().size(); i++) {
            gameLogic.getLevel().getMonsters().get(i).update();
        }
        gameLogic.getLevel().tickBombs();

    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                gameLogic.getPlayers().get(0).setLeft(true);
                break;
            case KeyEvent.VK_D:
                gameLogic.getPlayers().get(0).setRight(true);
                break;
            case KeyEvent.VK_W:
                gameLogic.getPlayers().get(0).setUp(true);
                break;
            case  KeyEvent.VK_S:
                gameLogic.getPlayers().get(0).setDown(true);
                break;
            case KeyEvent.VK_E:
                gameLogic.getPlayers().get(0).placeBomb();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                gameLogic.getPlayers().get(0).setLeft(false);
                break;
            case KeyEvent.VK_D:
                gameLogic.getPlayers().get(0).setRight(false);
                break;
            case KeyEvent.VK_W:
                gameLogic.getPlayers().get(0).setUp(false);
                break;
            case  KeyEvent.VK_S:
                gameLogic.getPlayers().get(0).setDown(false);
                break;
        }

    }

    public void restartGame() {
        for (Bomberman man : gameLogic.getPlayers()){
            man.reset();
        }
    }

    class NewFrameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            gameLogic.updateMonsters();
            repaint();
        }
    }


}
