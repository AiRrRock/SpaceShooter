package com.borichev.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.borichev.base.PowerUp;
import com.borichev.math.Rect;
import com.borichev.math.Rnd;
import com.borichev.pool.PowerUpPool;
import com.borichev.utils.enums.PowerUps;

public class PowerUpProducer {

    private TextureRegion healthRegions;
    private TextureRegion bulletSpeedRegions;
    private TextureRegion powerRegions;

    private Rect worldBounds;
    private PowerUpPool powerUpPool;

    private Vector2 speed = new Vector2(0f, 0.12f);

    private float generateInterval = 5f;
    private float generateTimer;


    public PowerUpProducer(TextureAtlas atlas, Rect worldBounds, PowerUpPool powerUpPool) {
        this.worldBounds = worldBounds;
        this.powerUpPool = powerUpPool;

        healthRegions = atlas.findRegion("puHealth");
        bulletSpeedRegions = atlas.findRegion("puSpeed");
        powerRegions = atlas.findRegion("puPower");
    }

    public void produce(Vector2 pos) {
        PowerUp powerUp = powerUpPool.obtain();
        float random = Rnd.nextFloat(0f, 0.9f);
        if (random <= 0.3) {
            powerUp.setPowerUp(com.borichev.utils.enums.PowerUps.HEALTH, healthRegions, speed);
        } else if (random <= 0.6) {
            powerUp.setPowerUp(com.borichev.utils.enums.PowerUps.BULLET_SPEED, bulletSpeedRegions, speed);
        } else {
            powerUp.setPowerUp(PowerUps.POWER, powerRegions, speed);
        }
        powerUp.pos.set(pos);
    }

}
