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


public class GameEngine extends JPanel implements Runnable{
    private Image background = new ImageIcon("src/bomberman/Assets/mapbackground.png").getImage();
    private GameLogic gameLogic;
    private int FPS_SET=120;
    private int UPS_SET= 200;
    private Thread gameThread;

    public GameEngine(){
        int FPS = 60;
        gameLogic = new GameLogic();
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed W");
        this.getActionMap().put("pressed W", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameLogic.pressed("W");
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed S");
        this.getActionMap().put("pressed S", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameLogic.pressed("S");
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed A");
        this.getActionMap().put("pressed A", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameLogic.pressed("A");
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed D");
        this.getActionMap().put("pressed D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameLogic.pressed("D");
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("E"), "pressed E");
        this.getActionMap().put("pressed E", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameLogic.pressed("E");
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("released W"), "released W");
        this.getInputMap().put(KeyStroke.getKeyStroke("released A"), "released A");
        this.getInputMap().put(KeyStroke.getKeyStroke("released S"), "released S");
        this.getInputMap().put(KeyStroke.getKeyStroke("released D"), "released D");

        //key release hogy tudjam hogy mar nem mozog (animacionak)
        this.getActionMap().put("released W", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameLogic.released("W"); // Assuming this method sets moving to false
            }
        });
        this.getActionMap().put("released S", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameLogic.released("S");
            }
        });

        this.getActionMap().put("released A", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameLogic.released("A");
            }
        });

        this.getActionMap().put("released D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameLogic.released("D");
            }
        });


//        Timer newFrameTimer = new Timer(1000/FPS, new NewFrameListener());
//        newFrameTimer.start();
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
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    class NewFrameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            gameLogic.updateMonsters();
            repaint();
        }
    }


}
