package bomberman.Game;

import bomberman.Network.GameClient;
import bomberman.Network.GameServer;
import bomberman.Packets.Packet;
import bomberman.Packets.Packet00Login;
import bomberman.Packets.Packet02Move;
import bomberman.Sprite.Entity;
import bomberman.Sprite.Monster;
import bomberman.Sprite.PlayerMP;
import bomberman.Sprite.PowerUp;
import bomberman.UI.MenuGUI;

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
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;


public class GameEngine extends JPanel implements Runnable,StateMethods{
    private Image background = new ImageIcon("src/bomberman/Assets/mapbackground.png").getImage();
    MenuGUI menuGUI;
    public GameLogic gameLogic;
    private int FPS_SET=120;
    public static GameEngine gameEngine;
    private int UPS_SET= 200;
    private Thread gameThread;
    private GameClient socketClient;
    private GameServer socketServer;
    private String username;
    public boolean server=false;
    public boolean multiplayer;

    public GameEngine(MenuGUI menuGUI){
        gameEngine=this;
        gameLogic = new GameLogic(this);
        this.menuGUI=menuGUI;
        setFocusable(true);
        startGameLoop();
        int multiplayerint= JOptionPane.showConfirmDialog(this,"Do you want to player multiPlayer?");
        if (multiplayerint==0)
            multiplayer=true;
        else {
            multiplayer=false;
        }
        if (multiplayer) {
            startServer();

            username = JOptionPane.showInputDialog("Username:");
            PlayerMP playerMP = new PlayerMP(80, 80, 40, 50, username, gameLogic.getLevel(), null, -1);
            gameLogic.getPlayers().add(playerMP);
            Packet00Login login = new Packet00Login(playerMP.getUsername(), playerMP.x, playerMP.y);
            if (socketServer != null) {
                socketServer.addConnection(playerMP, login);
            }
            login.writeData(socketClient);
        }
        else {
            Bomberman man = new Bomberman(75, 75, 40, 50, username, gameLogic.getLevel());
            gameLogic.getPlayers().add(man);
        }
        addKeyListener(new Keyboard(this));

        //socketClient.sendData("ping".getBytes());
    }

    private synchronized void startServer() {
        if (JOptionPane.showConfirmDialog(this,"Do you want to start the server?")==0){
            socketServer=new GameServer(this);
            socketServer.start();
            server=true;
        }
        socketClient=new GameClient(this,"localhost");
        socketClient.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, 896, 775, null);
        gameLogic.drawEverything(g);
        //drawBar(g);

    }

    private void drawBar(Graphics g) {
        int barHeight = 80; // The height of the black bar
        int screenWidth = getWidth(); // Assuming this is in a JPanel or similar
        int screenHeight = getHeight();
        g.setColor(Color.BLACK);
        g.fillRect(0, screenHeight - barHeight, screenWidth, barHeight);
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
                //System.out.println("FPS: " +frames+"| UPS: "+updates+"|x: ");
                if (username!=null) {
                    PlayerMP playerMP = (PlayerMP) getPlayers().get(gameLogic.getPlayerMPIndex(username));
                    System.out.println("left: " + playerMP.isLeft() + " down: " + playerMP.isDown() + " right: " + playerMP.isRight() + " up: " + playerMP.isUp());
                }
                frames = 0;
                updates=0;
            }
        }
    }

    private void update() {
        synchronized (getPlayers()) {

            for (int i = 0; i < getPlayers().size(); i++) {
                getPlayers().get(i).update();
            }
        }
        for (int i = 0; i < gameLogic.getLevel().getMonsters().size(); i++) {
            gameLogic.getLevel().getMonsters().get(i).update();
        }
        gameLogic.getLevel().tickBombs();
        if (!gameLogic.getPlayers().isEmpty()) {
            Bomberman player = gameLogic.getPlayers().get(0);

            if (!player.firstbomb)
                menuGUI.updateBombCounter();
        }

        ArrayList<PowerUp> toRemove = new ArrayList<>();
        for (PowerUp bombs : gameLogic.bombs) {
            if (bombs.isCollected()) {
                toRemove.add(bombs);
            } else {
                bombs.update();
            }
        }
        gameLogic.bombs.removeAll(toRemove); // Remove all collected bombs after iteration

    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Entity playerMP=null;
        if (multiplayer)
            playerMP= getPlayers().get(gameLogic.getPlayerMPIndex(username));
        else{
            if (!getPlayers().isEmpty())
                playerMP=getPlayers().get(0);
        }
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                ((Bomberman) playerMP).setLeft(true);
                break;
            case KeyEvent.VK_D:
                ((Bomberman) playerMP).setRight(true);
                break;
            case KeyEvent.VK_W:
                ((Bomberman) playerMP).setUp(true);
                break;
            case  KeyEvent.VK_S:
                ((Bomberman) playerMP).setDown(true);
                break;
            case KeyEvent.VK_E:
                ((Bomberman) playerMP).placeBomb();
                break;
            case  KeyEvent.VK_R:
                restartGame();
                break;
        }
        Packet02Move packet= new Packet02Move(((PlayerMP) playerMP).getUsername(), ((PlayerMP)playerMP).hitbox.x, ((PlayerMP)playerMP).hitbox.y,((PlayerMP)playerMP).isLeft(),((PlayerMP)playerMP).isRight(),((PlayerMP)playerMP).isUp(),((PlayerMP)playerMP).isDown());
        GameEngine.gameEngine.getSocketClient().sendData(packet.getData());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Entity playerMP=null;
        if (multiplayer)
            playerMP= getPlayers().get(gameLogic.getPlayerMPIndex(username));
        else{
            if (!getPlayers().isEmpty())
                playerMP=getPlayers().get(0);
        }
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                ((Bomberman) playerMP).setLeft(false);
                break;
            case KeyEvent.VK_D:
                ((Bomberman) playerMP).setRight(false);
                break;
            case KeyEvent.VK_W:
                ((Bomberman) playerMP).setUp(false);
                break;
            case  KeyEvent.VK_S:
                ((Bomberman) playerMP).setDown(false);
                break;

        }
        if (multiplayer) {
            Packet02Move packet = new Packet02Move(((PlayerMP) playerMP).getUsername(), playerMP.hitbox.x, playerMP.hitbox.y, ((PlayerMP)playerMP).isLeft(), ((PlayerMP)playerMP).isRight(), ((PlayerMP)playerMP).isUp(), ((PlayerMP)playerMP).isDown());
            GameEngine.gameEngine.getSocketClient().sendData(packet.getData());
        }

    }

    public void restartGame() {
        /*for (Bomberman man : gameLogic.getPlayers()){
            man.reset();
        }*/
        gameLogic.getLocal().reset();
        gameLogic = new GameLogic(this);
        //resetStatusbar();
    }

    private void resetStatusbar() {
        Bomberman player = gameLogic.getPlayers().get(gameLogic.getPlayerMPIndex(username));
        menuGUI.getStatusLabel().setText(": " + player.getBombCounter());
    }

    class NewFrameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            gameLogic.updateMonsters();
            repaint();
        }
    }

    public GameClient getSocketClient() {
        return socketClient;
    }

    public GameServer getSocketServer() {
        return socketServer;
    }

    public String getUsername() {
        return username;
    }
    public synchronized List<Bomberman> getPlayers(){
        return gameLogic.getPlayers();
    }

}
