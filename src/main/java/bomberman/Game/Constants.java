package bomberman.Game;

public class Constants {
    public static final int IDLE=4;
    public static final int RUNNING_LEFT=1;
    public static final int RUNNING_RIGHT=3;
    public static final int RUNNING_UP=0;
    public static final int RUNNING_DOWN=2;
    public static  final int DEAD=5;
    public static final int BARRICADE=3;
    public static final int BIGBOMB=1;
    public static final int BOMB=0;
    public static final int  GAME_WIDTH=1033;
    public static final int GAME_HEIGHT=790;
    public static final int SPAWN1=GAME_WIDTH/2;
    public static final int SPAWN1Y=30;


    public static int getSprite(int player_action){
        switch (player_action){
            case RUNNING_LEFT:
                return 3;
            case IDLE:
                return 1;
            case RUNNING_RIGHT:
                return 3;
            case RUNNING_UP:
                return 3;
            case RUNNING_DOWN:
                return 3;
            case DEAD:
                return 6;
            default:
                return 1;
        }
    }
}
