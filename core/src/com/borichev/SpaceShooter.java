package com.borichev;

import com.badlogic.gdx.Game;
import com.borichev.screen.MenuScreen;

public class SpaceShooter extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen());
    }

}
