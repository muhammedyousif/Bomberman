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
import java.awt.Font;

public class GameEngine extends JPanel{
    private Image background = new ImageIcon("src/bomberman/Assets/background.png").getImage();
    private GameLogic gameLogic;
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
        Timer newFrameTimer = new Timer(1000/FPS, new NewFrameListener());
        newFrameTimer.start();
    }
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 900, 780, null);
        gameLogic.drawEverything(grphcs);

    }

    class NewFrameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            gameLogic.updateMonsters();
            repaint();
        }
    }


}
