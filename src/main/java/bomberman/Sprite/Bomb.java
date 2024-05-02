package bomberman.Sprite;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import bomberman.Game.*;
import bomberman.Packets.Packet03Destroy;

import javax.swing.*;

public class Bomb extends Sprite {
    private int timeLeft = 300;
    private int strength = 2;
    private Level level;

    public Bomb(int x, int y, int height, int width, Image image, Level level) {
        super(x - 42 / 2, y - 42 / 2, 42, 42, image);
        this.level = level;
    }

    public boolean collides_with_sprite(int x1, int y1, int w1, int h1, Sprite sprite) {
        Rectangle rect = new Rectangle(x1, y1, w1, h1);
        Rectangle otherRect = new Rectangle(sprite.x, sprite.y, sprite.width, sprite.height);
        return rect.intersects(otherRect);
    }

    public int checkCollisionsLevel(int x1, int y1) {
        int i = 0;
        while (i < level.grid.size()) {
            if (collides_with_sprite(x1, y1, 50, 50, level.grid.get(i))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private boolean collides_with_player(Entity sprite) {
        Rectangle rect = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle otherRect = new Rectangle(sprite.getHitbox().x, sprite.getHitbox().y, sprite.getHitbox().width, sprite.getHitbox().height);
        return rect.intersects(otherRect);
    }


    public void tick() {
        timeLeft = timeLeft - 1;
        if (timeLeft == 0) {
            blowUp();
        }
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void blowUp() {
        level.explosions.add(new Explosion(x, y, level)); // Center explosion
        explodeInDirection(-1, 0); // explode left
        explodeInDirection(1, 0);  // explode right
        explodeInDirection(0, -1); // explode up
        explodeInDirection(0, 1);  // explode down
        level.bombs.remove(this);
    }

    public void explodeInDirection(int dx, int dy) {
        boolean found = false;
        for (int idx = 1; idx <= this.strength && !found; idx++) {
            int pos_to_check_x = this.x + (60 * idx * dx);
            int pos_to_check_y = this.y + (60 * idx * dy);
            int index = checkCollisionsLevel(pos_to_check_x, pos_to_check_y);
            if (index == -1) {
                level.explosions.add(new Explosion(pos_to_check_x, pos_to_check_y, level));
                checkPlayerHit(pos_to_check_x,pos_to_check_y);
                checkMonsterHit(pos_to_check_x,pos_to_check_y);

            } else {
                Sprite hitSprite = level.grid.get(index);
                if (hitSprite instanceof Box ) {
                    ((Box) hitSprite).blowUp();
                    level.explosions.add(new Explosion(pos_to_check_x, pos_to_check_y, level));
                    //send packet
                    if (GameEngine.gameEngine.multiplayer){
                        PlayerMP local = (PlayerMP) level.gameEngine.gameLogic.getLocal();
                        Packet03Destroy packet= new Packet03Destroy(local.getUsername(),((Box) hitSprite).id);
                        GameEngine.gameEngine.getSocketClient().sendData(packet.getData());
                    }
                    found = true;
                } else if (hitSprite instanceof Wall) {
                    found = true;
                } else if (hitSprite instanceof Barricade) {
                    ((Barricade) hitSprite).blowUp();
                    level.explosions.add(new Explosion(pos_to_check_x, pos_to_check_y, level));
                    //send packet FIX
                    if (GameEngine.gameEngine.multiplayer){
                        PlayerMP local = (PlayerMP) level.gameEngine.gameLogic.getLocal();
                        Packet03Destroy packet= new Packet03Destroy(local.getUsername(),((Box) hitSprite).id);
                        GameEngine.gameEngine.getSocketClient().sendData(packet.getData());
                    }
                    found=true;
                }
            }
        }

    }
    void checkPlayerHit(int x, int y) {
        Bomberman player =level.gameEngine.gameLogic.getPlayers().get(0);
        if (collides_with_player(player) ||player.collides_with_sprite(x, y, width, height, player)) {
                player.setAlive(false);
                level.explosions.add(new Explosion(x, y, level));  // Optional: Add explosion effect on player hit
            }

    }
    void checkMonsterHit(int x, int y){
        List<Monster> monsters = level.getMonsters();
        for (Monster monster : monsters){
            if (collides_with_player(monster) || monster.collides_with_sprite(x,y,monster.getHitbox().width, monster.getHitbox().height, monster)){
                monster.die();
                level.explosions.add(new Explosion(x, y, level));
            }
        }
    }

}