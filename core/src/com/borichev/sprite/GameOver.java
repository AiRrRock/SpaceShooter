package com.borichev.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.borichev.base.Sprite;
import com.borichev.math.Rect;

public class GameOver extends Sprite {

    private static final float HEIGHT = 0.08f;
    private static final float BOTTOM = 0.3f;

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(BOTTOM);
    }
}
