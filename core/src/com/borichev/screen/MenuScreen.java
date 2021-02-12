package com.borichev.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.borichev.base.BaseScreen;
import com.borichev.math.Rect;
import com.borichev.sprite.Background;

public class MenuScreen extends BaseScreen {
    Texture img;
    Texture bg;
    Background background;
    int movementSpeed;
    Vector2 position;
    Vector2 v;
    Vector2 tmp;
    Vector2 touch;

    @Override
    public void show() {
        bg = new Texture("nebula.jpg");
        background = new Background(bg);
        /*
        img = new Texture("badlogic.jpg");
        position = new Vector2(0, 0);
        //touch = new Vector2(0, 0);
        v = new Vector2();
        tmp = new Vector2();
        movementSpeed = 40;*/
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.6f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        //batch.draw(img, position.x, position.y, 0.5f, 0.5f);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();

        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //    touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        //   v.set(touch.cpy().sub(position)).setLength(movementSpeed);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return super.touchDown(touch, pointer, button);
    }
}
