package bomberman.Game;
import bomberman.Sprite.*;
import bomberman.Sprite.Box;
import java.awt.Image;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Graphics;
public class Level {
    public GameEngine gameEngine;
    private BufferedReader br;
    public ArrayList<Sprite> grid;
    public ArrayList<Bomb> bombs;
    public ArrayList<BigBomb>bigBombs;
    public ArrayList<Explosion> explosions;
    private ArrayList<Monster> monsters;
    private ArrayList<ArrayList<Integer>> snap_positions = new ArrayList<>();
    private ArrayList<Barricade> barricades;
    private final int block_width = 61;
    private final int block_height = 61;
    private int gameWidth=900;
    private int gameHeight=800;
    private int id=0;
    private int monsterId=0;

    public Level(String levelPath,GameEngine gameEngine) throws IOException {
        explosions = new ArrayList<Explosion>();
        bombs = new ArrayList<Bomb>();
        monsters=new ArrayList<Monster>();
        grid = new ArrayList<>();
        bigBombs=new ArrayList<>();
        barricades=new ArrayList<>();
        fileToLevel(levelPath);
        getSnapPositions();
        this.gameEngine=gameEngine;
    }

    private void getSnapPositions(){
        for(int x = 0; x < 15; x++){
            for(int y = 0; y < 13; y++){
                ArrayList<Integer> tmp = new ArrayList<>();
                tmp.add(block_width/2 + (x*block_width));
                tmp.add(block_height/2 + (y*block_height));
                snap_positions.add(tmp);
            }
        }
    }
    private static float distance(float x1, float y1, float x2, float y2) {
        float deltaX = x2 - x1;
        float deltaY = y2 - y1;
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public Bomb placeBomb(int x, int y){
        int closest_x = block_width/2;
        int closest_y = block_height/2;
        float distance = 500;
        for (ArrayList<Integer> tuple : snap_positions) {
            float currentDistance = distance(tuple.get(0), tuple.get(1), x, y);
            if (currentDistance < distance) {
                distance = currentDistance;
                closest_y = tuple.get(1);
                closest_x = tuple.get(0);
            }
        }
        Image image = new ImageIcon(getClass().getResource("/Assets/bomb.png")).getImage();
        Bomb b = new Bomb(closest_x,closest_y,50,50,image,this);
        b.setIgnoreCollisionWithPlayer(true);
        for (Bomb bomb : bombs) {
            if (bomb.collides_with_sprite(bomb.getX(),bomb.getY(),bomb.width,bomb.height,b)) {
                return null;
            }
        }
        int index = bombs.size();
        if (!gameEngine.multiplayer)
            grid.add(b);
        bombs.add(b);
        return bombs.get(index);
    }
    public BigBomb placeBigBomb(int x,int y){
        int closest_x = 30;
        int closest_y = 30;
        float distance = 500;
        for (ArrayList<Integer> tuple : snap_positions) {
            float currentDistance = distance(tuple.get(0), tuple.get(1), x, y);
            if (currentDistance < distance) {
                distance = currentDistance;
                closest_y = tuple.get(1);
                closest_x = tuple.get(0);
            }
        }
        Image image = new ImageIcon(getClass().getResource("/Assets/bigBomb.png")).getImage();
        BigBomb b = new BigBomb(closest_x,closest_y,50,50,image,this);
        b.setIgnoreCollisionWithPlayer(true);
        for (Bomb bomb : bombs) {
            if (bomb.collides_with_sprite(bomb.getX(),bomb.getY(),bomb.width,bomb.height,b)) {
                return null;
            }
        }
        int index = bombs.size();
        bombs.add(b);
        return (BigBomb) bombs.get(index);
    }



    private void fileToLevel(String levelPath) throws IOException {
        InputStream ist = getClass().getClassLoader().getResourceAsStream(levelPath);
        if (ist == null) {
            throw new FileNotFoundException("Resource not found: " + levelPath);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(ist));
        bombs = new ArrayList<>();
        int y = 0;
        int diff=0;
        int xoffset=58+1;
        int yoffset=120-60-block_height*2;
        String line;
        id=0;
        while ((line = br.readLine()) != null) {
            int x = 0;
            for (char blockType : line.toCharArray()) {

                if (blockType == '1') {
                    Image image = new ImageIcon(getClass().getResource("/Assets/wall.png")).getImage();
                    grid.add(new Wall((x * block_width)+xoffset, (y * block_height)+yoffset, block_width, block_height,image));
                } else if (blockType == '2') {
                    Image image = new ImageIcon(getClass().getResource("/Assets/box.png")).getImage();
                    grid.add(new Box((int) (x * block_width+2.5)+xoffset, (int) (y * block_height+2.5)+yoffset, block_width-5, block_height-5, image,this,id));
                    id++;
                } else if (blockType == '3') {
                    Image image = new ImageIcon(getClass().getResource("/Assets/monster.png")).getImage();
                    Monster monster = new Monster((x * block_width)+xoffset, (y * block_height)+yoffset, block_width, block_height,monsterId);
                    monsterId++;
                    monster.setLevel(this);
                    //grid.add(monster);
                    //grid.add(null);
                    monsters.add(monster);

                }
                x++;
            }
            y++;
        }
    }

    public ArrayList<Sprite> getGrid(){
        return this.grid;
    }
    public ArrayList<Monster> getMonsters(){
        /*ArrayList<Monster> monsters = new ArrayList<>();
        for(Sprite sprite : grid){
            if(sprite instanceof Monster){
                monsters.add((Monster) sprite);
            }
        }
        return monsters;*/
        return monsters;
    }

    public void tickBombs(){
        for(int i = 0; i < bombs.size(); i++){
            bombs.get(i).tick();
        }
        for(int i = 0; i < explosions.size(); i++){
            explosions.get(i).tick();
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i <grid.size(); i++) {
            if (!(grid.get(i)instanceof Wall)) {
                grid.get(i).draw(g);
            }
            //grid.get(i).draw(g);
        }
        for(int i = 0; i < bombs.size(); i++){
            bombs.get(i).draw(g);
        }
        for(int i = 0; i < explosions.size();i++){
            explosions.get(i).draw(g);
        }
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public int getBlock_width() {
        return block_width;
    }

    public int getBlock_height() {
        return block_height;
    }

    public ArrayList<Barricade> getBarricades() {
        return barricades;
    }
}
