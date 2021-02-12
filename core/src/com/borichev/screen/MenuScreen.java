package com.borichev.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.borichev.base.BaseScreen;
import com.borichev.math.Rect;
import com.borichev.sprite.Background;
import com.borichev.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Texture img;
    private Logo logo;
    private Background background;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/nebula.jpg");
        background = new Background(bg);
        img = new Texture("badlogic.jpg");
        logo = new Logo(img);
    }

    @Override
    public void render(float delta) {
        logo.update(delta);
        Gdx.gl.glClearColor(0.3f, 0.6f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }



    @Override
    public void dispose() {
        bg.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch, pointer, button);
        return false;
    }
}
