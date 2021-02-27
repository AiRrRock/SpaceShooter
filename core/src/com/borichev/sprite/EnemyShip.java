package com.borichev.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.borichev.base.Ship;
import com.borichev.math.Rect;
import com.borichev.math.Rnd;
import com.borichev.pool.BulletPool;
import com.borichev.pool.ExplosionPool;
import com.borichev.utils.enums.Points;
import com.borichev.utils.PowerUpProducer;

public class EnemyShip extends Ship {

    private static final float START_V_Y = -0.3f;
    private boolean boss;
    private Points points;
    private PowerUpProducer powerUpProducer;

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, PowerUpProducer powerUpProducer,
                     Rect worldBounds, boolean boss, Sound sound) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.powerUpProducer = powerUpProducer;
        this.worldBounds = worldBounds;
        this.sound = sound;
        v = new Vector2();
        v0 = new Vector2();
        bulletPos = new Vector2();
        bulletV = new Vector2();
        this.boss = boss;
    }

    @Override
    public void update(float delta) {
        if (getTop() > worldBounds.getTop()) {
            reloadTimer = reloadInterval * 0.8f;
        } else if (!v.equals(v0)) {
            if (boss) {
                toggleDirection();
            } else {
                v.set(v0);
            }
        }
        super.update(delta);
        bulletPos.set(pos.x, pos.y - getHalfHeight());
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }


    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            Vector2 bulletV,
            int damage,
            float reloadInterval,
            float height,
            boolean boss,
            int hp,
            Points points
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(bulletV);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        this.boss = boss;
        v.set(0, START_V_Y);
        this.points = points;
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
        );
    }

    private void toggleDirection() {
        if (getTop() > worldBounds.getTop()) {
            return;
        } else {
            if (worldBounds.getRight() < getRight()) {
                v.set(-0.07f, 0f);
            } else if (worldBounds.getLeft() > getLeft()) {
                v.set(0.07f, 0f);
            } else if (v.y > 0 || v.y < 0) {
                v.set(0.07f, 0f);
            }
        }
    }

    public boolean isBoss() {
        return boss;
    }

    public Points getPoints() {
        return points;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (boss) {
            producePowerUp(10);
        } else {
            if (Rnd.nextFloat(0, 1f) > 0.3f) {
                producePowerUp(1);
            }
        }
    }

    private void producePowerUp(int number) {
        for (int i = 0; i < number; i++) {
            powerUpProducer.produce(this.pos);
        }
    }
}
