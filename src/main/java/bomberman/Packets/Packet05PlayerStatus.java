package bomberman.Packets;

import bomberman.Network.GameClient;
import bomberman.Network.GameServer;

public class Packet05PlayerStatus extends Packet{
    String username;
    private boolean alive;
    public Packet05PlayerStatus(byte[] data) {
        super(05);
        String[] dataArray =readData(data).split(",");
        username=dataArray[0];
        alive= Boolean.parseBoolean(dataArray[1]);
    }
    public Packet05PlayerStatus(String username,boolean alive) {
        super(05);
        this.username=username;
        this.alive=alive;
    }

    @Override
    public byte[] getData() {
        return ("05"+username+","+alive).getBytes();
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    public boolean isAlive() {
        return alive;
    }

    public String getUsername() {
        return username;
    }
}
