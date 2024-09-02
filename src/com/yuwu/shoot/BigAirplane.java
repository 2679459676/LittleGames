package com.yuwu.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 大型敌机
 *
 */
public class BigAirplane extends FlyingObject implements EnemyScore {
    private int speed;//移动速度
    public BigAirplane() {
        super(109,89);
        speed = 2;
    }
    private int index = 1;//爆破图起始下标
    @Override
    public BufferedImage getImage() {
        if (isLive()) {
            return Images.bairs[0];
        } else if (isDead()) {
            BufferedImage img =  Images.bairs[index++];
            if (index >= Images.bairs.length) {//若爆破图下标大于数组长度，则将状态改为删除
                state = REMOVE;
            }
            return img;
        }
        return null;//删除状态时，不返回图片
    }
    @Override
    public void step() {
        y += speed;
    }
    @Override
    public int getScore() {
        return 3;
    }
}
