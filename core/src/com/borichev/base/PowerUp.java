package com.borichev.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.borichev.math.Rect;
import com.borichev.utils.enums.PowerUps;

public class PowerUp extends Sprite {
    private static final float HEIGHT = 0.05f;
    private PowerUps type;
    protected Vector2 v = new Vector2(0,-0.06f);

    public PowerUp() {
    }

    public void setPowerUp(PowerUps type, TextureRegion regions, Vector2 v) {
        this.type = type;
        this.regions = new TextureRegion[1];
        this.regions[0] = regions;
        setHeightProportion(HEIGHT);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }

    public PowerUps getType() {
        return type;
    }
}
