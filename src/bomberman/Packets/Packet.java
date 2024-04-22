package bomberman.Packets;

public abstract class Packet {
    public static enum PacketTypes{
        INVALID(-1),LOGIN(00),DISCONNECT(01);
        private int PacketId;
        private PacketTypes(int PacketId){
            this.PacketId=PacketId;
        }
        public int getPacketId(){
            return PacketId;
        }
    }
    public byte PacketId;
    public Packet(int PacketId){
        this.PacketId=(byte) PacketId;
    }
    public abstract void writeData();
    //public abstract void
}
