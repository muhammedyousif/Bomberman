package bomberman.Sprite;
import java.awt.*;


public class Sprite {

    protected int x;
    protected int y;
    public int width;
    public int height;
    private Image image;
    protected Rectangle hitbox;
    public Sprite(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
        initHitbox();

    }
    private void initHitbox(){
        hitbox=new Rectangle(x,y,width,height);
    }
    public void updateHitbox(){
        hitbox.x=x;
        hitbox.y=y;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    protected void drawHitbox(Graphics g){
        g.setColor(Color.red);
        g.drawRect(hitbox.x,hitbox.y,hitbox.width,hitbox.height);
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

}
