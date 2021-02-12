package com.borichev.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.borichev.base.Sprite;
import com.borichev.math.Rect;

public class Logo extends Sprite {
    private Vector2 newPos;
    private final Vector2 v;
    private final Vector2 tmp;
    private final float V_LENGTH = 0.01f;
    private float height = 0.2f;

    public Logo(Texture texture) {
        super(new TextureRegion(texture));
        newPos = new Vector2();
        tmp = new Vector2();
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportions(height);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        newPos = touch;
        v.set(newPos.cpy().sub(pos)).setLength(V_LENGTH);
        return false;
    }

    @Override
    public void update(float delta) {
        tmp.set(newPos);
        if (tmp.sub(pos).len() > v.len()) {
            pos.add(v);
        } else {
            pos.set(newPos);
        }
    }


}
