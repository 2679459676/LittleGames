package com.yuwu.shoot;

import java.awt.image.BufferedImage;

/**
 * 英雄机
 */
public class Hero extends FlyingObject {
    private int life;// 生命值
    private int fire;// 火力

    public Hero() {
        super(97, 108, 140, 400);
        life = 3;
        fire = 0;
    }

    private int index = 0;

    public BufferedImage getImage() {//每10毫秒刷新一次图片
        return Images.heros[index++ % Images.heros.length];
    }

    //英雄机发射子弹，生成子弹对象
    public Bullet[] shoot() {
        int xStep = this.width / 4;// 1/4英雄机的宽度
        int yStep = 40;//固定子弹的Y坐标
        if (fire > 0) {//双倍子弹
            Bullet[] bs = new Bullet[2];
            bs[0] = new Bullet(this.x + xStep, this.y - yStep);
            bs[1] = new Bullet(this.x + 3 * xStep, this.y - yStep);
            fire-=2;//发射一次双倍火力，则火力-2
            return bs;
        } else {//单倍子弹
            Bullet[] bs = new Bullet[1];
            bs[0] = new Bullet(this.x + 2 * xStep, this.y - yStep);
            return bs;
        }
    }
    public void step() {

    }
    //英雄机移动，随着鼠标移动而移动
    public void moveTo(int x, int y) {
        this.x = x - this.width / 2;
        this.y = y - this.height / 2;
    }
    //英雄机增加火力
    public void addFire() {
        fire += 40;//增加40火力
    }
    //英雄机增加生命
    public void addLife() {
        life++;//增加1生命
    }
    //获取英雄机的生命值
    public int getLife() {
        return life;
    }
    //英雄机减少生命
    public void subtractLife() {
        life--;//减少1生命
    }
    //英雄机清空活力值
    public void clearFire() {
        fire = 0;//清空火力
    }
}
