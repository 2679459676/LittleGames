package com.yuwu.shoot;

import java.awt.image.BufferedImage;



/**
 * 小敌机
 */
public class Airplane extends FlyingObject implements EnemyScore {
    private int speed;// 移动速度

    public Airplane() {
       super(49,37);
        speed = 2;
    }
    private int index = 1;//爆破图起始下标
    @Override
    public BufferedImage getImage() {
        if (isLive()) {
            return Images.airs[0];
        } else if (isDead()) {
            BufferedImage img =  Images.airs[index++];
            if (index >= Images.airs.length) {//若爆破图下标大于数组长度，则将状态改为删除
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
        return 1;//打掉小敌机得分为1
    }
}
