package bomberman.Packets;

import bomberman.Network.GameClient;
import bomberman.Network.GameServer;


public class Packet03Destroy extends Packet{
    String username;
    private int id;

    public Packet03Destroy(byte[] data) {
        super(03);
        String[] dataArray =readData(data).split(",");
        this.username=dataArray[0];
        id = Integer.parseInt(dataArray[1]);
    }
    public Packet03Destroy(String username,int index){
        super(03);
        this.username=username;
        this.id =index;
    }
    @Override
    public byte[] getData() {
        return ("03" +this.username+","+this.id).getBytes();

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

    public int getId() {
        return id;
    }
}
