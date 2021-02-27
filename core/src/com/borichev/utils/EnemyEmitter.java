package com.borichev.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.borichev.math.Rect;
import com.borichev.math.Rnd;
import com.borichev.pool.EnemyPool;
import com.borichev.sprite.EnemyShip;

import static com.borichev.utils.enums.Points.BIG;
import static com.borichev.utils.enums.Points.BOSS;
import static com.borichev.utils.enums.Points.MEDIUM;
import static com.borichev.utils.enums.Points.SMALL;

public class EnemyEmitter {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final int ENEMY_MEDIUM_BULLET_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_MEDIUM_HP = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_BIG_HP = 10;

    private static final float ENEMY_BOSS_HEIGHT = 0.4f;
    private static final float ENEMY_BOSS_BULLET_HEIGHT = 0.1f;
    private static final int ENEMY_BOSS_BULLET_DAMAGE = 20;
    private static final float ENEMY_BOSS_RELOAD_INTERVAL = 2.5f;
    private static final int ENEMY_BOSS_HP = 50;


    private final Vector2 enemySmallBulletV = new Vector2(0, -0.3f);
    private final Vector2 enemyMediumBulletV = new Vector2(0, -0.25f);
    private final Vector2 enemyBigBulletV = new Vector2(0, -0.2f);
    private final Vector2 enemyBossBulletV = new Vector2(0, -0.4f);
    private final Vector2 enemySmallV = new Vector2(0, -0.2f);
    private final Vector2 enemyMediumV = new Vector2(0, -0.03f);
    private final Vector2 enemyBigV = new Vector2(0, -0.005f);
    private final Vector2 enemyBossV = new Vector2(-0.2f, 0f);

    private TextureRegion[] enemySmallRegions;
    private TextureRegion[] enemyMediumRegions;
    private TextureRegion[] enemyBigRegions;

    private Rect worldBounds;
    private TextureRegion bulletRegion;
    private EnemyPool enemyPool;

    private float generateInterval = 4f;
    private float generateTimer;

    private int level;
    private boolean bossBattle;
    private boolean bossUnleashed;

    public EnemyEmitter(TextureAtlas atlas, Rect worldBounds, EnemyPool enemyPool) {
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;

        enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        enemyMediumRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);

        bulletRegion = atlas.findRegion("bulletEnemy");
        bossUnleashed = false;
    }

    public void generate(float delta, int frags) {
        level = frags / 10 + 1;
        bossBattle = level % 5 == 0;
        generateTimer += delta;
        EnemyShip enemyShip = null;
        if (!bossBattle) {
            if (generateTimer >= generateInterval) {
                generateTimer = 0f;
                enemyShip = generateEnemy();
            }
        } else {
            if (!bossUnleashed) {
                enemyShip = generateEnemy();
                bossUnleashed = true;
            }
        }

        if (enemyShip != null) {
            enemyShip.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft() + enemyShip.getHalfWidth(),
                    worldBounds.getRight() - enemyShip.getHalfWidth()
            );
            enemyShip.setBottom(worldBounds.getTop());
        }
    }

    private EnemyShip generateEnemy() {
        EnemyShip enemyShip = enemyPool.obtain();
        float enemyType = (float) Math.random();
        if (bossBattle) {
            enemyShip.set(
                    enemySmallRegions,
                    enemyBossV,
                    bulletRegion,
                    ENEMY_BOSS_BULLET_HEIGHT,
                    enemyBossBulletV,
                    ENEMY_BOSS_BULLET_DAMAGE,
                    ENEMY_BOSS_RELOAD_INTERVAL,
                    ENEMY_BOSS_HEIGHT,
                    true,
                    ENEMY_BOSS_HP,
                    BOSS
            );
        } else {
            if (enemyType < 0.5f) {
                enemyShip.set(
                        enemySmallRegions,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        enemySmallBulletV,
                        ENEMY_SMALL_BULLET_DAMAGE * level,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        false,
                        ENEMY_SMALL_HP,
                        SMALL
                );
            } else if (enemyType < 0.8f) {
                enemyShip.set(
                        enemyMediumRegions,
                        enemyMediumV,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        enemyMediumBulletV,
                        ENEMY_MEDIUM_BULLET_DAMAGE * level,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT,
                        false,
                        ENEMY_MEDIUM_HP,
                        MEDIUM
                );
            } else {
                enemyShip.set(
                        enemyBigRegions,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        enemyBigBulletV,
                        ENEMY_BIG_BULLET_DAMAGE * level,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT,
                        false,
                        ENEMY_BIG_HP,
                        BIG
                );
            }
        }
        return enemyShip;
    }

    public int getLevel() {
        return level;
    }

    public void setBossUnleashed(boolean bossUnleashed) {
        this.bossUnleashed = bossUnleashed;
    }


}
