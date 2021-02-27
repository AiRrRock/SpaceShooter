package com.borichev.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.borichev.base.BaseButton;
import com.borichev.math.Rect;
import com.borichev.screen.GameScreen;

public class NewGame extends BaseButton {

    private static final float HEIGHT = 0.05f;
    private static final float BOTTOM = 0.2f;


    private Game game;

    public NewGame(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("button_new_game"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}
