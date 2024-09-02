package com.yuwu.shoot;

import java.awt.image.BufferedImage;

/**
 * 子弹
 */
public class Bullet extends FlyingObject {
    private int speed;

    public Bullet(int x, int y) {
        super(8, 25, x, y);
        speed = 3;
    }

    @Override
    public BufferedImage getImage() {
        if (isLive()) {
            return Images.bullet;
        } else if (isDead()) {
            state = REMOVE;
        }
        return null;//死了的和删除的
    }
    @Override
    public void step() {
        y -= speed;
    }
    @Override
    public boolean isOutOfBounds() {
        return y <= -height;//子弹飞出屏幕
    }
}
