package com.borichev.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.borichev.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    Texture img;
    Texture background;
    int movementSpeed;
    Vector2 speed;
    Vector2 distance;
    Vector2 position;
    Vector2 desiredPosition;

    @Override
    public void show() {
        img = new Texture("badlogic.jpg");
        background = new Texture("nebula.jpg");
        position = new Vector2(0, 0);
        desiredPosition = new Vector2(position);
        distance = new Vector2(0, 0);
        speed = new Vector2(0, 0);
        movementSpeed = 40;
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.6f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!desiredPosition.equals(position)) {
            calculateMovement();
        }
        batch.begin();
        batch.draw(background, 0, 0, 1080, 1920);
        batch.draw(img, position.x, position.y);
        batch.end();
        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        desiredPosition.set(screenX, Gdx.graphics.getHeight() - screenY);
        distance.set(desiredPosition);
        distance.sub(position);
        return false;
    }

    private void calculateMovement() {
        speed.set(distance);
        if (speed.len() > movementSpeed) {
            speed.setLength(movementSpeed);
        }
        position.add(speed);
        distance.sub(speed);
    }
}
