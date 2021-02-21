package com.borichev.pool;


import com.borichev.base.SpritesPool;
import com.borichev.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

}
