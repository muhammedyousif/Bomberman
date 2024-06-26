package bomberman.Network;

import bomberman.Game.Bomberman;
import bomberman.Game.GameEngine;
import bomberman.Packets.*;
import bomberman.Sprite.Box;
import bomberman.Sprite.Monster;
import bomberman.Sprite.PlayerMP;
import bomberman.Sprite.Sprite;

import java.io.IOException;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static bomberman.Game.Constants.*;

public class GameClient extends Thread{
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private GameEngine gameEngine;
    private Bomberman player;
    private boolean recieved=false;
    public GameClient(GameEngine gameEngine,String ipAddress){
        this.gameEngine=gameEngine;
        try {
            this.socket=new DatagramSocket();
            this.ipAddress=InetAddress.getByName(ipAddress);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        catch (UnknownHostException e){
            e.printStackTrace();
        }
        player = GameEngine.gameEngine.gameLogic.getLocal();
    }
    public void run(){
        while (true){
            byte[] data=new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //System.out.println("server >" + new String( packet.getData()));
            try {
                parsePacket(packet.getData(),packet.getAddress(),packet.getPort());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) throws IOException {
        Packet p=null;
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookUpPacket(message.substring(0,2));
        switch (type){
            case INVALID:
                break;
            case LOGIN:
                p = new Packet00Login(data);
                handleLogin((Packet00Login) p,address,port);
                break;
            case DISCONNECT:
                p = new Packet01Disconnect(data);
                System.out.println("["+address.getHostAddress()+":"+ port+"] "+ ((Packet01Disconnect) p).getUsername() + " has left the world");
                gameEngine.gameLogic.removePlayerMP(((Packet01Disconnect)p).getUsername());

                break;
            case MOVE:
                p=new Packet02Move(data);
                handlePacket((Packet02Move) p);
                //System.out.println(((Packet02Move) p).getUsername()+" has moved to "+((Packet02Move) p).getX()+","+((Packet02Move) p).getY());
                break;
            case DESTROY:
                p=new Packet03Destroy(data);
                handleDestruction((Packet03Destroy)p);
                break;
            case BOMB:
                p=new Packet04Bomb(data);
                handleBomb((Packet04Bomb) p);
                break;
            case PLAYER_STATUS:
                p=new Packet05PlayerStatus(data);
                handleStatus((Packet05PlayerStatus) p);
                break;
            case MONSTER:
                p= new Packer07Monster((data));
                handleMonster((Packer07Monster)p);
                break;
            case RESET:
                p=new Packet06Restart(data);
                handleRestart((Packet06Restart) p);
                break;
            case MAP:
                if (!recieved) {
                    p = new Packet08Map(data);
                    recieved=true;
                    gameEngine.setBackgroundimg(((Packet08Map) p).getMap());
                }
                break;

        }
    }

    private void handleMonster(Packer07Monster p) {
            List<Monster> monsterList= player.getLevel().getMonsters();
            for (Monster monster : monsterList ){
                if (monster.getId()==p.getMonsterId()) {
                    //monster.x = p.getX();
                    monster.hitbox.x = p.getX();
                    //monster.y = p.getY();
                    monster.hitbox.y = p.getY();
                    monster.setAlive(p.isAlive());
                }
            }

    }

    private void handleRestart(Packet06Restart p) throws IOException {
            Bomberman player= gameEngine.gameLogic.getLocal();
            player.getLevel().gameEngine.restartGame();
            player.reset();
            gameEngine.gameLogic.reset();

    }


    private void handleStatus(Packet05PlayerStatus p) {
        int index=gameEngine.gameLogic.getPlayerMPIndex(p.getUsername());
        gameEngine.gameLogic.getPlayers().get(index).setAlive(p.isAlive());
    }

    private void handleBomb(Packet04Bomb p) {
        if (Objects.equals(p.getUsername(), gameEngine.getUsername())){
            return;
        }
        if (p.getType()==BOMB)
            gameEngine.gameLogic.getLevel().placeBomb(p.getX(),p.getY());
        else if (p.getType()==BIGBOMB) {
            gameEngine.gameLogic.getLevel().placeBigBomb(p.getX(),p.getY());
        }
    }

    private void handleDestruction(Packet03Destroy p) {
        /*for (Sprite block: gameEngine.gameLogic.getLevel().grid){
            if (block instanceof Box) {
                if (((Box) block).id == p.getId()) {
                    gameEngine.gameLogic.getLevel().grid.remove(block);
                }
            }
        }*/
        Iterator<Sprite> iterator = gameEngine.gameLogic.getLevel().grid.iterator();
        while (iterator.hasNext()) {
            Sprite block = iterator.next();
            if (block instanceof Box && ((Box) block).id == p.getId()) {
                iterator.remove();
                break;
            }
        }

    }

    private void handlePacket(Packet02Move p) {
        gameEngine.gameLogic.movePlayer(p.getUsername(),p.getX(),p.getY(),p.isLeft(),p.isRight(),p.isUp(),p.isDown());
    }
    private void handleLogin(Packet00Login p, InetAddress address, int port){
        System.out.println("["+address.getHostAddress()+":"+ port+"] "+ ( p).getUsername() + "  has joined the game");
        PlayerMP player = new PlayerMP(p.getX(),p.getY(),40,50, ((Packet00Login) p).getUsername(),gameEngine.gameLogic.getLevel(),address,port);
        gameEngine.gameLogic.getPlayers().add(player);
    }

    public void sendData(byte[] data){
        DatagramPacket packet=new DatagramPacket(data, data.length,ipAddress,1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
