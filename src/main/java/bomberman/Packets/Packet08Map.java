package bomberman.Packets;

import bomberman.Network.GameClient;
import bomberman.Network.GameServer;

public class Packet08Map extends Packet{
    private String username;
    private int map ;
    public Packet08Map(byte[] data){
        super(98);
        String[] dataArray =readData(data).split(",");
        this.username=dataArray[0];
        this.map=Integer.parseInt( dataArray[1]);

    }
    public Packet08Map(String username,int map){
        super(98);
        this.username=username;
        this.map=map;
    }
    @Override
    public byte[] getData() {
        return ("98"+this.username+","+map).getBytes();

    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    public String getUsername() {
        return username;
    }

    public int getMap() {
        return map;
    }
}
