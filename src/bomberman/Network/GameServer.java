package bomberman.Network;

import bomberman.Game.GameEngine;
import bomberman.Packets.Packet;
import bomberman.Packets.Packet00Login;
import bomberman.Packets.Packet01Disconnect;
import bomberman.Sprite.PlayerMP;
import bomberman.UI.MenuGUI;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread{
    private DatagramSocket socket;
    private GameEngine gameEngine;
    private List<PlayerMP> connectedPlayers=new ArrayList<>();
    private boolean loginFinished=false;
    public GameServer(GameEngine gameEngine){
        this.gameEngine=gameEngine;
        try {
            this.socket=new DatagramSocket(1331);
        } catch (SocketException e) {
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
            parsePacket(packet.getData(),packet.getAddress(),packet.getPort());
            /*String message = new String(packet.getData());
            System.out.println("client >" + message);
            if (message.trim().equalsIgnoreCase("ping"))
                sendData("pong".getBytes(),packet.getAddress(),packet.getPort());*/
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
                System.out.println("["+address.getHostAddress()+":"+ port+"] "+ ((Packet00Login) p).getUsername() + "has connected");
                PlayerMP player = new PlayerMP(100,100,40,50, ((Packet00Login) p).getUsername(),gameEngine.gameLogic.getLevel(),address,port);
                this.addConnection(player,(Packet00Login) p);
                //connectedPlayers.add(player);
                //gameEngine.gameLogic.getPlayers().add(player);
                loginFinished=true;
                break;
            case DISCONNECT:
                p = new Packet01Disconnect(data);
                System.out.println("["+address.getHostAddress()+":"+ port+"] "+ ((Packet01Disconnect) p).getUsername() + " has left");
                this.removeConnection((Packet01Disconnect) p);
                break;
        }
    }

    public void removeConnection(Packet01Disconnect p) {
        connectedPlayers.remove(getPlayerMPIndex(p.getUsername()));
        p.writeData(this);
    }

    public void addConnection(PlayerMP player, Packet00Login p) {
        boolean alreadyConnected=false;
        for (PlayerMP playerMP: connectedPlayers) {
            if (playerMP.getUsername().equalsIgnoreCase(player.getUsername())){
                if(playerMP.ipAddress==null){
                    playerMP.ipAddress=player.ipAddress;
                }
                if (playerMP.port==-1){
                    playerMP.port=player.port;
                }
                alreadyConnected=true;
            }
            else {
                sendData(p.getData(),playerMP.ipAddress,playerMP.port);
                p = new Packet00Login(playerMP.getUsername());
                sendData(p.getData(), player.ipAddress, player.port);

            }
        }
        if (!alreadyConnected){
            this.connectedPlayers.add(player);
            //p.writeData(this);
        }
    }

    public void sendData(byte[] data,InetAddress ipAddress,int port){
        DatagramPacket packet=new DatagramPacket(data, data.length,ipAddress,port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for (PlayerMP p: connectedPlayers){
            sendData(data,p.ipAddress,p.port);
        }
    }

    public boolean isLoginFinished() {
        return loginFinished;
    }

    public List<PlayerMP> getConnectedPlayers() {
        return connectedPlayers;
    }
    public PlayerMP getPlayerMP(String username){
        for (PlayerMP player:connectedPlayers) {
            if (player.getUsername().equals(username))
                return player;
        }
        return null;
    }
    public int getPlayerMPIndex(String username){
        int index=0;
        for (PlayerMP player:connectedPlayers) {
            if (player.getUsername().equals(username))
                break;
            index++;
        }
        return index;
    }
}