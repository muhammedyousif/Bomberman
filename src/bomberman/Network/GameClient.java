package bomberman.Network;

import bomberman.Game.GameEngine;
import bomberman.Packets.Packet;
import bomberman.Packets.Packet00Login;
import bomberman.Packets.Packet01Disconnect;
import bomberman.Packets.Packet02Move;
import bomberman.Sprite.PlayerMP;

import java.io.IOException;
import java.net.*;

public class GameClient extends Thread{
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private GameEngine gameEngine;
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
            parsePacket(packet.getData(),packet.getAddress(),packet.getPort());

        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
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
        }
    }

    private void handlePacket(Packet02Move p) {
        gameEngine.gameLogic.movePlayer(p.getUsername(),p.getX(),p.getY());
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
