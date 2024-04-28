package bomberman.Packets;

import bomberman.Network.GameClient;
import bomberman.Network.GameServer;

public abstract class Packet {
    public static enum PacketTypes{
        INVALID(-1),LOGIN(00),DISCONNECT(01),MOVE(02),DESTROY(03);
        private int PacketId;
        private PacketTypes(int PacketId){
            this.PacketId=PacketId;
        }
        public int getId(){
            return PacketId;
        }
    }
    public byte PacketId;
    public Packet(int PacketId){
        this.PacketId=(byte) PacketId;
    }
    public abstract byte[] getData();
    public abstract void writeData(GameClient client);
    public abstract void writeData(GameServer server);
    public String readData(byte[] data){
        String message = new String(data).trim();
        return message.substring(2);
    }
    public static PacketTypes lookUpPacket(String packetId){
        try {
            return lookUpPacket(Integer.parseInt(packetId));
        }catch (NumberFormatException e){
            return PacketTypes.INVALID;
        }
    }
    public static PacketTypes lookUpPacket(int id){
        for (PacketTypes p : PacketTypes.values()){
            if (p.getId()==id){
                return p;
            }
        }
        return PacketTypes.INVALID;
    }
}
