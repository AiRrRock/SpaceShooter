package com.borichev.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.borichev.math.Rect;
import com.borichev.pool.BulletPool;
import com.borichev.sprite.Bullet;


public class Ship extends Sprite {

    protected Vector2 v0;
    protected Vector2 v;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected float bulletHeight;
    protected int damage;

    protected float reloadInterval;
    protected float reloadTimer;

    protected int hp;

    protected Sound sound;

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        calculateCollision();
        if (hp<=0){
            this.destroy();
        }
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
        sound.play();
    }

    public void setReloadTimer(float reloadTimer) {
        this.reloadTimer = reloadTimer;
    }

    public void calculateCollision() {
        for (Bullet bullet : bulletPool.activeObjects) {
            if (!bullet.getOwner().equals(this)) {
                if (this.isMe(bullet.pos)) {
                    this.hp -= bullet.getDamage();
                    bullet.destroy();
                }
            }

        }
    }

}
