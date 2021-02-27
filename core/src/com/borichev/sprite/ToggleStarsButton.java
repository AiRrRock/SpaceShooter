package com.borichev.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.borichev.base.ToggleButton;
import com.borichev.math.Rect;
import com.borichev.screen.GameScreen;

public class ToggleStarsButton extends ToggleButton {

    GameScreen screen;

    public ToggleStarsButton(TextureAtlas atlas, GameScreen screen) {
        super(atlas);
        this.screen = screen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setRight(-0.2f);
        setTop(0.2f);
    }

    @Override
    public void action() {
        super.action();
        screen.showStars(active);
    }
}
