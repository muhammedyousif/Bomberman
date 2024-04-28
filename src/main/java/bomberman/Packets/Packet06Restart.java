package bomberman.Packets;

import bomberman.Network.GameClient;
import bomberman.Network.GameServer;

public class Packet06Restart extends Packet{
    String username;

    public Packet06Restart(byte[] data) {
        super(06);
        String[] dataArray =readData(data).split(",");
        username=dataArray[0];
    }
    public Packet06Restart(String username){
        super(06);
        this.username=username;
    }

    @Override
    public byte[] getData() {
        return ("06"+username).getBytes();
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    public String  getUsername() {
        return username;
    }
}
