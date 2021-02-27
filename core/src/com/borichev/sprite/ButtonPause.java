package com.borichev.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.borichev.base.BaseButton;
import com.borichev.math.Rect;
import com.borichev.screen.GameScreen;

public class ButtonPause extends BaseButton {

    private static final float HEIGHT = 0.09f;
    private static final float PADDING_TOP = 0.1f;
    private static final float PADDING_RIGHT = 0.03f;

    private GameScreen screen;

    public ButtonPause(TextureAtlas atlas, GameScreen screen) {
        super(atlas, "btPause", "btResume");
        this.screen = screen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setTop(worldBounds.getTop() - PADDING_TOP);
        setRight(worldBounds.getRight() - PADDING_RIGHT);
    }

    @Override
    public void action() {
        toggle();
    }

    private void toggle() {
        if (frame == 0) {
            frame = 1;
        } else {
            frame = 0;
        }
        screen.togglePause();
    }
}
