package com.borichev.pool;

import com.badlogic.gdx.audio.Sound;

import com.borichev.base.PowerUp;
import com.borichev.base.SpritesPool;
import com.borichev.math.Rect;
import com.borichev.sprite.EnemyShip;
import com.borichev.utils.PowerUpProducer;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private PowerUpProducer powerUpProducer;
    private Rect worldBounds;
    private Sound sound;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool,
                     PowerUpProducer powerUpProducer, Rect worldBounds, Sound sound) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.powerUpProducer = powerUpProducer;
        this.worldBounds = worldBounds;
        this.sound = sound;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, powerUpProducer, worldBounds, false, sound);
    }
}
