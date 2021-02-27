package com.borichev.utils.enums;

public enum Points {
    SMALL(1), MEDIUM(3), BIG(5), BOSS(10);
    private int score;

    Points(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
