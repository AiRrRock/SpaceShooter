package com.borichev.pool;

import com.borichev.base.PowerUp;
import com.borichev.base.SpritesPool;
import com.borichev.math.Rect;

public class PowerUpPool extends SpritesPool<PowerUp> {

    private Rect worldBounds;

    public PowerUpPool(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    protected PowerUp newObject() {
        return new PowerUp();
    }
}
