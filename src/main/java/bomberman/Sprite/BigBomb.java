package bomberman.Sprite;

import bomberman.Game.Explosion;
import bomberman.Game.Level;

import java.awt.*;

public class BigBomb extends Bomb{
    private int strength=3;
    private Level level;
    public BigBomb(int x, int y, int height, int width, Image image, Level level) {
        super(x, y, height, width, image, level);
        this.level=level;
    }

    @Override
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
                if (hitSprite instanceof Box) {
                    ((Box) hitSprite).blowUp();
                    level.explosions.add(new Explosion(pos_to_check_x, pos_to_check_y, level));
                    found = true;
                } else if (hitSprite instanceof Wall) {
                    found = true;
                }
            }
        }

    }
}
