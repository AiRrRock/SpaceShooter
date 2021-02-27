package com.borichev.base;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ToggleButton extends BaseButton {
    public static final float HEIGHT = 0.08f;
    protected boolean active;

    public ToggleButton(TextureAtlas atlas) {
        super(atlas, "tbOff", "tbOn");
        this.active = true;
        frame = 1;
    }

    private void toggle() {
        if (frame == 0) {
            frame = 1;
            active = true;
        } else {
            frame = 0;
            active = false;
        }
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void action() {
        toggle();
    }
}
