package com.borichev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpaceShooter extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    TextureRegion region;
    int x;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        region = new TextureRegion(img, 128, 256);
        x = 0;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.5f, 0.6f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, x, x);
        batch.draw(region, 0, 0);
        batch.end();
        x++;
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
