package com.borichev.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.borichev.base.BaseButton;
import com.borichev.math.Rect;
import com.borichev.screen.GameScreen;

public class ButtonPlay extends BaseButton {
    private static final float HEIGHT = 0.25f;
    private static final float PADDING = 0.03f;

    private final Game game;

    public ButtonPlay(TextureAtlas region, Game game) {
        super(region.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + PADDING);
        setLeft(worldBounds.getLeft() + PADDING);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}
