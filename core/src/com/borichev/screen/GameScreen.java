package com.borichev.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import com.borichev.base.BaseScreen;
import com.borichev.base.PowerUp;
import com.borichev.math.Rect;
import com.borichev.pool.BulletPool;
import com.borichev.pool.EnemyPool;
import com.borichev.pool.ExplosionPool;
import com.borichev.pool.PowerUpPool;
import com.borichev.sprite.Background;
import com.borichev.sprite.Bullet;
import com.borichev.sprite.ButtonPause;
import com.borichev.sprite.EnemyShip;
import com.borichev.sprite.GameOver;
import com.borichev.sprite.MainShip;
import com.borichev.sprite.NewGame;
import com.borichev.sprite.ToggleBackgroundButton;
import com.borichev.sprite.ToggleStarsButton;
import com.borichev.sprite.TrackingStar;
import com.borichev.utils.EnemyEmitter;
import com.borichev.utils.Font;
import com.borichev.utils.PowerUpProducer;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;
    private static final float FONT = 0.02f;
    private static final float PADDING = 0.01f;

    private static final String FRAGS = "Score: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private enum State {PLAYING, GAME_OVER, PAUSED}

    private Texture bg;
    private TextureAtlas atlas;
    private TextureAtlas buttons;

    private Background background;
    private TrackingStar[] stars;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private PowerUpPool powerUpPool;
    private PowerUpProducer powerUpProducer;

    private MainShip mainShip;

    private Music music;
    private Sound enemyBulletSound;
    private Sound explosionSound;

    private EnemyEmitter enemyEmitter;
    private State state;

    private GameOver gameOver;
    private NewGame newGame;

    private Font font;

    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    private String sbShowBg;
    private String sbShowStars;


    private int frags;

    private boolean showBg;
    private boolean showStars;
    private ToggleBackgroundButton toggleBackground;
    private ToggleStarsButton toggleStars;
    private ButtonPause pause;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        buttons = new TextureAtlas(Gdx.files.internal("textures/additionalContent.tpack"));
        background = new Background(bg);
        bulletPool = new BulletPool();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        powerUpPool = new PowerUpPool(worldBounds);
        powerUpProducer = new PowerUpProducer(buttons, worldBounds, powerUpPool);
        enemyPool = new EnemyPool(bulletPool, explosionPool, powerUpProducer, worldBounds, enemyBulletSound);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        gameOver = new GameOver(atlas);
        newGame = new NewGame(atlas, this);
        font = new Font("font/font.fnt", "font/font.png");
        font.setColor(Color.GOLD);
        font.setSize(FONT);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        sbShowBg = "Background";
        sbShowStars = "Stars";

        stars = new TrackingStar[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new TrackingStar(atlas);
        }

        enemyEmitter = new EnemyEmitter(atlas, worldBounds, enemyPool);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        state = State.PLAYING;


        showBg = true;
        showStars = true;
        toggleBackground = new ToggleBackgroundButton(buttons, this);
        toggleStars = new ToggleStarsButton(buttons, this);
        pause = new ButtonPause(buttons, this);
    }

    public void startNewGame() {
        state = State.PLAYING;

        mainShip.startNewGame();
        frags = 0;

        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
        powerUpPool.freeAllActiveSprites();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (TrackingStar star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGame.resize(worldBounds);
        toggleBackground.resize(worldBounds);
        toggleStars.resize(worldBounds);
        pause.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        buttons.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        powerUpPool.dispose();
        music.dispose();
        enemyBulletSound.dispose();
        explosionSound.dispose();
        mainShip.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
            pause.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            newGame.touchDown(touch, pointer, button);
        } else if (state == State.PAUSED) {
            pause.touchDown(touch, pointer, button);
            toggleBackground.touchDown(touch, pointer, button);
            toggleStars.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
            pause.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            newGame.touchUp(touch, pointer, button);
        } else if (state == State.PAUSED) {
            pause.touchUp(touch, pointer, button);
            toggleBackground.touchUp(touch, pointer, button);
            toggleStars.touchUp(touch, pointer, button);
        }
        return false;
    }

    private void update(float delta) {
        for (TrackingStar star : stars) {
            star.update(delta, mainShip.getXv());
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            powerUpPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
        }

    }

    private void checkCollisions() {
        if (state == State.GAME_OVER) {
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        List<PowerUp> powerUpList = powerUpPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if (enemyShip.pos.dst(mainShip.pos) < minDist) {
                enemyShip.destroy();
                mainShip.damage(enemyShip.getDamage());
            }
        }
        for (PowerUp powerUp : powerUpList) {
            if (mainShip.isBulletCollision(powerUp)) {
                mainShip.consumePowerUp(powerUp);
                powerUp.destroy();
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() != mainShip) {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isDestroyed()) {
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemyShip.isDestroyed()) {
                        if (enemyShip.isBoss()) {
                            enemyEmitter.setBossUnleashed(false);
                        }
                        frags += enemyShip.getPoints().getScore();
                    }
                }
            }
            for (PowerUp powerUp : powerUpList) {
                if (powerUp.isBulletCollision(bullet)) {
                    bullet.destroy();
                    powerUp.destroy();
                    mainShip.consumePowerUp(powerUp);
                }
            }
        }
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        powerUpPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (showBg) {
            background.draw(batch);
        }
        if (showStars) {
            for (TrackingStar star : stars) {
                star.draw(batch);
            }
        }
        if (state == State.PLAYING) {
            pause.draw(batch);
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            powerUpPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            newGame.draw(batch);
        } else if (state == State.PAUSED) {
            pause.draw(batch);
            toggleBackground.draw(batch);
            toggleStars.draw(batch);
            printPauseInfo();
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(
                batch,
                sbFrags.append(FRAGS).append(frags),
                worldBounds.getLeft() + PADDING,
                worldBounds.getTop() - PADDING
        );
        font.draw(
                batch,
                sbHp.append(HP).append(mainShip.getHp()),
                worldBounds.pos.x,
                worldBounds.getTop() - PADDING,
                Align.center
        );
        font.draw(
                batch,
                sbLevel.append(LEVEL).append(enemyEmitter.getLevel()),
                worldBounds.getRight() - PADDING,
                worldBounds.getTop() - PADDING,
                Align.right
        );
    }

    private void printPauseInfo() {
        font.draw(
                batch,
                sbShowStars,
                -0.18f,
                0.17f
        );
        font.draw(
                batch,
                sbShowBg,
                -0.18f,
                0.07f
        );
    }

    public void showBg(boolean showBg) {
        this.showBg = showBg;
    }

    public void showStars(boolean showStars) {
        this.showStars = showStars;
    }

    public void togglePause() {
        if (state != State.GAME_OVER) {
            if (state == State.PAUSED) {
                state = State.PLAYING;
            } else {
                state = State.PAUSED;
            }
        }
    }

}
