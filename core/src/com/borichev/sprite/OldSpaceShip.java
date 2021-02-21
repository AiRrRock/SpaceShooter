package com.borichev.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.borichev.base.Sprite;
import com.borichev.math.Rect;

public class OldSpaceShip extends Sprite {
    private static final float HEIGHT = 0.2f;
    private static final float PADDING = 0.03f;
    private final float V_LENGTH = 0.001f;

    private Rect worldBounds;

    private Vector2 v;
    private Vector2 newPos;
    private final Vector2 tmp;


    public OldSpaceShip(TextureAtlas atlas) {
        super(new TextureRegion(atlas.findRegion("main_ship"), 0, 0, 195, 287));
        v = new Vector2();
        newPos = new Vector2();
        tmp = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
        this.worldBounds = worldBounds;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        newPos.set(touch.x, pos.y);
        v.set(newPos.cpy().sub(pos)).setLength(V_LENGTH);
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case 21:
                pos.x -= V_LENGTH * 10;
                break;
            case 22:
                pos.x += V_LENGTH * 10;
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        if (isMe(touch)) {
            pos.set(touch.x, pos.y);
            newPos.set(pos);
        }
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

        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
    }
}
