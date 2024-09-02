package com.yuwu.shoot;

import java.util.Random;
import java.awt.image.BufferedImage;

/**
 * 飞行物超类
 */
public abstract class FlyingObject {
    public static final int LIVE = 0;//活着的
    public static final int DEAD = 1;//死了的
    public static final int REMOVE = 2;//要删除的
    protected int state = LIVE;//当前状态默认活着
    protected int width;// 宽
    protected int height;// 高
    protected int x;// 横坐标
    protected int y;// 纵坐标

    //为三种敌人提供的构造
    public FlyingObject(int width, int height) {
        this.width = width;
        this.height = height;
        Random rand = new Random();
        x = rand.nextInt(World.WIDTH - width);//x:0到World.WIDTH-width之间随机
        y = -height;
    }

    //英雄机，天空，子弹
    public FlyingObject(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    //获取对象的图片抽象方法
    public abstract BufferedImage getImage();

    //判断对象是否活着
    public boolean isLive() {
        return state == LIVE;
    }

    //判断对象是否被删除
    public boolean isRemove() {
        return state == REMOVE;
    }

    //判断是否是死亡的
    public boolean isDead() {
        return state == DEAD;
    }

    //飞行物移动
    public abstract void step();

    //检测敌人是否越界
    public boolean isOutOfBounds() {
        return y >= World.HEIGHT;// y坐标大于等于屏幕高度，则越界
    }

    //碰撞检测
    public boolean isHit(FlyingObject other) {
        int x1 = this.x - other.width;
        int x2 = this.x + this.width;
        int y1 = this.y - other.height;
        int y2 = this.y + this.height;
        int x = other.x;
        int y = other.y;
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }
    //飞行物死亡
    public void goDead() {
        state = DEAD;
    }

}
