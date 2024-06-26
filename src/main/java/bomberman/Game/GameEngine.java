package bomberman.Game;

import bomberman.Network.GameClient;
import bomberman.Network.GameServer;
import bomberman.Packets.*;
import bomberman.Sprite.Entity;
import bomberman.Sprite.PlayerMP;
import bomberman.Sprite.PowerUp;
import bomberman.UI.MenuGUI;
import bomberman.UI.PauseOverlay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import static bomberman.Game.Constants.*;


public class GameEngine extends JPanel implements Runnable,StateMethods{
    private BufferedImage background;
    public MenuGUI menuGUI;
    public GameLogic gameLogic;
    private int FPS_SET=120;
    public static GameEngine gameEngine;
    private int UPS_SET= 200;
    private Thread gameThread;
    private GameClient socketClient;
    private GameServer socketServer;
    private String username;
    public boolean server=false;
    public boolean multiplayer,serverhost;
    private boolean paused;
    private PauseOverlay pauseOverlay;
    private Mouse mouse;
    private int map;
    public GameEngine(MenuGUI menuGUI) throws IOException {
        gameEngine=this;
        gameLogic = new GameLogic(this);
        this.menuGUI=menuGUI;
        setFocusable(true);
        startGameLoop();
        //int multiplayerint= JOptionPane.showConfirmDialog(this,"Do you want to play multiplayer?");
        mouse=new Mouse(this);
        addKeyListener(new Keyboard(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        pauseOverlay=new PauseOverlay(this);
        //socketClient.sendData("ping".getBytes());
    }

    public void setBackgroundimg(int map) {
        this.map=map;
        String location="";
        switch (map){
            case GREEN:
                location=GREENLOC;
                gameLogic.setLevel(gameLogic.getLevels().get(0));
                break;
            case PINK:
                location=PINKLOC;
                gameLogic.setLevel(gameLogic.getLevels().get(3));

                break;
            default:
                break;
        }
        try {
            InputStream backgroundlink = getClass().getClassLoader().getResourceAsStream(location);
            background= ImageIO.read(backgroundlink);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void multiplayerSetup(int multiplayerint,String username,int map) {
        if (multiplayerint==0)
            multiplayer=true;
        else {
            multiplayer=false;
        }
        if (multiplayer) {
            startServer(map);
            this.username=username;
            //username = JOptionPane.showInputDialog("Username:");
            PlayerMP playerMP = new PlayerMP(SPAWN1, SPAWN1Y, 40, 50, username, gameLogic.getLevel(), null, -1);
            gameLogic.getPlayers().add(playerMP);
            Packet00Login login = new Packet00Login(playerMP.getUsername(), playerMP.x, playerMP.y);
            if (socketServer != null) {
                socketServer.addConnection(playerMP, login);
            }
            login.writeData(socketClient);
        }
        else {
            Bomberman man = new Bomberman(SPAWN1, SPAWN1Y, 40, 50, username, gameLogic.getLevel());
            gameLogic.getPlayers().add(man);
        }
    }

    private synchronized void startServer(int map) {
        if (serverhost){
            socketServer=new GameServer(this,map);
            socketServer.start();
            server=true;
        }
        socketClient=new GameClient(this,"localhost");
        socketClient.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        switch (GameState.state){
            case MENU:
                menuGUI.render(g);
                break;
            case GAME:
                render(g);
                break;
            default:
                break;
        }


    }
    @Override
    public void render(Graphics g) {
        setFocusable(true);
        requestFocus();
        super.paintComponent(g);
        g.drawImage(background, 0, -64, GAME_WIDTH, GAME_HEIGHT, null);
        gameLogic.drawEverything(g);
        if (paused) {
            Graphics2D g2d = (Graphics2D) g.create();
            Color overlayColor = new Color(0, 0, 0, 90);
            g2d.setColor(overlayColor);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
            pauseOverlay.draw(g);
        }
    }

    private void drawBar(Graphics g) {
        int barHeight = 80;
        int screenWidth = getWidth();
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
                //System.out.println("FPS: " +frames+"| UPS: "+updates);
                //System.out.println(paused);
                frames = 0;
                updates=0;
            }
        }
    }
    @Override
    public void update() {
        switch (GameState.state){
            case MENU:
                menuGUI.update();
                break;
            case GAME:
                updateGame();
                break;
            default:
                break;
        }

    }

    private void updateGame() {
        if (!paused || multiplayer){
            if (gameLogic.getLevel()!=null) {
                synchronized (getPlayers()) {
                    for (int i = 0; i < getPlayers().size(); i++) {
                        getPlayers().get(i).update();
                    }
                }

                for (int i = 0; i < gameLogic.getLevel().getMonsters().size(); i++) {
                    gameLogic.getLevel().getMonsters().get(i).update();
                    if (multiplayer){

                    }
                }
                gameLogic.getLevel().tickBombs();
                if (!gameLogic.getPlayers().isEmpty()) {
                    Bomberman player = gameLogic.getLocal();
                    if (!player.firstbomb) {
                        menuGUI.updateBombCounter();
                        menuGUI.updateBigBombCounter();
                    }
                }
                ArrayList<PowerUp> toRemove = new ArrayList<>();
                for (PowerUp bombs : gameLogic.bombs) {
                    if (bombs.isCollected()) {
                        toRemove.add(bombs);
                    } else {
                        bombs.update();
                    }
                }
                gameLogic.bombs.removeAll(toRemove);
            }
        }

        if (paused||multiplayer) {
            if (pauseOverlay!=null)
                pauseOverlay.update();
        }
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void keyPressed(KeyEvent e) throws IOException {
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
            case KeyEvent.VK_F:
                ((Bomberman) playerMP).placeBigBomb();
                break;
            case KeyEvent.VK_ESCAPE:
                paused=true;
                pauseOverlay.setPause(true);
                break;
            case  KeyEvent.VK_R:
                if (multiplayer){
                    Packet06Restart packet06Restart=new Packet06Restart(username);
                    GameEngine.gameEngine.getSocketClient().sendData(packet06Restart.getData());
                    restartGame();
                }
                else {
                    restartGame();
                }
                break;
        }
        if (multiplayer) {
            Packet02Move packet = new Packet02Move(((PlayerMP) playerMP).getUsername(), ((PlayerMP) playerMP).hitbox.x, ((PlayerMP) playerMP).hitbox.y, ((PlayerMP) playerMP).isLeft(), ((PlayerMP) playerMP).isRight(), ((PlayerMP) playerMP).isUp(), ((PlayerMP) playerMP).isDown());
            GameEngine.gameEngine.getSocketClient().sendData(packet.getData());
        }
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


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void restartGame() throws IOException {
        /*for (Bomberman man : gameLogic.getPlayers()){
            man.reset();
        }*/
        //gameLogic.getLocal().reset();
        gameLogic.reset();
        //gameLogic = new GameLogic(this);

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

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public PauseOverlay getPauseOverlay() {
        return pauseOverlay;
    }

    public int getMap() {
        return map;
    }
}
