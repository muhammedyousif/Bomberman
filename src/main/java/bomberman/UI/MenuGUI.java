package bomberman.UI;

import bomberman.Game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static bomberman.Game.Constants.GAME_HEIGHT;
import static bomberman.Game.Constants.GAME_WIDTH;
import static bomberman.UI.Page.page;


public class MenuGUI implements StateMethods{
    public JFrame frame;
    public GameEngine GE;
    public WindowHandler windowHandler;
    private JLabel statusLabel;
    private JLabel bigBombLabel;
    MainMenu mainMenu;
    GameModeMenu gameModeMenu;
    public MenuGUI(){
        mainMenu=new MainMenu(this);
        gameModeMenu=new GameModeMenu(this);
        frame = new JFrame();
        switchToGameEngine();
    }
    public void setStatusLabel(){
        if (GE.multiplayer) {
            if (!GE.server) {
                while (GE.gameLogic.getLocal() == null) {
                    System.out.println(GE.getUsername());
                }
            } else {
                while (GE.getSocketServer() == null || GE.gameLogic.getLocal() == null) {
                    System.out.println("MAKING");
                }
            }
        }
        frame.getContentPane().add(GE);
        JPanel statusBar = new JPanel();
        statusBar.setBackground(Color.BLACK);
        statusBar.setPreferredSize(new Dimension(GAME_WIDTH, 45));
        //bomb
        ImageIcon bomb = new ImageIcon("src/main/resources/Assets/bomb.png");
        Image image = bomb.getImage(); // Convert the ImageIcon to an Image
        Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // Scale it to fit your layout, here 20x20 is an example
        bomb = new ImageIcon(newimg);
        //big bomb
        ImageIcon bigBombIcon = new ImageIcon("src/main/resources/Assets/bigbomb.png");
        Image bigBombImage = bigBombIcon.getImage();
        Image scaledBigBombImage = bigBombImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        bigBombIcon = new ImageIcon(scaledBigBombImage);


        statusLabel = new JLabel(": "+GE.gameLogic.getLocal().getBombCounter());
        statusLabel.setIcon(bomb); // Set the icon to the label
        statusLabel.setHorizontalTextPosition(SwingConstants.RIGHT); // Text to the right of the icon
        statusLabel.setForeground(Color.WHITE);

        bigBombLabel = new JLabel(": " + GE.gameLogic.getLocal().getBigBombCount());
        bigBombLabel.setIcon(bigBombIcon);
        bigBombLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        bigBombLabel.setForeground(Color.WHITE);

        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.LINE_AXIS));
        statusBar.add(statusLabel);
        statusBar.add(Box.createHorizontalStrut(30));
        statusBar.add(bigBombLabel);

        GE.add(statusBar,BorderLayout.SOUTH);
        getmaniac();

    }
    public void switchToGameEngine() {
        GE = new GameEngine(this);
        frame.add(GE);
        GE.setVisible(true);
        frame.revalidate();
        frame.repaint();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GE.setLayout(new BorderLayout());
        //frame.setPreferredSize(new Dimension(920,843));
        frame.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                //reset dirbool
            }
        });

    }


    private void getmaniac() {
        try {
            // Create a Font object from the font file
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Assets/MonomaniacOne-Regular.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // Register the font
            ge.registerFont(customFont);
            statusLabel.setFont(customFont);
            bigBombLabel.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }


    }

    public void updateBombCounter() {
        int currentBombCount = GE.gameLogic.getLocal().getBombCounter();
        statusLabel.setText(": " + currentBombCount);
    }
    public void updateBigBombCounter() {
        int currentBombCount = GE.gameLogic.getLocal().getBigBombCount();
        bigBombLabel.setText(": " + currentBombCount);
    }


    public JLabel getStatusLabel() {
        return statusLabel;
    }
    @Override
    public void update() {
        switch (page){
            case MAINMENU:
                mainMenu.update();
                break;
            case GAMEMODE:
                gameModeMenu.update();
                break;
            default:
                break;
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            startGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (page){
            case MAINMENU:
                mainMenu.mousePressed(e);
                break;
            case GAMEMODE:
                gameModeMenu.mousePressed(e);
                break;
            default:
                break;
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (page){
            case MAINMENU:
                mainMenu.mouseMoved(e);
                break;
            case GAMEMODE:
                gameModeMenu.mouseMoved(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        switch (page){
            case MAINMENU:
                break;
            case GAMEMODE:
                //gameModeMenu.mouseExited(e);
                break;
            default:
                break;
        }


    }

    @Override
    public void render(Graphics g) {
        g.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        g.setColor(Color.BLACK);

        switch (page){
            case MAINMENU:
                mainMenu.render(g);
                break;
            case GAMEMODE:
                gameModeMenu.render(g);
                break;
            default:
                break;

        }
    }
    public void startGame(){
        setStatusLabel();
        GameState.state=GameState.GAME;
    }
}
